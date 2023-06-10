package com.michaelm.cookbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.michaelm.cookbook.R
import com.michaelm.cookbook.adapter.IngredientRvAdapter
import com.michaelm.cookbook.adapter.RecipeRvAdapterInterface
import com.michaelm.cookbook.adapter.SimilarRecipeRvAdapter
import com.michaelm.cookbook.databinding.FragmentRecipeDetailsBinding
import com.michaelm.cookbook.entity.FavoriteRecipes
import com.michaelm.cookbook.viewmodels.RecipeDetailsViewModel
import com.squareup.picasso.Picasso

class RecipeDetailsFragment : Fragment(), RecipeRvAdapterInterface {

    private var _binding: FragmentRecipeDetailsBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel

    private lateinit var adapter: SimilarRecipeRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val id = bundle?.getLong("id")!!

        binding.btnBackDetails.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.similarRecipesRv.layoutManager = LinearLayoutManager(requireContext(),HORIZONTAL,false)
        adapter = SimilarRecipeRvAdapter(this)

        recipeDetailsViewModel = ViewModelProvider(this)[RecipeDetailsViewModel::class.java]

        recipeDetailsViewModel.setId(id)

        loadNewRecipe()




    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRecipeClick(id: Long) {
        recipeDetailsViewModel.setId(id)
        loadNewRecipe()
    }

    private fun loadNewRecipe(){
        recipeDetailsViewModel.isRecipeFavorite.observe(viewLifecycleOwner) {
            val resource = if(it) {
                R.drawable.baseline_favorite_24
            }else {
                R.drawable.baseline_favorite_border_24
            }
            binding.btnFavorite.setImageResource(resource)
        }
        recipeDetailsViewModel.detailsLiveData.observe(viewLifecycleOwner){recipeDetails ->
            Picasso.get().load(recipeDetails.image).into(binding.recipeImageDetails)
            binding.recipeNameDetails.text = recipeDetails.title
            binding.readyInMinutes.text = "Ready in ${recipeDetails.readyInMinutes} minutes."
            binding.servings.text = "${recipeDetails.servings} servings."
            val ingredientsAdapter = IngredientRvAdapter(recipeDetails.extendedIngredients)
            binding.ingredientsRv.adapter = ingredientsAdapter
            binding.btnFavorite.setOnClickListener {
                val resource = if(recipeDetailsViewModel.isRecipeFavorite.value!!) {
                    R.drawable.baseline_favorite_border_24
                } else {
                    R.drawable.baseline_favorite_24
                }
                (it as ImageView).setImageResource(resource)
                recipeDetailsViewModel.insertFavorite(FavoriteRecipes(recipeDetails.id, recipeDetails.image, recipeDetails.title))
            }



            binding.instructions.loadData(recipeDetails.instructions,"text/html", "UTF-8")
        }

        recipeDetailsViewModel.similarLiveData.observe(viewLifecycleOwner){ recipes ->
            adapter.setRecipes(recipes)
            binding.similarRecipesRv.adapter = adapter
        }
    }
}