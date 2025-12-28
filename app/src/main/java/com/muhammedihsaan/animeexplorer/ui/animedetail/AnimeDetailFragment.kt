package com.muhammedihsaan.animeexplorer.ui.animedetail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.muhammedihsaan.animeexplorer.R
import com.muhammedihsaan.animeexplorer.databinding.FragmentAnimeDetailBinding
import com.muhammedihsaan.animeexplorer.domain.model.AnimeDetail
import com.muhammedihsaan.animeexplorer.ui.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailFragment : Fragment(R.layout.fragment_anime_detail) {

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeDetailViewModel by viewModels()

    private var player: ExoPlayer? = null

    private val animeId: Int by lazy {
        requireArguments().getInt("animeId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAnimeDetailBinding.bind(view)

        viewModel.loadAnimeDetail(animeId)
        observeDetail()
        setupBackPressHandler()
    }

    private fun observeDetail() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        bindAnime(state.data)
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = state.message
                    }

                    UiState.Idle -> Unit
                }
            }
        }
    }

    private fun bindAnime(anime: AnimeDetail) {
        binding.tvTitle.text = anime.title
        binding.tvSynopsis.text = anime.synopsis ?: "No description available"
        binding.tvEpisodes.text = "Episodes: ${anime.episodes ?: "N/A"}"
        binding.tvRating.text = "Rating: ${anime.rating ?: "N/A"}"
        binding.tvGenres.text = anime.genres?.joinToString(", ")

        Glide.with(this).load(anime.imageUrl).into(binding.imgPoster)

        handleVideoPlayback(
            videoUrl = anime.videoUrl, embedUrl = anime.trailerUrl
        )
    }

    private fun handleVideoPlayback(
        videoUrl: String?, embedUrl: String?
    ) {
        when {
            !videoUrl.isNullOrBlank() -> {
                playVideoInPlayer(videoUrl)
            }

            !embedUrl.isNullOrBlank() -> {
                showEmbeddedVideoOnClick(embedUrl)
            }

            else -> {
                showPosterOnly()
            }
        }
    }

    private fun showEmbeddedVideoOnClick(embedUrl: String) {
        binding.imgPoster.visibility = View.VISIBLE
        binding.imgPlay.visibility = View.VISIBLE
        binding.playerView.visibility = View.GONE
        binding.webViewTrailer.visibility = View.GONE

        binding.flPoster.setOnClickListener {
            binding.imgPoster.visibility = View.GONE
            binding.imgPlay.visibility = View.GONE
            binding.playerView.visibility = View.GONE
            binding.webViewTrailer.visibility = View.VISIBLE

            setupWebView(embedUrl)
        }
    }

    private fun setupWebView(url: String) {
        binding.webViewTrailer.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()

            loadUrl(url)
        }
    }

    private fun playVideoInPlayer(videoUrl: String) {
        binding.playerView.visibility = View.GONE
        binding.webViewTrailer.visibility = View.GONE
        binding.imgPoster.visibility = View.VISIBLE
        binding.imgPlay.visibility = View.VISIBLE

        binding.flPoster.setOnClickListener {
            binding.playerView.visibility = View.VISIBLE
            binding.webViewTrailer.visibility = View.GONE
            binding.imgPoster.visibility = View.GONE

            player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
                binding.playerView.player = exoPlayer
                exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl))
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
        }
    }

    private fun setupBackPressHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner
        ) {
            if (binding.webViewTrailer.isVisible) {
                binding.webViewTrailer.apply {
                    loadUrl("about:blank")
                    stopLoading()
                    clearHistory()
                    onPause()
                }

                binding.webViewTrailer.visibility = View.GONE
                binding.imgPoster.visibility = View.VISIBLE
                binding.imgPlay.visibility = View.VISIBLE
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun showPosterOnly() {
        binding.playerView.visibility = View.GONE
        binding.webViewTrailer.visibility = View.GONE
        binding.imgPoster.visibility = View.VISIBLE
        binding.imgPlay.visibility = View.GONE
        binding.flPoster.setOnClickListener(null)
    }

    private fun resumePlayback() {
        if (binding.webViewTrailer.isVisible) {
            binding.webViewTrailer.onResume()
        }

        player?.playWhenReady = true
    }

    private fun pausePlayback() {
        if (binding.webViewTrailer.isVisible) {
            binding.webViewTrailer.onPause()
        }

        player?.pause()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onResume() {
        super.onResume()
        resumePlayback()
    }

    override fun onPause() {
        pausePlayback()
        super.onPause()
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}