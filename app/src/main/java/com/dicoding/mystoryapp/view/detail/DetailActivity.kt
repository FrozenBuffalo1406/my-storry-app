package com.dicoding.mystoryapp.view.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.factory.ViewModelFactory
import com.dicoding.mystoryapp.data.response.Story
import com.dicoding.mystoryapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val storyId = intent.getStringExtra("id")

        storyId?.let {
            viewModel.getDetail(it)
        }

        viewModel.story.observe(this) { storyResponse ->
            if (!storyResponse.error!!) {
                storyResponse.story?.let { showStoryDetails(it) }
            } else {
                "Gagal memuat cerita".showDialog()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showStoryDetails(story: Story) {
        binding.tvTitle.text = story.name
        binding.tvDesc.text = story.description
        Glide.with(this)
            .load(story.photoUrl)
            .into(binding.ivImage)

        binding.ivImage.transitionName = "image"
        binding.tvTitle.transitionName = "title"
        binding.tvDesc.transitionName = "desc"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun String.showDialog() {
        val builder = AlertDialog.Builder(this@DetailActivity)
        builder.setMessage(this)
            .setTitle("Information")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}