package com.dicoding.mystoryapp.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.databinding.ActivityAuthBinding
import com.dicoding.mystoryapp.view.signin.SigninActivity
import com.dicoding.mystoryapp.view.singup.SignupActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this@AuthActivity, SigninActivity::class.java))
            finish()
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this@AuthActivity, SignupActivity::class.java))
            finish()
        }
    }


}