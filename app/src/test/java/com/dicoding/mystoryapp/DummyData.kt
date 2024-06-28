package com.dicoding.mystoryapp

import com.dicoding.mystoryapp.data.response.ListStoryItem

object DummyData {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "photoUrl $i",
                "description $i",
                "name $i",
                "2021-09-14T06:51.15.000Z",
                0.0,
                0.0
            )
            items.add(story)
        }
        return items
    }
}