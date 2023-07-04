package com.randomx.travel.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar
import com.randomx.travel.R
import com.randomx.travel.activity.MainActivity
import org.jsoup.Jsoup
import java.text.NumberFormat
import java.util.Currency
import java.util.Random
import java.util.UUID


object ToolsUtils {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }


    fun fullScreen(activity: Activity) {
        val window = activity.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Modo de pantalla completa utilizando la API de inmersión del sistema en Android 11 y versiones posteriores
            val insetsController = window.insetsController
            insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Modo de pantalla completa compatible con versiones anteriores a Android 11
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    fun goToActivity(context: Context, destinationClass: Class<*>, params: Map<String, String>? = null, clearStack: Boolean = false) {

        val intent = Intent(context, destinationClass)

        if (clearStack) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        if (params != null) {
            for ((key, value) in params) {
                intent.putExtra(key, value)
            }
        }


        if (context is Activity)
        {
            context.overridePendingTransition(0, 0)
        }

        context.startActivity(intent)

    }

    fun goToHome(context: Context, clearStack: Boolean, toastMessage: String? = null) {

        if (toastMessage != null) {
            toast(context, toastMessage)
        }

        goToActivity(context, MainActivity::class.java, null, clearStack)
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun snack(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }


    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = activity.currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusView.windowToken,
                0
            )
        }
    }

    fun getString(resId: Int): String {
        return appContext.getString(resId)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }


    fun dpToPx(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    fun pxToDp(context: Context, px: Int): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }

    fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun formatCurrency(amount: Double, currencyCode: String): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        currencyFormatter.currency = Currency.getInstance(currencyCode)
        return currencyFormatter.format(amount)
    }

    fun shareText(context: Context, title: String, text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }


    fun generateRandomNumber(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min
    }

    fun getDeviceScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun getDeviceScreenHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    fun generateRandomString(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun openUrlInBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun copyTextToClipboard(context: Context, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }


    fun formatFileSize(sizeInBytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var size = sizeInBytes.toDouble()
        var unitIndex = 0

        while (size > 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }

        return String.format("%.2f %s", size, units[unitIndex])
    }

    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun capitalizeWords(input: String): String {
        val words = input.split(" ")
        val capitalizedWords = words.map { it.capitalize() }
        return capitalizedWords.joinToString(" ")
    }

    fun copyTextToClipboard(context: Context, label: String, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
    }

    fun isDarkModeEnabled(context: Context): Boolean {
        val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }

    fun launchEmailApp(context: Context, emailAddress: String, subject: String = "", body: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // No se encontró ninguna aplicación de correo electrónico
            Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    fun nestedScrollTo(nested: NestedScrollView, targetView: View) {
        nested.post { nested.scrollTo(500, targetView.bottom) }
    }

    fun removeHtmlTags(input: String): String {
        return Jsoup.parse(input).text()
    }

    inline fun <reified T> getListFromArray(context: Context, arrayResId: Int): List<T> {
        val resources: Resources = context.resources
        val array: Array<T> = resources.getStringArray(arrayResId) as Array<T>
        return array.toList()
    }



}
