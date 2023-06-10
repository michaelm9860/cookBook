package com.michaelm.cookbook.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipes(@PrimaryKey(autoGenerate = false) val id: Long, val image: String, val name: String)