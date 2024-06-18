package com.capstone.allergysavvy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.allergysavvy.databinding.ItemLoadingBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.VideoLoadingStateViewHolder>() {
    override fun onBindViewHolder(
        holder: VideoLoadingStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): VideoLoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoLoadingStateViewHolder(binding, retry)
    }

    class VideoLoadingStateViewHolder(
        private val binding: ItemLoadingBinding, retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonTryAgainLoading.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvErrorMessageLoading.text = loadState.error.localizedMessage
            }
            binding.ivErrorMessage.isVisible = loadState is LoadState.Error
            binding.progressBarLoading.isVisible = loadState is LoadState.Loading
            binding.tvOopsMessage.isVisible = loadState is LoadState.Error
            binding.tvErrorMessageLoading.isVisible = loadState is LoadState.Error
        }

    }
}