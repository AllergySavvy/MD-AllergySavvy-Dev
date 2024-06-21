package com.capstone.allergysavvy.ui.main.fragment.ingredient

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.allergysavvy.databinding.FragmentIngredientBinding
import com.capstone.allergysavvy.ui.adapter.IngredientAdapter
import com.capstone.allergysavvy.ui.adapter.LoadingStateAdapter
import com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.RecipeFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class IngredientFragment : Fragment() {
    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!
    private val ingredientViewModel: IngredientViewModel by viewModels {
        IngredientViewModelFactory.getInstance(requireContext())
    }
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var fab: FloatingActionButton

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
        setupFab()
        setupSearchBar()
    }

    private fun setupRecyclerView() {
        ingredientAdapter = IngredientAdapter()

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

        ingredientAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                toggleFabVisibility()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                toggleFabVisibility()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                toggleFabVisibility()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                toggleFabVisibility()
            }

            private fun toggleFabVisibility() {
                val selectedIngredients = ingredientAdapter.getSelectedIngredientsString()
                if (selectedIngredients.isNotEmpty()) {
                    showFab()
                } else {
                    hideFab()
                }
            }
        })
    }

    private fun setupFab() {
        fab = binding.fabSendIngredientFragmentIngredient
        fab.setOnClickListener {
            sendSelectedIngredients()
        }
    }

    private fun showFab() {
        if (!fab.isVisible) {
            fab.show()
            fab.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(null)
        }
    }

    private fun hideFab() {
        if (fab.isVisible) {
            fab.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    fab.hide()
                }
        }
    }

    private fun sendSelectedIngredients() {
        val selectedIngredients = ingredientAdapter.getSelectedIngredientsString()

        val intent = Intent(requireContext(), RecipeFragment::class.java).apply {
            putExtra(RecipeFragment.EXTRA_SELECTED_INGREDIENTS, selectedIngredients)
        }

        startActivity(intent)

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

    private fun setupSearchBar() {
        binding.edSearchIngredientFragmentIngredient.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ingredientViewModel.setQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
