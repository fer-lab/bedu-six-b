package com.randomx.travel.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.randomx.travel.R
import com.randomx.travel.activity.category.CategoryHomeActivity
import com.randomx.travel.activity.destination.DestinationHomeActivity
import com.randomx.travel.activity.error.InternetErrorActivity
import com.randomx.travel.activity.home.HomeActivity
import com.randomx.travel.activity.wishlist.WishlistHomeActivity
import com.randomx.travel.utils.ToolsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var _progressBar: ProgressBar
    private lateinit var _navbarHome: LinearLayout
    private lateinit var _navbarDestinations: LinearLayout
    private lateinit var _navbarCategories: LinearLayout
    private lateinit var _navbarWishlist: LinearLayout
    private lateinit var _navbarProfile: LinearLayout
    private var _navbarLoaded: Boolean = false
    private var _exitTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNetwork()

    }


    override fun onBackPressed() {
        doExitApp()
    }

    open fun doExitApp() {
        if (System.currentTimeMillis() - _exitTime > 2000) {
            Toast.makeText(this, getString(R.string.system_exit_warning), Toast.LENGTH_SHORT).show()
            _exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
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
        val clearStack = false;

        _navbarLoaded = true
        _navbarHome = findViewById(R.id.nav_menu_home)
        _navbarDestinations = findViewById(R.id.nav_menu_destinations)
        _navbarCategories = findViewById(R.id.nav_menu_categories)
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
                // Ya estás en la CategoryHomeActivity, no es necesario iniciarla nuevamente
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, CategoryHomeActivity::class.java, clearStack)
        }
        _navbarWishlist.setOnClickListener {
            if (this is WishlistHomeActivity) {
                // Ya estás en la WishlistHomeActivity, no es necesario iniciarla nuevamente
                return@setOnClickListener
            }
            ToolsUtils.goToActivity(this, WishlistHomeActivity::class.java, clearStack)
        }

    }

    private fun getProgressBar():ProgressBar {
        if (!::_progressBar.isInitialized) {
            _progressBar = findViewById(R.id.content_main_progress_bar)
        }

        return _progressBar;
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

            Thread.sleep(3000)
            callback.invoke()
        }.start()
    }


    /**

    closeSession.setOnClickListener {
    showProgressBar()
    disableButton(closeSession)

    showProgressBar()
    disableButton(closeSession)

    performLongOperation {
    runOnUiThread {
    hideProgressBar()
    enableButton(closeSession)
    }
    }


    }

     */

}
