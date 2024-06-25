package com.dicoding.mystoryapp.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.mystoryapp.data.response.ListStoryItem

@Database(entities = [ListStoryItem::class, RemoteKeys::class],
    version = 1,
//    autoMigrations =
//    [
//    AutoMigration(from = 1, to = 2),
//    ],
    exportSchema = false
) abstract class StoryDatabase: RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE : StoryDatabase? = null
        fun getDatabase(context:Context) : StoryDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                StoryDatabase::class.java,
                "story_database"
            ).build().also { INSTANCE = it }
        }
    }
}
