package com.randomx.travel.activity.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.randomx.travel.R
import com.randomx.travel.model.UserModel
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.UserSessionUtils

class RegisterForm : AppCompatActivity() {

    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameLayout: TextInputLayout
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneNumberLayout: TextInputLayout
    private lateinit var phoneNumberInput: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwordInput: TextInputEditText
    private lateinit var registerFormSubmit: Button
    private lateinit var registerFormCancel: Button

    private val minCharacters: Int = 3
    private val minCharactersPasswd: Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)

        initViews()
        setupToolbar()
        setupListeners()

        registerFormSubmit.setOnClickListener { registerUser() }
        registerFormCancel.setOnClickListener { finish() }

    }

    private fun initViews() {
        firstNameLayout = findViewById(R.id.register_layout_first_name)
        firstNameInput = findViewById(R.id.register_field_first_name)
        lastNameLayout = findViewById(R.id.register_layout_last_name)
        lastNameInput = findViewById(R.id.register_field_last_name)
        emailLayout = findViewById(R.id.register_layout_email)
        emailInput = findViewById(R.id.register_field_email)
        phoneNumberLayout = findViewById(R.id.register_layout_phone_number)
        phoneNumberInput = findViewById(R.id.register_field_phone_number)
        passwordLayout = findViewById(R.id.register_layout_password)
        passwordInput = findViewById(R.id.register_field_password)
        registerFormSubmit = findViewById(R.id.register_button_submit)
        registerFormCancel = findViewById(R.id.register_button_cancel)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.register_title)

    }

    private fun setupListeners() {
        firstNameInput.addTextChangedListener(createTextWatcher(firstNameLayout, minCharacters))
        lastNameInput.addTextChangedListener(createTextWatcher(lastNameLayout, minCharacters))
        emailInput.addTextChangedListener(createEmailTextWatcher(emailLayout))
        passwordInput.addTextChangedListener(createPasswordTextWatcher(passwordLayout, minCharactersPasswd))
    }

    private fun createTextWatcher(layout: TextInputLayout, minCharacters: Int = 0): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val isValid = s.toString().trim().length >= minCharacters
                layout.isErrorEnabled = !isValid
                layout.error = if (!isValid) getString(R.string.at_least_characters, minCharacters) else ""
                registerFormSubmit.isEnabled = isFormValid()
            }
        }
    }

    private fun createEmailTextWatcher(layout: TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val isValid = isEmailValid(emailInput)
                layout.isErrorEnabled = !isValid
                layout.error = if (!isValid) getString(R.string.register_error_email) else ""
                registerFormSubmit.isEnabled = isFormValid()
            }
        }
    }

    private fun createPasswordTextWatcher(layout: TextInputLayout, minCharacters: Int): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val isValid = isPasswordValid(passwordInput)
                layout.isErrorEnabled = !isValid
                layout.error = if (!isValid) getString(R.string.register_error_password) else ""
                registerFormSubmit.isEnabled = isFormValid()
            }
        }
    }

    private fun isFormValid(): Boolean {
        return isFieldValid(firstNameInput, minCharacters)
                && isFieldValid(lastNameInput, minCharacters)
                && isEmailValid(emailInput)
                && isPasswordValid(passwordInput)
    }

    private fun isFieldValid(input: TextInputEditText, minLength: Int): Boolean {
        val value = input.text.toString().trim()
        return value.length >= minLength
    }

    private fun isEmailValid(input: TextInputEditText): Boolean {
        val email = input.text.toString().trim()
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(input: TextInputEditText): Boolean {
        val password = input.text.toString().trim()
        val minLength = 8
        val hasUpperCase = password.matches(Regex(".*[A-Z].*"))
        val hasLowerCase = password.matches(Regex(".*[a-z].*"))
        val hasDigit = password.matches(Regex(".*\\d.*"))
        val hasSpecialCharacter = password.matches(Regex(".*[^A-Za-z0-9].*"))
        return password.length >= minLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialCharacter
    }

    private fun registerUser() {
        if (isFormValid()) {
            val user = UserModel(
                firstNameInput.text.toString(),
                lastNameInput.text.toString(),
                emailInput.text.toString(),
                passwordInput.text.toString(),
                phoneNumberInput.text.toString()
            )
            UserSessionUtils.setUser(user)
            ToolsUtils.goToHome(this, true)
        } else {
            DialogUtils.showErrorDialog(this, getString(R.string.register_error_fields))
        }
    }

}