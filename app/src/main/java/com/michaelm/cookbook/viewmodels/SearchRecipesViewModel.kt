package com.michaelm.cookbook.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelm.cookbook.models.Recipe
import com.michaelm.cookbook.service.RecipeService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchRecipesViewModel : ViewModel() {
    private val _recipesLiveData  = MutableLiveData<List<Recipe>>()

    val recipesLiveData : LiveData<List<Recipe>> = _recipesLiveData

    private var _searchParam : String = ""

    private val TAG = "SEARCH_RECIPES_VIEW_MODEL"

    fun setSearchParam(searchParam: String){
        _searchParam = searchParam
        fetchMoreRecipes()
    }

    private fun fetchMoreRecipes(){
        val searchParam = _searchParam.ifEmpty {
            return
        }
        try {
            val service  = RecipeService.get()

            viewModelScope.launch {

                val response = service.searchRecipes(searchParam = searchParam)
                Log.i(TAG, "fetchMoreRecipes: $response")
                _recipesLiveData.postValue(response.results)
            }
        } catch (e: HttpException){
            Log.e(TAG, "HTTP exception", e)

        }
    }
}