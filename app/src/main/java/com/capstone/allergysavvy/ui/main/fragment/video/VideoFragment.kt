package com.capstone.allergysavvy.ui.main.fragment.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.allergysavvy.databinding.FragmentVideoBinding
import com.capstone.allergysavvy.di.Injection
import com.capstone.allergysavvy.ui.adapter.VideoAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private val videoRepository = Injection.videoRepository()
    private val videoViewModel: VideoViewModel by viewModels {
        VideoViewModelFactory.getInstance(videoRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        searchVideo()
    }

    private fun setupRecyclerView() {
        val videoAdapter = VideoAdapter()
        binding.rvVideoYoutube.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            videoViewModel.searchVideo("How To Make Healthy Food Recipes").asFlow()
                .collectLatest { pagingData ->
                    videoAdapter.submitData(pagingData)
                }
        }
    }

    private fun searchVideo() {
        binding.edSearchRecipeVideo.setOnClickListener {
            val searchQuery =
                "${binding.edSearchRecipeVideo.text.toString()} + tutorial food recipes"
            if (searchQuery.isNotEmpty()) {
                lifecycleScope.launch {
                    videoViewModel.searchVideo(searchQuery).asFlow().collectLatest { pagingData ->
                        (binding.rvVideoYoutube.adapter as VideoAdapter).submitData(pagingData)
                    }
                }
            } else {
                Snackbar.make(binding.root, "Please enter a search query", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
