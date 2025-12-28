package com.muhammedihsaan.animeexplorer.ui.animelist

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muhammedihsaan.animeexplorer.R
import com.muhammedihsaan.animeexplorer.databinding.FragmentAnimeListBinding
import com.muhammedihsaan.animeexplorer.ui.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : Fragment(R.layout.fragment_anime_list) {

    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeListViewModel by viewModels()
    private lateinit var adapter: AnimeListAdapter
    private var listState: Parcelable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAnimeListBinding.bind(view)

        setupRecyclerView()
        observeData()
        clickListener()
        viewModel.loadTopAnimeIfNeeded()
    }

    private fun setupRecyclerView() {
        adapter = AnimeListAdapter { anime ->
            listState = binding.rvAnime.layoutManager?.onSaveInstanceState() // Save scroll position BEFORE navigation
            val bundle = Bundle().apply {
                putInt("animeId", anime.id)
            }
            findNavController().navigate(
                R.id.action_list_to_detail,
                bundle
            )
        }

        binding.rvAnime.adapter = adapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.errorContainer.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorContainer.visibility = View.GONE
                        adapter.submitList(state.data) {
                            listState?.let {
                                binding.rvAnime.layoutManager
                                    ?.onRestoreInstanceState(it)
                            }
                        }
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorContainer.visibility = View.VISIBLE
                        binding.tvError.text = state.message
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun clickListener() {
        binding.btnRetry.setOnClickListener {
            viewModel.loadTopAnime()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
