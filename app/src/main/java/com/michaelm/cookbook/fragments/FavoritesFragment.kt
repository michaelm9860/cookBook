package com.michaelm.cookbook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelm.cookbook.R
import com.michaelm.cookbook.adapter.FavoriteRecipesRvAdapter
import com.michaelm.cookbook.adapter.RecipeRvAdapterInterface
import com.michaelm.cookbook.databinding.FragmentFavoritesBinding
import com.michaelm.cookbook.viewmodels.FavoriteRecipesViewModel


class FavoritesFragment : Fragment(), RecipeRvAdapterInterface {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!

    private lateinit var favoriteRecipeViewModel : FavoriteRecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        getFavorites()

        binding.btnBackFavorites.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun getFavorites(){
        val adapter = FavoriteRecipesRvAdapter(this, requireContext())

        favoriteRecipeViewModel = ViewModelProvider(this)[FavoriteRecipesViewModel::class.java]

        favoriteRecipeViewModel.favoriteRecipesLiveData.observe(viewLifecycleOwner) { recipes->
            if (recipes.isNotEmpty()){
                adapter.setRecipes(recipes)
                binding.FavoriteRecipesRv.adapter = adapter
            }else{
                binding.noFavorites.visibility = View.VISIBLE
                adapter.setRecipes(recipes)
                binding.FavoriteRecipesRv.adapter = adapter
            }

        }
    }

    override fun onResume() {
        super.onResume()
        favoriteRecipeViewModel.fetchFavoriteRecipes()
        getFavorites()
    }

    override fun onRecipeClick(id: Long) {
        val bundle = Bundle()
        bundle.putLong("id", id)
        findNavController().navigate(R.id.action_favoritesFragment_to_recipeDetailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}