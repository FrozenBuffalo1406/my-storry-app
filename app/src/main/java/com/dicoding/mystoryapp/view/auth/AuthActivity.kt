package com.dicoding.mystoryapp.view.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.databinding.ActivityAuthBinding
import com.dicoding.mystoryapp.view.signin.SigninActivity
import com.dicoding.mystoryapp.view.singup.SignupActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this@AuthActivity, SigninActivity::class.java))

        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this@AuthActivity, SignupActivity::class.java))
        }
        playAnimation()
    }

    @SuppressLint("Recycle")
    private fun playAnimation() {
        val signin = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(400)
        val signup = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(400)
        val title = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(600)
        val btnAppear = AnimatorSet().apply {
            playTogether(signin, signup)
        }
        AnimatorSet().apply {
            playSequentially(title, btnAppear)
            start()
        }
    }
}