package com.example.jobharbor.di

import com.example.jobharbor.data.JobRepository

object Injection {
    fun provideRepository(): JobRepository {
        return JobRepository.getInstance()
    }
}