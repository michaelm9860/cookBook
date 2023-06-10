package com.michaelm.cookbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelm.cookbook.databinding.IngredientItemBinding
import com.michaelm.cookbook.models.Ingredient
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class IngredientRvAdapter(private val ingredients : List<Ingredient>) : RecyclerView.Adapter<IngredientRvAdapter.IngredientViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]

        holder.binding.ingredientName.text = ingredient.name
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_250x250/${ingredient.image}").into(holder.binding.ingredientImage)
        val df = DecimalFormat("#.##")
        holder.binding.ingredientAmount.text = "${df.format(ingredient.amount)} ${ingredient.unit}"
    }

    class IngredientViewHolder(
        val binding : IngredientItemBinding
    ) : RecyclerView.ViewHolder(binding.root)


}