package com.michaelm.cookbook.service

import com.michaelm.cookbook.models.Recipe
import com.michaelm.cookbook.models.RecipeDetails
import com.michaelm.cookbook.models.RecipeResponse
import com.michaelm.cookbook.models.SearchRecipesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.michaelm.cookbook.BuildConfig


interface RecipeService {

    private val API_KEY: String
        get() = BuildConfig.SPOONACULAR_API_KEY

    @GET("recipes/random")
    suspend fun getRandomRecipes(@Query("apiKey") apiKey: String = API_KEY, @Query("number") number: Int = 50) : RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(@Path("id") id: Long, @Query("apiKey") apiKey: String = API_KEY) : RecipeDetails

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(@Path("id") id: Long, @Query("apiKey") apiKey: String = API_KEY) : List<Recipe>

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(@Query("apiKey") apiKey: String = API_KEY, @Query("query") searchParam: String, @Query("number") number: Int = 50) : SearchRecipesResponse

    companion object {
        private var service : RecipeService? = null

        fun get() : RecipeService {
            if(service == null) {
                service = Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RecipeService::class.java)
            }
            return service!!
        }
    }
}