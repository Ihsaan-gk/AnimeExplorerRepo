package com.muhammedihsaan.animeexplorer.ui.animelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammedihsaan.animeexplorer.data.repository.AnimeRepository
import com.muhammedihsaan.animeexplorer.domain.model.Anime
import com.muhammedihsaan.animeexplorer.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<Anime>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Anime>>> = _uiState

    private var currentPage = 1
    private var isLoading = false
    private var isInitialLoadDone = false
    private val animeList = mutableListOf<Anime>()

    fun loadTopAnimeIfNeeded() {
        if (isInitialLoadDone) return
        isInitialLoadDone = true
        loadTopAnime()
    }

    fun loadTopAnime(loadNextPage: Boolean = false) {
        if (isLoading) return

        if (loadNextPage) {
            currentPage++
        } else {
            currentPage = 1
            animeList.clear()
        }

        viewModelScope.launch {
            isLoading = true
            _uiState.value = UiState.Loading

            val result = repository.getTopAnime(currentPage)

            result
                .onSuccess { list ->
                    animeList.addAll(list)
                    _uiState.value = UiState.Success(animeList)
                }
                .onFailure { error ->
                    _uiState.value =
                        UiState.Error(error.message ?: "Something went wrong")
                }

            isLoading = false
        }
    }
}
