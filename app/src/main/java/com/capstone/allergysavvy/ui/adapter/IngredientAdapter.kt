package com.capstone.allergysavvy.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.R
import com.capstone.allergysavvy.data.response.DataItemIngredient
import com.capstone.allergysavvy.databinding.ItemIngredientBinding
import com.capstone.allergysavvy.utils.imageUrlIngredient

class IngredientAdapter :
    PagingDataAdapter<DataItemIngredient, IngredientAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private val selectedItems = mutableListOf<DataItemIngredient>()
    private val maxSelection = 3

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredient = getItem(position)
        if (ingredient != null) {
            holder.bind(ingredient, selectedItems.contains(ingredient))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun getSelectedIngredientsString(): String {
        return selectedItems.joinToString(", ") { it.ingredient.toString() }

    }

    inner class MyViewHolder(val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        toggleSelection(item)
                    }
                }
            }
        }

        fun bind(ingredient: DataItemIngredient, isSelected: Boolean) {
            binding.tvIngredientName.text = ingredient.ingredient
            val categoryIngredient = ingredient.category

            val urlImage = categoryIngredient?.let { imageUrlIngredient(it) }
            Glide.with(binding.root)
                .load(urlImage)
                .into(binding.ivImageIngredient)

            binding.cardIngredient.setCardBackgroundColor(
                if (isSelected) ContextCompat.getColor(binding.root.context, R.color.dark_green)
                else ContextCompat.getColor(binding.root.context, R.color.light_green)
            )

            binding.tvIngredientName.setTextColor(
                if (isSelected) ContextCompat.getColor(binding.root.context, R.color.white)
                else ContextCompat.getColor(binding.root.context, R.color.black)
            )
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun toggleSelection(item: DataItemIngredient) {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item)
            } else {
                if (selectedItems.size < maxSelection) {
                    selectedItems.add(item)
                }
            }
            notifyDataSetChanged()
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
