package com.michaelm.cookbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michaelm.cookbook.daos.RecipeDao
import com.michaelm.cookbook.entity.FavoriteRecipes


@Database(entities = [FavoriteRecipes::class], version = 4, exportSchema = false)
abstract class FavoriteRecipeDatabase : RoomDatabase() {

    abstract fun dao() : RecipeDao

    companion object {
        var instance : FavoriteRecipeDatabase? = null

        fun getInstance(context: Context) : FavoriteRecipeDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(context,
                    FavoriteRecipeDatabase::class.java,"recipe_db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

    }

}