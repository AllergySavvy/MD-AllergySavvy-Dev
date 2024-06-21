package com.capstone.allergysavvy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.data.response.DataItemFoodRecommend
import com.capstone.allergysavvy.databinding.ItemRecipesBinding

class RecommendationFoodAdapter(private val data: List<DataItemFoodRecommend?>) :
    RecyclerView.Adapter<RecommendationFoodAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val foodRecommend = data[position]
        if (foodRecommend != null) {
            holder.bind(foodRecommend)
        }
    }

    override fun getItemCount(): Int = data.size

    class MyViewHolder(
        private val binding: ItemRecipesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(foodRecommend: DataItemFoodRecommend) {
            binding.tvNameRecipes.text = foodRecommend.recipeName
            Glide.with(binding.root)
                .load(foodRecommend.imageUrl)
                .into(binding.ivRecipesImage)
        }
    }
}
