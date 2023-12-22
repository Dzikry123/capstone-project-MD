package com.example.jobharbor.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobharbor.data.JobRepository
import com.example.jobharbor.model.OrderJob
import com.example.jobharbor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: JobRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderJob>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderJob>>>
        get() = _uiState

    fun getAllJobs() {
        viewModelScope.launch {
            repository.getAllJobs()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderLaptops ->
                    _uiState.value = UiState.Success(orderLaptops)
                }
        }
    }
}