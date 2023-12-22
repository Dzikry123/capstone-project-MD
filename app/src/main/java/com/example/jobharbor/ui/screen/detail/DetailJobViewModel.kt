package com.example.jobharbor.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobharbor.data.JobRepository
import com.example.jobharbor.model.Job
import com.example.jobharbor.model.OrderJob
import com.example.jobharbor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailJobViewModel(
    private val repository: JobRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderJob>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderJob>>
        get() = _uiState

    fun getJobById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderJobById(rewardId))
        }
    }

    fun addToBookmark(job: Job, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateOrderJob(job.id, isFavorite)
        }
    }
}