package com.example.jobharbor.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobharbor.data.JobRepository
import com.example.jobharbor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: JobRepository) :ViewModel() {
    private val _uiState: MutableStateFlow<UiState<BookmarkState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<BookmarkState>>
        get() = _uiState

    fun getAddedOrderLaptops() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderJobs()
                .collect { orderJob ->
                    _uiState.value = UiState.Success(BookmarkState(orderJob))
                }
        }
    }
}