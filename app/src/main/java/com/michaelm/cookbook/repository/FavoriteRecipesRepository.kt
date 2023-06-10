package com.michaelm.cookbook.repository

import androidx.lifecycle.LiveData
import com.michaelm.cookbook.daos.RecipeDao
import com.michaelm.cookbook.entity.FavoriteRecipes

class FavoriteRecipesRepository(private val dao: RecipeDao) {

    suspend fun getFavoriteRecipes() : List<FavoriteRecipes> {
       return dao.getFavoriteRecipes()
    }

    suspend fun insertRecipe(recipe: FavoriteRecipes)  {
        return dao.insertRecipeToFavorites(recipe)
    }
    suspend fun deleteRecipe(recipe: FavoriteRecipes)  {
        return dao.deleteRecipeFromFavorites(recipe)
    }
    suspend fun selectRecipe(recipeId:String) : List<FavoriteRecipes> {
        return dao.selectRecipe(recipeId)
    }

}