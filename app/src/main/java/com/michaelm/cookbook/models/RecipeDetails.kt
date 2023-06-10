package com.michaelm.cookbook.models


data class RecipeDetails(val id: Long,
                         val title: String,
                         val readyInMinutes: Int,
                         val image: String,
                         val servings: Int,
                         val instructions: String,
                         val extendedIngredients: List<Ingredient>,

)


