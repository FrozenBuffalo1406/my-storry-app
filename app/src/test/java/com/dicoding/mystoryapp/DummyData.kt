package com.dicoding.mystoryapp

import com.dicoding.mystoryapp.data.response.ListStoryItem

object DummyData {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "photoUrl $i",
                "description $i",
                "name $i",
            )
            items.add(story)
        }
        return items
    }
}