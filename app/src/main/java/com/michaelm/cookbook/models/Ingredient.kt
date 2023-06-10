package com.michaelm.cookbook.models


import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: Long,
    val name: String,
    val amount: Double,
    val unit: String,
    val image: String
)