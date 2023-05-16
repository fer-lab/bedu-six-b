package com.randomx.travel.activity.auth

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.textfield.TextInputEditText
import com.randomx.travel.R
import com.randomx.travel.model.UserModel
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.UserSessionUtils

class LoginScreen : AppCompatActivity() {

    private lateinit var emailInput: TextInputEditText
    private lateinit var passwdInput: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var errorLabel: TextView
    private lateinit var signupButton: TextView
    private lateinit var logo: ImageView
    private lateinit var form: LinearLayout
    private lateinit var forgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        initViews()
        setupAnimations()
        setupListeners()
        //ToolsUtils.fullScreen(this)
    }

    private fun initViews() {
        emailInput = findViewById(R.id.login_field_email)
        passwdInput = findViewById(R.id.login_field_password)
        loginButton = findViewById(R.id.login_submit)
        errorLabel = findViewById(R.id.login_error)
        signupButton = findViewById(R.id.login_sign_up_btn)
        logo = findViewById(R.id.login_logo)
        form = findViewById(R.id.login_form)
        forgotPassword = findViewById(R.id.login_forgot_password)
    }

    private fun setupAnimations() {
        form.visibility = View.INVISIBLE

        val alphaAnimation = AlphaAnimation(0.2f, 1.0f)
        val alphaAnimation2 = AlphaAnimation(0.0f, 1.0f)

        alphaAnimation.duration = 600
        alphaAnimation.repeatCount = 2
        alphaAnimation.repeatMode = Animation.REVERSE
        logo.startAnimation(alphaAnimation)

        alphaAnimation.duration = 500
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                form.visibility = View.VISIBLE
                form.startAnimation(alphaAnimation2)
                logo.animate().translationY(-(form.height / 1.3f))
                    .setInterpolator(LinearOutSlowInInterpolator()).duration = 1000
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun setupListeners() {
        forgotPassword.setOnClickListener {
            DialogUtils.showErrorNotImplemented(this)
        }

        signupButton.setOnClickListener {
            ToolsUtils.goToActivity(this, RegisterForm::class.java)
        }

        loginButton.setOnClickListener {
            UserSessionUtils.setUser(UserModel("John", "Doe", "smith@smith.com", "12345678", "12345678"))
            ToolsUtils.goToHome(this, true)
        }
    }
}
