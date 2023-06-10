package com.michaelm.cookbook.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelm.cookbook.service.RecipeService
import com.michaelm.cookbook.models.Recipe
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RandomRecipeViewModel : ViewModel() {

    private val _recipesLiveData  = MutableLiveData<List<Recipe>>()

    val recipesLiveData : LiveData<List<Recipe>> = _recipesLiveData

    private val TAG = "RECIPE_VIEW_MODEL"

    fun fetchMoreRecipes(){
        try {
            val service  = RecipeService.get()

            viewModelScope.launch {

                val response = service.getRandomRecipes()
                _recipesLiveData.postValue(response.recipes)
            }
        } catch (e: HttpException){
            Log.e(TAG, "HTTP exception", e)

        }
    }

    init {
        fetchMoreRecipes()
    }


}