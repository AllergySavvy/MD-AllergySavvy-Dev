package com.capstone.allergysavvy.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecipeFavoriteEntity::class], version = 1)
abstract class RecipeFavoriteDatabase : RoomDatabase() {
    abstract fun recipeFavoriteDao(): RecipeFavoriteDao

    companion object {
        @Volatile
        private var INSTACE: RecipeFavoriteDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): RecipeFavoriteDatabase {
            if (INSTACE == null) {
                synchronized(RecipeFavoriteDatabase::class.java) {
                    INSTACE = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeFavoriteDatabase::class.java, "recipe_favorite_database"
                    )
                        .build()
                }
            }
            return INSTACE as RecipeFavoriteDatabase
        }
    }
}