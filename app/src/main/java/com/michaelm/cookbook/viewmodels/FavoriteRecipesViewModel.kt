package com.michaelm.cookbook.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.michaelm.cookbook.db.FavoriteRecipeDatabase
import com.michaelm.cookbook.entity.FavoriteRecipes
import com.michaelm.cookbook.repository.FavoriteRecipesRepository
import kotlinx.coroutines.launch
import java.sql.SQLException

class FavoriteRecipesViewModel(application: Application) : AndroidViewModel(application) {
    private val _favoriteRecipesLiveData  = MutableLiveData<List<FavoriteRecipes>>()

    val favoriteRecipesLiveData : LiveData<List<FavoriteRecipes>> = _favoriteRecipesLiveData

    private var favoriteRecipeRepository = FavoriteRecipesRepository(
        FavoriteRecipeDatabase.getInstance(application.applicationContext).dao())

    private val TAG = "FAVORITE_RECIPES_VIEW_MODEL"

    fun fetchFavoriteRecipes(){
        viewModelScope.launch {
            try {
                val recipes = favoriteRecipeRepository.getFavoriteRecipes()
                _favoriteRecipesLiveData.postValue(recipes)
            }catch (e: SQLException){
                Log.e(TAG, "SQL exception", e)
            }
        }
    }

    init {
        fetchFavoriteRecipes()
    }
}