package com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.allergysavvy.R


class RecipeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    companion object {
        const val EXTRA_SELECTED_INGREDIENTS = "extra_selected_ingredients"
    }


}