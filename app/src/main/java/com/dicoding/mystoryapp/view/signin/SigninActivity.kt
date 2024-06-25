package com.dicoding.mystoryapp.view.signin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.factory.AuthViewModelFactory
import com.dicoding.mystoryapp.databinding.ActivitySignInBinding
import com.dicoding.mystoryapp.view.costumView.Button
import com.dicoding.mystoryapp.view.costumView.Button.Companion.STRING
import com.dicoding.mystoryapp.view.costumView.EmailInput
import com.dicoding.mystoryapp.view.costumView.PasswordInput
import com.dicoding.mystoryapp.view.main.MainActivity
import com.dicoding.mystoryapp.view.singup.SignupActivity

class SigninActivity : AppCompatActivity() {
    private val viewModel by viewModels<SigninViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    private lateinit var binding : ActivitySignInBinding
    private lateinit var myButton: Button
    private lateinit var emailInput: EmailInput
    private lateinit var passwordInput: PasswordInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupView()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myButton = binding.btnSignin
        emailInput = binding.etEmail
        passwordInput = binding.etPassword
        setMyButtonEnable()
        emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                setMyButtonEnable()
            }
        })

        binding.btnSignin.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.signin(email, password)
        }
        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this@SigninActivity, SignupActivity::class.java))
            finish()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.loginStatus.observe(this) { signinStatus ->
            when (signinStatus) {
                is SigninViewModel.SignInStatus.Success -> {
                    showDialog("Login successful: ${signinStatus.message}", true)
                }
                is SigninViewModel.SignInStatus.Error -> {
                    showDialog("Login failed: $signinStatus.message}", false)
                }
                is SigninViewModel.SignInStatus.NetworkError -> {
                    showDialog("Network error: ${signinStatus.message}", false)
                }
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
    private fun showDialog(message: String, shouldNavigate: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setTitle("Information")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                if (shouldNavigate) {
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

    private fun setMyButtonEnable() {
        val emailFilled = emailInput.text.toString().isNotEmpty()
        val passwordFilled = passwordInput.text.toString().isNotEmpty()
        myButton.isEnabled = emailFilled && passwordFilled
        STRING = "Sign In"
    }
}