package com.randomx.travel.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.randomx.travel.R
import com.randomx.travel.activity.auth.LoginScreen
import com.randomx.travel.activity.home.HomeActivity
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.UserSessionUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ToolsUtils.init(applicationContext)

        checkUserSession()
    }

    override fun onResume() {
        super.onResume()
        checkUserSession()
    }

    override fun onStart() {
        super.onStart()
        checkUserSession()
    }

    private fun checkUserSession() {
        UserSessionUtils.init(this)
        ToolsUtils.goToActivity(this, if (UserSessionUtils.isUserLogged()) HomeActivity::class.java else LoginScreen::class.java, true)
        finish()
    }
}
