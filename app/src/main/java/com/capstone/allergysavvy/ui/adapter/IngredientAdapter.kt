package com.capstone.allergysavvy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.allergysavvy.data.response.DataItemIngredient
import com.capstone.allergysavvy.databinding.ItemIngredientBinding

class IngredientAdapter :
    PagingDataAdapter<DataItemIngredient, IngredientAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredient = getItem(position)
        if (ingredient != null) {
            holder.bind(ingredient)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    class MyViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: DataItemIngredient) {
            binding.tvIngredientName.text = ingredient.ingredient
            val categoryIngredient = ingredient.category
        }
    }


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DataItemIngredient> =
            object : DiffUtil.ItemCallback<DataItemIngredient>() {
                override fun areItemsTheSame(
                    oldItem: DataItemIngredient,
                    newItem: DataItemIngredient
                ): Boolean {
                    return oldItem.ingredient == newItem.ingredient
                }

                override fun areContentsTheSame(
                    oldItem: DataItemIngredient,
                    newItem: DataItemIngredient
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}