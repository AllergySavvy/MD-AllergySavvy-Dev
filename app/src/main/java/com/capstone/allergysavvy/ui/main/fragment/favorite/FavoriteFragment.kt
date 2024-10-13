package com.capstone.allergysavvy.ui.main.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.allergysavvy.data.response.DataItemFoodRecommendationDetail
import com.capstone.allergysavvy.databinding.FragmentFavoriteBinding
import com.capstone.allergysavvy.ui.adapter.RecommendationFoodAdapter
import com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe.DetailRecipeViewModel
import com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe.DetailRecipesViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val detailRecipeViewModel: DetailRecipeViewModel by viewModels {
        DetailRecipesViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: FavoriteViewModelFactory =
            FavoriteViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        val favoriteAdapter = RecommendationFoodAdapter(detailRecipeViewModel)

        favoriteViewModel.getFavoriteRecipe().observe(viewLifecycleOwner) { favoriteRecipes ->
            binding.progressBarFavorite.visibility = View.GONE
            val items = arrayListOf<DataItemFoodRecommendationDetail>()
            favoriteRecipes.map {
                val item = DataItemFoodRecommendationDetail(
                    imageUrl = it.imageUrl,
                    recipeName = it.recipeName,
                    index = it.index
                )
                items.add(item)
            }
            favoriteAdapter.submitList(items)

            if (items.isEmpty()) {
                binding.ivErrorFavorite.visibility = View.VISIBLE
                binding.tvOopsFavorite.visibility = View.VISIBLE
                binding.tvMsgErrorFavorite.visibility = View.VISIBLE
            } else {
                binding.ivErrorFavorite.visibility = View.GONE
                binding.tvOopsFavorite.visibility = View.GONE
                binding.tvMsgErrorFavorite.visibility = View.GONE
            }
        }

        binding.rvFavoriteRecipes.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = favoriteAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    GridLayoutManager(requireActivity(), 2).orientation
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
