package com.capstone.allergysavvy.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.allergysavvy.data.response.Video
import com.capstone.allergysavvy.databinding.ItemVideoBinding
import com.capstone.allergysavvy.utils.formatDuration
import com.capstone.allergysavvy.utils.formatViewsVideo

class VideoAdapter : PagingDataAdapter<Video, VideoAdapter.VideoViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoData = getItem(position)
        videoData?.let { holder.bind(it) }
    }

    class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            with(binding) {
                tvViewsVideo.text = formatViewsVideo(video.viewCount)
                tvTitleVideo.text = video.title
                tvChanelName.text = video.channelTitle
                tvDuration.text = formatDuration(video.duration)
                Glide.with(itemView.context)
                    .load(video.thumbnailUrl)
                    .into(ivThumbnailVideo)

                root.setOnClickListener {
                    openYoutubeVideo(video.id)
                }
            }
        }

        @SuppressLint("QueryPermissionsNeeded")
        private fun openYoutubeVideo(videoId: String) {
            val context = itemView.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
            intent.putExtra("force_fullscreen", true)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                val webIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))
                context.startActivity(webIntent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Video> =
            object : DiffUtil.ItemCallback<Video>() {
                override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
