package com.dicoding.mystoryapp.view.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mystoryapp.data.response.ListStoryItem
import com.dicoding.mystoryapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story: ListStoryItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_STORY, ListStoryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_STORY)
        }

        story?.let {
            bindData(it)
        }


    }

    private fun bindData(story: ListStoryItem) {
        Glide.with(binding.root)
            .load(story.photoUrl)
            .into(binding.ivImage)

        binding.tvTitle.text = story.name
        binding.tvDesc.text = story.description
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}