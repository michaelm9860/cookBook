package com.michaelm.cookbook.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelm.cookbook.R
import com.michaelm.cookbook.adapter.RecipeRvAdapter
import com.michaelm.cookbook.adapter.RecipeRvAdapterInterface
import com.michaelm.cookbook.databinding.FragmentRandomBinding
import com.michaelm.cookbook.viewmodels.RandomRecipeViewModel

class RandomRecipesFragment : Fragment(), RecipeRvAdapterInterface {


    private var _binding : FragmentRandomBinding? = null
    private val binding : FragmentRandomBinding get()= _binding!!

    lateinit var randomRecipeViewModel : RandomRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = RecipeRvAdapter(this, requireContext())

        binding.btnBackRandom.setOnClickListener{
            requireActivity().onBackPressed()
        }

        randomRecipeViewModel = ViewModelProvider(this)[RandomRecipeViewModel::class.java]

        randomRecipeViewModel.recipesLiveData.observe(viewLifecycleOwner) { recipes->
            adapter.setRecipes(recipes)
            binding.recipesRv.adapter = adapter

        }

        binding.recipesRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    randomRecipeViewModel.fetchMoreRecipes()
                    randomRecipeViewModel.recipesLiveData.observe(viewLifecycleOwner) { recipes->
                        adapter.setRecipes(recipes)
                        binding.recipesRv.adapter = adapter

                    }
                }


            }
        })

        

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRecipeClick(id: Long) {
        val bundle = Bundle()
        bundle.putLong("id", id)
        findNavController().navigate(R.id.action_mainFragment_to_recipeDetailsFragment, bundle)
    }

    fun getViewportWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

//    .viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//        override fun onGlobalLayout() {
//            // Retrieve the width of the ImageView
//            val widthInPixels =
//            // Do something with the width
//            Log.d("ImageViewWidth", "Width in pixels: $widthInPixels")
//
//            // Remove the listener to avoid redundant callbacks
//            imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//        }
//    })


}