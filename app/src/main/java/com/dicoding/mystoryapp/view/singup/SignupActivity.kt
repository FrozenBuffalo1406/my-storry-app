package com.dicoding.mystoryapp.view.singup

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.factory.AuthViewModelFactory
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.data.api.AuthConfig
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.repository.AuthRepository
import com.dicoding.mystoryapp.databinding.ActivitySignUpBinding
import com.dicoding.mystoryapp.view.costumView.Button
import com.dicoding.mystoryapp.view.costumView.Button.Companion.STRING
import com.dicoding.mystoryapp.view.costumView.EmailInput
import com.dicoding.mystoryapp.view.costumView.PasswordInput
import com.dicoding.mystoryapp.view.main.MainActivity
import com.dicoding.mystoryapp.view.signin.SigninActivity

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignupViewModel> {
        AuthViewModelFactory(
            AuthRepository(AuthConfig.getApiService(getToken()), UserPreference.getInstance(this))
        )
    }

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN, "") ?: ""
    }

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var myButton: Button
    private lateinit var emailInput: EmailInput
    private lateinit var passwordInput: PasswordInput

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        myButton = binding.btnSignup
        emailInput = binding.etEmail
        passwordInput = binding.etPassword
        myButton.linkEditText(emailInput)
        myButton.linkEditText(passwordInput)
        STRING = getString(R.string.sign_up)

        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.signup(name, email, password)
        }
        binding.tvSignin.setOnClickListener {
            startActivity(Intent(this@SignupActivity, SigninActivity::class.java ))
            finish()
        }
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.signUpStatus.observe(this) { signUpStatus ->
            when (signUpStatus) {
                is SignupViewModel.SignUpStatus.Success -> {
                    true.showDialog("Sign up successful: ${signUpStatus.message}")
                }
                is SignupViewModel.SignUpStatus.Error ->  {
                    false.showDialog("Sign up failed: ${signUpStatus.message}")
                }
                else -> {}
            }
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun Boolean.showDialog(message: String) {
        val builder = AlertDialog.Builder(this@SignupActivity)
        builder.setMessage(message)
            .setTitle("Information")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                if (this) {
                    Handler(Looper.getMainLooper()).postDelayed({
                       navigateToMainView()
                    }, 2000)
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun navigateToMainView() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        const val NAME = "UserPreference"
        const val TOKEN = "token"
    }
}