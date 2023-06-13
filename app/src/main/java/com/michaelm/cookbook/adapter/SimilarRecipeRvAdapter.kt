package com.michaelm.cookbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelm.cookbook.R
import com.michaelm.cookbook.databinding.RecipeItemSimilarBinding
import com.michaelm.cookbook.models.Recipe
import com.squareup.picasso.Picasso

class SimilarRecipeRvAdapter(
    private val recipeRvAdapterInterface: RecipeRvAdapterInterface,

) : RecyclerView.Adapter<SimilarRecipeRvAdapter.RecipeViewHolder>() {

    private var recipes:List<Recipe> = listOf()

    fun setRecipes(recipes: List<Recipe>){
        this.recipes = recipes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecipeItemSimilarBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]


        Picasso.get().load("https://spoonacular.com/recipeImages/${recipe.id}-90x90.jpg").error(R.drawable.food_icon).into(holder.binding.recipeSimilarImage)

        holder.binding.recipeSimilarCard.setOnClickListener {
            recipeRvAdapterInterface.onRecipeClick(recipe.id)
        }

      //  holder.binding.recipeSimilarName.maxWidth = holder.binding.recipeSimilarImage.width

        holder.binding.recipeSimilarName.text = recipe.title

    }

    override fun getItemCount(): Int = recipes.size


    class RecipeViewHolder(
        val binding : RecipeItemSimilarBinding
    ) : RecyclerView.ViewHolder(binding.root)


}