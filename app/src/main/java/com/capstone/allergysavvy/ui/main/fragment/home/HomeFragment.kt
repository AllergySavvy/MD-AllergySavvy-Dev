package com.capstone.allergysavvy.ui.main.fragment.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.databinding.FragmentHomeBinding
import com.capstone.allergysavvy.ui.adapter.FoodRecipeRandomAdapter
import com.capstone.allergysavvy.ui.adapter.IngredientRandomAdapter
import com.capstone.allergysavvy.ui.category.form.FormActivity
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDarkMode()
        checkUserAllergies()
        setupRecyclerView()
        setupObserver()


    }


    private fun setupRecyclerView() {
        homeViewModel.findFoodRandom()
        homeViewModel.findIngredientRandom()

        binding.rvRecentRecipes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentRecipes.adapter = FoodRecipeRandomAdapter()

        binding.rvRecentIngredient.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentIngredient.adapter = IngredientRandomAdapter()
    }

    private fun setupObserver() {
        homeViewModel.foodRecipeRandom.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> {
                    binding.progressBarRecentRecipes.visibility = View.GONE
                    showSnackBar(it.error)
                }

                Result.Loading -> binding.progressBarRecentRecipes.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBarRecentRecipes.visibility = View.GONE
                    (binding.rvRecentRecipes.adapter as FoodRecipeRandomAdapter).submitList(it.data)
                }
            }
        }

        homeViewModel.ingredientRandom.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> {
                    binding.progressBarRecentIngredients.visibility = View.VISIBLE
                    showSnackBar(it.error)
                }

                Result.Loading -> binding.progressBarRecentIngredients.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBarRecentIngredients.visibility = View.GONE
                    (binding.rvRecentIngredient.adapter as IngredientRandomAdapter).submitList(it.data)
                }
            }
        }
    }


    private fun checkUserAllergies() {
        homeViewModel.checkUserAllergies()
        if (homeViewModel.isUserAllergies.value == true) {
            val intent = Intent(requireContext(), FormActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun checkDarkMode() {
        val isDarkModeActive =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    == Configuration.UI_MODE_NIGHT_YES)

        if (isDarkModeActive) {
            binding.ivAppIcon.setImageResource(R.drawable.logo_allergysavvy_dark_mode_svg)
        } else {
            binding.ivAppIcon.setImageResource(R.drawable.logo_allergysavvy_svg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}