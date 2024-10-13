package com.capstone.allergysavvy.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.response.DataItemFoodRandom
import com.capstone.allergysavvy.databinding.ItemRecipesBinding
import com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe.DetailRecipeActivity
import com.capstone.allergysavvy.ui.main.fragment.ingredient.recipe.detailrecipe.DetailRecipeViewModel

class FoodRecipeRandomAdapter(
    private val detailRecipeViewModel: DetailRecipeViewModel
) :
    ListAdapter<DataItemFoodRandom, FoodRecipeRandomAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val foodRandom = getItem(position)
        holder.bind(foodRandom)

        holder.itemView.setOnClickListener {
            val foodIndex = foodRandom.index
            val foodName = foodRandom.recipeName
            val foodImageUrl = foodRandom.imageUrl
            val intentIndex =
                Intent(holder.itemView.context, DetailRecipeActivity::class.java).apply {
                    putExtra(DetailRecipeActivity.EXTRA_INDEX, foodIndex)
                    putExtra(DetailRecipeActivity.EXTRA_RECIPES_NAME, foodName)
                    putExtra(DetailRecipeActivity.EXTRA_IMAGE_URL, foodImageUrl)
                }

            holder.itemView.context.startActivity(intentIndex)
        }
    }

    inner class MyViewHolder(private val binding: ItemRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodRandom: DataItemFoodRandom) {
            binding.tvNameRecipes.text = foodRandom.recipeName
            Glide.with(binding.root)
                .load(foodRandom.imageUrl)
                .into(binding.ivRecipesImage)

            foodRandom.index?.let {
                detailRecipeViewModel.isFavoriteRecipe(it).observeForever { isFavorite ->
                    if (isFavorite) {
                        binding.ivFavorite.setImageResource(R.drawable.fill_favorite)
                    } else {
                        binding.ivFavorite.setImageResource(R.drawable.favorite)
                    }
                }
            }
        }


    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DataItemFoodRandom> =
            object : DiffUtil.ItemCallback<DataItemFoodRandom>() {
                override fun areItemsTheSame(
                    oldItem: DataItemFoodRandom,
                    newItem: DataItemFoodRandom
                ): Boolean {
                    return oldItem.index == newItem.index
                }

                override fun areContentsTheSame(
                    oldItem: DataItemFoodRandom,
                    newItem: DataItemFoodRandom
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}