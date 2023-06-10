package com.michaelm.cookbook.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelm.cookbook.R
import com.michaelm.cookbook.databinding.RecipeItemBinding
import com.michaelm.cookbook.models.Recipe
import com.squareup.picasso.Picasso

class RecipeRvAdapter(
    private val recipeRvAdapterInterface: RecipeRvAdapterInterface,
    private val context: Context
) : RecyclerView.Adapter<RecipeRvAdapter.RecipeViewHolder>() {

    private var recipes:List<Recipe> = listOf()

    fun setRecipes(recipes: List<Recipe>){
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

        holder.binding.recipeName.text  = recipe.title



        val viewportWidth = getViewportWidth()
        val layoutParams = holder.binding.recipeImage.layoutParams
        layoutParams.width = viewportWidth
        holder.binding.recipeImage.layoutParams = layoutParams

        Picasso.get().load(recipe.image).error(R.drawable.food_icon).into(holder.binding.recipeImage)

        holder.binding.recipeContainer.minWidth = getViewportWidth()



        holder.binding.recipeImage.setOnClickListener{
            recipeRvAdapterInterface.onRecipeClick(recipe.id)
        }
    }

    class RecipeViewHolder(
        val binding: RecipeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private fun getViewportWidth(): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }
}
