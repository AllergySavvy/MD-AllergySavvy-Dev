package com.capstone.allergysavvy.ui.main.fragment.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.allergysavvy.data.Result
import com.capstone.allergysavvy.databinding.FragmentVideoBinding
import com.capstone.allergysavvy.di.Injection
import com.capstone.allergysavvy.ui.adapter.VideoAdapter
import com.google.android.material.snackbar.Snackbar

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
        checkVideo()
        setupRecyclerView()
        searchVideo()
    }

    private fun checkVideo() {
        if (videoViewModel.videos.value == null) {
            videoViewModel.searchVideo("How To Make Healthy Food Recipes")
        }
    }

    private fun setupRecyclerView() {
        val videoAdapter = VideoAdapter()
        binding.rvVideoYoutube.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun searchVideo() {
        videoViewModel.videos.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBarVideo.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressBarVideo.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        "Error finding video: ${result.error}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Result.Success -> {
                    binding.progressBarVideo.visibility = View.GONE
                    val videoAdapter = binding.rvVideoYoutube.adapter as VideoAdapter
                    videoAdapter.submitList(result.data)
                }
            }
        }

        binding.edSearchRecipeVideo.setOnClickListener {
            val searchQuery = "${binding.edSearchRecipeVideo.text.toString()} + tutorial recipes"
            if (searchQuery.isNotEmpty()) {
                videoViewModel.searchVideo(searchQuery)
            } else {
                Snackbar.make(
                    binding.root,
                    "Please enter a recipe name",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
