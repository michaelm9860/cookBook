package com.michaelm.cookbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelm.cookbook.R
import com.michaelm.cookbook.adapter.RecipeRvAdapter
import com.michaelm.cookbook.adapter.RecipeRvAdapterInterface
import com.michaelm.cookbook.databinding.FragmentSearchRecipesBinding
import com.michaelm.cookbook.viewmodels.SearchRecipesViewModel

class SearchRecipesFragment : Fragment(), RecipeRvAdapterInterface {

    private var _binding : FragmentSearchRecipesBinding? = null

    private val binding get() = _binding!!

    private lateinit var searchRecipesViewModel: SearchRecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchRecipesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeRvAdapter(this, requireContext())

        searchRecipesViewModel = ViewModelProvider(this)[SearchRecipesViewModel::class.java]

        binding.btnBackSearch.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSearch.setOnClickListener {
            searchRecipesViewModel.setSearchParam(
                binding.searchRecipes.text.toString()
            )
            searchRecipesViewModel.recipesLiveData.observe(viewLifecycleOwner){ recipes ->
                adapter.setRecipes(recipes)
                binding.searchRecipesRv.adapter = adapter
                if (recipes.isEmpty()){
                    binding.noSearchResults.visibility = View.VISIBLE
                }else{
                    binding.noSearchResults.visibility = View.GONE
                }
        }


        }
    }

    override fun onRecipeClick(id: Long) {
        val bundle = Bundle()
        bundle.putLong("id", id)
        findNavController().navigate(R.id.action_searchRecipesFragment_to_recipeDetailsFragment, bundle)
    }
}