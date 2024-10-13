package com.capstone.allergysavvy.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeFavoriteEntity(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "index")
    val index: Int,

    @ColumnInfo(name = "recipe_name")
    val recipeName: String? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,


    )