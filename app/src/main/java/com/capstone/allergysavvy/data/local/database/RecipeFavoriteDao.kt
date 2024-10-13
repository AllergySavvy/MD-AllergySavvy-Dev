package com.capstone.allergysavvy.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoriteRecipe(recipeFavoriteEntity: RecipeFavoriteEntity)

    @Delete
    fun removeFavoriteRecipe(recipeFavoriteEntity: RecipeFavoriteEntity)

    @Query("SELECT * FROM RecipeFavoriteEntity WHERE `index` = :index")
    fun getRecipeFavoriteByIndex(index: Int): LiveData<RecipeFavoriteEntity>

    @Query("SELECT * FROM RecipeFavoriteEntity")
    fun getAllFavoriteRecipes(): LiveData<List<RecipeFavoriteEntity>>


}