package com.michaelm.cookbook.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelm.cookbook.R
import com.michaelm.cookbook.databinding.RecipeItemBinding
import com.michaelm.cookbook.entity.FavoriteRecipes
import com.squareup.picasso.Picasso

class FavoriteRecipesRvAdapter(
    private val recipeRvAdapterInterface: RecipeRvAdapterInterface,
    private val context: Context
) : RecyclerView.Adapter<FavoriteRecipesRvAdapter.RecipeViewHolder>(){
    private var recipes:List<FavoriteRecipes> = listOf()

    fun setRecipes(recipes: List<FavoriteRecipes>){
        this.recipes = recipes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int = recipes.size


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.binding.recipeName.text  = recipe.name

        Picasso.get().load(recipe.image).error(R.drawable.food_icon).into(holder.binding.recipeImage)

        holder.binding.recipeImage.setOnClickListener{
            recipeRvAdapterInterface.onRecipeClick(recipe.id)
        }
        holder.binding.recipeContainer.minWidth = getViewportWidth()

    }

    class RecipeViewHolder(
        val binding: RecipeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private fun getViewportWidth(): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }
}