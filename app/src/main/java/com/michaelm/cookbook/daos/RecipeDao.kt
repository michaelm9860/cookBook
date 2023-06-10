package com.michaelm.cookbook.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michaelm.cookbook.entity.FavoriteRecipes


@Dao
interface RecipeDao {

    @Query("SELECT * from favorite_recipes")
    suspend fun getFavoriteRecipes() : List<FavoriteRecipes>

    @Insert
    suspend fun insertRecipeToFavorites(recipe: FavoriteRecipes)

    @Query("SELECT id, image, name from favorite_recipes WHERE id = :id")
    suspend fun selectRecipe(id:String) : List<FavoriteRecipes>


    @Delete
    suspend fun deleteRecipeFromFavorites(recipe: FavoriteRecipes)

}