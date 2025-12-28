package com.muhammedihsaan.animeexplorer.ui.animedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammedihsaan.animeexplorer.data.repository.AnimeRepository
import com.muhammedihsaan.animeexplorer.domain.model.AnimeDetail
import com.muhammedihsaan.animeexplorer.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<AnimeDetail>>(UiState.Idle)
    val uiState: StateFlow<UiState<AnimeDetail>> = _uiState

    fun loadAnimeDetail(animeId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = repository.getAnimeDetail(animeId)

            result
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }
                .onFailure {
                    _uiState.value =
                        UiState.Error(it.message ?: "Failed to load details")
                }
        }
    }
}
