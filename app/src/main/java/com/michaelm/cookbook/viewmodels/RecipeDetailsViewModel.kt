package com.michaelm.cookbook.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.michaelm.cookbook.db.FavoriteRecipeDatabase
import com.michaelm.cookbook.repository.FavoriteRecipesRepository
import com.michaelm.cookbook.models.Recipe
import com.michaelm.cookbook.models.RecipeDetails
import com.michaelm.cookbook.entity.FavoriteRecipes
import com.michaelm.cookbook.service.RecipeService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "RECIPE_DETAILS_VIEW_MODEL"

    private val _detailsLiveData = MutableLiveData<RecipeDetails>()
    val detailsLiveData: LiveData<RecipeDetails> = _detailsLiveData

    private val _similarLiveData = MutableLiveData<List<Recipe>>()
    val similarLiveData: LiveData<List<Recipe>> = _similarLiveData

    private var _id: Long? = null
    private var favoriteRecipeRepository = FavoriteRecipesRepository(
        FavoriteRecipeDatabase.getInstance(application.applicationContext).dao())

    private val _isRecipeFavorite =  MutableLiveData(false)
    var isRecipeFavorite : LiveData<Boolean> =  _isRecipeFavorite


    fun setId(id: Long) {
        _id = id
        fetchData()
        fetchSimilarData()
    }


    fun insertFavorite(recipe: FavoriteRecipes) {
        viewModelScope.launch {

            val existingRecipe = favoriteRecipeRepository.selectRecipe(recipe.id.toString()).isNotEmpty()
            if(existingRecipe) {
                favoriteRecipeRepository.deleteRecipe(recipe)
                _isRecipeFavorite.postValue(false)
            } else {
                favoriteRecipeRepository.insertRecipe(recipe)
                _isRecipeFavorite.postValue(true)
            }
        }
    }

    private fun fetchData() {
        val id = _id ?: return // Return if id is null
        viewModelScope.launch {
            try {
                val service = RecipeService.get()

                val response = service.getRecipeInfo(id = id)
                _detailsLiveData.postValue(response.copy())

                val isFavorite = favoriteRecipeRepository.selectRecipe(response.id.toString()).isNotEmpty()
                _isRecipeFavorite.postValue(isFavorite)

            } catch (e: HttpException) {
                Log.e(TAG, "HTTP exception", e)
            }
        }
    }

    private fun fetchSimilarData(){
        val id = _id ?: return // Return if id is null

        viewModelScope.launch {
            try {
                val service = RecipeService.get()

                val response = service.getSimilarRecipes(id = id)
                _similarLiveData.postValue(response)
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP exception", e)
            }
        }
    }
}