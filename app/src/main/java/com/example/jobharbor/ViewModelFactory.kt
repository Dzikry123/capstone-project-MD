package com.example.jobharbor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobharbor.data.JobRepository
import com.example.jobharbor.ui.screen.bookmark.BookmarkViewModel
import com.example.jobharbor.ui.screen.detail.DetailJobViewModel
import com.example.jobharbor.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: JobRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailJobViewModel::class.java)) {
            return DetailJobViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}