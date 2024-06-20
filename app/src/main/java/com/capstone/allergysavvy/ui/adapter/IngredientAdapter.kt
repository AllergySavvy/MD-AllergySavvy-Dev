package com.capstone.allergysavvy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.allergysavvy.data.response.DataItemIngredientRandom
import com.capstone.allergysavvy.databinding.ItemIngredientBinding

class IngredientAdapter :
    ListAdapter<DataItemIngredientRandom, IngredientAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val randomIngredient = getItem(position)
        holder.bind(randomIngredient)

        holder.itemView.setOnClickListener {
            val ingredientName = randomIngredient.ingredient

        }
    }

    class MyViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(randomIngredient: DataItemIngredientRandom) {
            binding.tvIngredientName.text = randomIngredient.ingredient
            val categoryIngredient = randomIngredient.category
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DataItemIngredientRandom> =
            object : DiffUtil.ItemCallback<DataItemIngredientRandom>() {
                override fun areItemsTheSame(
                    oldItem: DataItemIngredientRandom,
                    newItem: DataItemIngredientRandom
                ): Boolean {
                    return oldItem.index == newItem.index
                }

                override fun areContentsTheSame(
                    oldItem: DataItemIngredientRandom,
                    newItem: DataItemIngredientRandom
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}