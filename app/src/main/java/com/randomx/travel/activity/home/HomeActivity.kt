package com.randomx.travel.activity.home

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.UserSessionUtils

class HomeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var textView = findViewById<TextView>(R.id.textView2)
        textView.text = UserSessionUtils.getUser().email


        val closeSession = findViewById<Button>(R.id.button2)


        closeSession.setOnClickListener {
            showProgressBar()
            disableButton(closeSession)

            showProgressBar()
            disableButton(closeSession)

            performLongOperation {
                runOnUiThread {
                    hideProgressBar()
                    enableButton(closeSession)
                    DialogUtils.showSuccessDialog(this, "Sesión cerrada", "La sesión se ha cerrado correctamente")
                }
            }


        }
        /*
        val closeSession = findViewById<Button>(R.id.button2)
        closeSession.setOnClickListener {
            UserSessionUtils.clearUser()
            ToolsUtils.goToHome(this, true)
        }

         */
    }


}