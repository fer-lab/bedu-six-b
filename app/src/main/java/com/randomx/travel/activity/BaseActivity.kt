package com.randomx.travel.activity

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.randomx.travel.R
import com.randomx.travel.activity.category.CategoryHomeActivity
import com.randomx.travel.activity.destination.DestinationHomeActivity
import com.randomx.travel.activity.error.InternetErrorActivity
import com.randomx.travel.activity.home.HomeActivity
import com.randomx.travel.activity.tours.ToursHomeActivity
import com.randomx.travel.activity.wishlist.WishlistHomeActivity
import com.randomx.travel.model.UserModel
import com.randomx.travel.network.datasource.CategoriesDataSource
import com.randomx.travel.network.datasource.DestinationsDataSource
import com.randomx.travel.network.datasource.ManagerDataSource
import com.randomx.travel.network.datasource.ProductsDataSource
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.UserSessionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var exitConfirmationReceiver: BroadcastReceiver
    private var exitTime: Long = 0
    private lateinit var exitConfirmationDialog: AlertDialog


    private lateinit var _progressBar: ProgressBar
    private lateinit var _navbarHome: LinearLayout
    private lateinit var _navbarDestinations: LinearLayout
    private lateinit var _navbarCategories: LinearLayout
    private lateinit var _navbarTours: LinearLayout
    private lateinit var _navbarWishlist: LinearLayout
    private lateinit var _navbarProfile: LinearLayout
    private var _navbarLoaded: Boolean = false
    private var _exitTime: Long = 0
    protected lateinit var bottomSheetLayout: LinearLayout
    protected lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    protected lateinit var buttonShowBottomSheet: Button
    protected lateinit var bottomSheetDialog: BottomSheetDialog
    protected lateinit var profileOverlay: View
    protected lateinit var user: UserModel
    protected val SWIPE_RANGE = 100



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNetwork()


        exitConfirmationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showExitConfirmationDialog()
            }
        }

        val intentFilter = IntentFilter("com.example.EXIT_CONFIRMATION")
        registerReceiver(exitConfirmationReceiver, intentFilter)

        exitConfirmationDialog = buildExitConfirmationDialog()

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(exitConfirmationReceiver)
    }

    override fun onBackPressed() {
        // Comprueba si eres la última actividad en la pila
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appTasks = activityManager.appTasks

        if (appTasks.isNotEmpty()) {
            val taskInfo = appTasks[0].taskInfo
            val numActivities = taskInfo.numActivities

            if (numActivities == 1) {
                // Eres la última actividad en la pila, muestra el diálogo de confirmación
                showExitConfirmationDialog()
                return
            }
        }

        super.onBackPressed()
    }

    private fun showExitConfirmationDialog() {
        exitConfirmationDialog.show()
    }

    private fun buildExitConfirmationDialog(): AlertDialog {

        return AlertDialog.Builder(this)
        .setTitle(getString(R.string.exit_confirmation_title))
        .setPositiveButton(R.string.close) { dialogInterface, i ->
            finish()
        }
        .setNegativeButton(R.string.cancel, null)
        .create()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !_navbarLoaded) {
            initNavbar()
        }
    }

    private fun checkNetwork()
    {
        if (!ToolsUtils.isNetworkAvailable(this)) {
            val intent = Intent(this, InternetErrorActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    protected fun initNavbar() {

        if (_navbarLoaded || R.id.navigation_bar == 0 || findViewById<LinearLayout>(R.id.nav_menu_home) === null) {
            return
        }
        val clearStack = false

        _navbarLoaded = true
        _navbarHome = findViewById(R.id.nav_menu_home)
        _navbarDestinations = findViewById(R.id.nav_menu_destinations)
        _navbarCategories = findViewById(R.id.nav_menu_categories)
        _navbarTours = findViewById(R.id.nav_menu_tours)
        _navbarWishlist = findViewById(R.id.nav_menu_wishlist)
        _navbarProfile = findViewById(R.id.nav_menu_profile)

        _navbarHome.setOnClickListener {
            if (this is HomeActivity) {
                // Ya estás en la HomeActivity, no es necesario iniciarla nuevamente
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, HomeActivity::class.java, clearStack)
        }
        _navbarDestinations.setOnClickListener {
            if (this is DestinationHomeActivity) {
                // Ya estás en la DestinationHomeActivity, no es necesario iniciarla nuevamente
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, DestinationHomeActivity::class.java, clearStack)
        }
        _navbarCategories.setOnClickListener {
            if (this is CategoryHomeActivity) {
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, CategoryHomeActivity::class.java, clearStack)
        }
        _navbarTours.setOnClickListener {
            if (this is ToursHomeActivity) {
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, ToursHomeActivity::class.java, clearStack)
        }
        _navbarWishlist.setOnClickListener {
            if (this is WishlistHomeActivity) {
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, WishlistHomeActivity::class.java, clearStack)
        }

        _navbarProfile.setOnClickListener {

            showBottomSheet()

        }

    }

    protected fun initBottomSheet()
    {

        if (R.id.bottomSheetLayout == 0 || findViewById<LinearLayout>(R.id.bottomSheetLayout) === null) {
            return
        }

        if (!::bottomSheetLayout.isInitialized)
        {
            bottomSheetLayout = findViewById(R.id.bottomSheetLayout)
        }

        if (!::bottomSheetBehavior.isInitialized)
        {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        if (!::profileOverlay.isInitialized)
        {
            profileOverlay = findViewById(R.id.profile_overlay)

            profileOverlay.setOnClickListener {
                hideBottomSheet()
            }
        }

        if (!::bottomSheetDialog.isInitialized)
        {

            bottomSheetDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
            bottomSheetDialog.setContentView(R.layout.profile_dialog_bottom_sheet_layout)
            bottomSheetDialog.setOnDismissListener {
                hideBottomSheet()
            }

            val headerView = LayoutInflater.from(this).inflate(R.layout.profile_dialog_bottom_sheet_content, null)
            val userName = headerView.findViewById<TextView>(R.id.profile_label_name)
            val userEmail = headerView.findViewById<TextView>(R.id.profile_label_email)

            userName.text = currentUser().name
            userEmail.text = currentUser().email

            val navigationView = bottomSheetDialog.findViewById<NavigationView>(R.id.navigationView)

            if (navigationView !== null)
            {
                navigationView.addHeaderView(headerView)

                navigationView.setNavigationItemSelectedListener { menuItem ->
                    hideBottomSheet()
                    menuItem.isChecked = false

                    when (menuItem.itemId) {
                        R.id.profile_menu_my_account -> {
                            DialogUtils.toast(this, "Not implemented")
                            //ToolsUtils.goToActivity(this, ProfileActivity::class.java)
                        }
                        R.id.profile_menu_orders -> {
                            DialogUtils.toast(this, "Not implemented")
                            //ToolsUtils.goToActivity(this, OrderActivity::class.java)
                        }
                        R.id.profile_menu_lab -> {
                            DialogUtils.toast(this, "Not implemented")
                            //ToolsUtils.goToActivity(this, OrderActivity::class.java)
                        }
                        R.id.profile_menu_sign_out -> {

                            showProgressBar()
                            performLongOperation {
                                runOnUiThread {
                                    UserSessionUtils.clearUser()
                                    hideProgressBar()
                                    DialogUtils.toast(this, getString(R.string.system_logout_success))
                                    ToolsUtils.goToHome(this, true)
                                }
                            }
                        }
                        R.id.profile_menu_exit -> {
                            System.exit(0)
                        }
                        else -> {
                            DialogUtils.toast(this, "Unknown option")
                        }
                    }
                    false
                }

            }


        }

    }
    private fun showBottomSheet() {

        initBottomSheet()

        if (!::bottomSheetBehavior.isInitialized)
        {
            return
        }

        // Mostrar el overlay
        profileOverlay.visibility = View.VISIBLE

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.show()
    }

    private fun hideBottomSheet()
    {

        if (!::bottomSheetBehavior.isInitialized)
        {
            return
        }


        // Ocultar el overlay
        profileOverlay.visibility = View.GONE

        // Ocultar el bottom sheet
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetDialog.dismiss()
    }
    private fun getProgressBar():ProgressBar {
        if (!::_progressBar.isInitialized) {
            _progressBar = findViewById(R.id.content_main_progress_bar)
        }

        return _progressBar
    }

    protected fun showProgressBar() {
        getProgressBar().visibility = View.VISIBLE
    }

    protected fun hideProgressBar() {
        getProgressBar().visibility = View.GONE
    }

    fun  performLongOperation() {
        CoroutineScope(Dispatchers.Main).launch {

            delay(3000)
        }

    }

    protected fun currentUser(): UserModel
    {
        if (!::user.isInitialized)
        {
            user = UserSessionUtils.getUser()
            Log.d("User", user.toString())
        }


        return user
    }

    protected fun disableButton(button: Button)
    {
        // disable button
        button.isEnabled = false
    }

    protected fun enableButton(button: Button)
    {
        // disable button
        button.isEnabled = true
    }

    fun performLongOperation(callback: () -> Unit) {

        Thread {

            Thread.sleep(1000)
            callback.invoke()
        }.start()
    }

    protected fun apiCategories(): CategoriesDataSource
    {
        return ManagerDataSource.getInstance().categories()
    }

    protected fun apiDestinations(): DestinationsDataSource
    {
        return ManagerDataSource.getInstance().destinations()
    }

    protected fun apiProducts(): ProductsDataSource
    {
        return ManagerDataSource.getInstance().products()
    }
    open fun onBackPressed(view: View?) {
        // Handle the back button click here
        onBackPressed() // or any custom logic you want
    }


}
