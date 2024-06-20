package com.capstone.allergysavvy.ui.main.fragment.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.allergysavvy.databinding.FragmentIngredientBinding
import com.capstone.allergysavvy.ui.adapter.IngredientAdapter
import com.capstone.allergysavvy.ui.adapter.LoadingStateAdapter
import com.google.android.material.snackbar.Snackbar

class IngredientFragment : Fragment() {
    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!
    private val ingredientViewModel: IngredientViewModel by viewModels {
        IngredientViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val ingredientAdapter = IngredientAdapter()

        binding.rvIngredient.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvIngredient.adapter = ingredientAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                ingredientAdapter.retry()
            }
        )

        ingredientViewModel.ingredient.observe(viewLifecycleOwner) {
            ingredientAdapter.submitData(lifecycle, it)
        }

        ingredientAdapter.addLoadStateListener {
            binding.progressBarIngredient.isVisible = it.source.refresh is LoadState.Loading
            if (it.source.refresh is LoadState.Error) {
                val error = (it.source.refresh as LoadState.Error).error
                showSnackBar(error.message.toString())
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}