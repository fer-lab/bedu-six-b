package com.randomx.travel.activity.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.randomx.travel.R
import com.randomx.travel.model.UserModel
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.UserSessionUtils

class LoginScreen : AppCompatActivity() {

    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwdInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var loginGoogleButton: SignInButton
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
        emailLayout = findViewById(R.id.login_layout_email)
        emailInput = findViewById(R.id.login_field_email)
        passwordLayout = findViewById(R.id.login_layout_password)
        passwdInput = findViewById(R.id.login_field_password)
        loginButton = findViewById(R.id.login_submit)
        loginGoogleButton = findViewById(R.id.login_submit_google)
        errorLabel = findViewById(R.id.login_error)
        signupButton = findViewById(R.id.login_sign_up_btn)
        logo = findViewById(R.id.login_logo)
        form = findViewById(R.id.login_form)
        forgotPassword = findViewById(R.id.login_forgot_password)

        setGoogleButtonText(getString(R.string.login_google_btn))
    }


    private fun setGoogleButtonText(buttonText: String) {



        for (i in 0 until loginGoogleButton.childCount) {
            val v = loginGoogleButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                return
            }
        }

        loginGoogleButton.getChildAt(0)?.let {
            val smaller = Math.min(it.paddingLeft, it.paddingRight) + 10
            it.setPadding(smaller, it.paddingTop, smaller, it.paddingBottom)
        }

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

        emailInput.addTextChangedListener(createEmailTextWatcher(emailLayout, emailInput))
        passwdInput.addTextChangedListener(createPasswordTextWatcher(passwordLayout, passwdInput))

        forgotPassword.setOnClickListener {
            DialogUtils.toastNotImplemented(this)
        }

        signupButton.setOnClickListener {
            ToolsUtils.goToActivity(this, RegisterForm::class.java)
        }

        loginButton.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        if (isFormValid()) {
            UserSessionUtils.setUser(UserModel("John", "Doe", "smith@smith.com", "12345678", "12345678"))
            ToolsUtils.goToHome(this, true, getString(R.string.login_success))
        } else {
            DialogUtils.toast(this, getString(R.string.login_error))
        }
    }

    private fun isFormValid(): Boolean {
        return isEmailValid(emailInput)
                && isPasswordValid(passwdInput)
    }

    private fun createEmailTextWatcher(layout: TextInputLayout, input: TextInputEditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val isValid = isEmailValid(input)
                layout.isErrorEnabled = !isValid
                layout.error = if (!isValid) getString(R.string.register_error_email) else ""
                loginButton.isEnabled = isFormValid()
            }
        }
    }

    private fun createPasswordTextWatcher(layout: TextInputLayout, input: TextInputEditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val isValid = isPasswordValid(input)
                layout.isErrorEnabled = !isValid
                loginButton.isEnabled = isFormValid()
            }
        }
    }

    private fun isEmailValid(input: TextInputEditText): Boolean {
        val email = input.text.toString().trim()
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(input: TextInputEditText): Boolean {
        return input.text.toString().trim().isNotEmpty()
    }
}
