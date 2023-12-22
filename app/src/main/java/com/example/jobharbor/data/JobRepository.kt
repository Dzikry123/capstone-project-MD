package com.example.jobharbor.data

import com.example.jobharbor.model.FakeJobResource
import com.example.jobharbor.model.OrderJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class JobRepository() {


    private val orderJobs = mutableListOf<OrderJob>()

    init {
        if (orderJobs.isEmpty()) {
            FakeJobResource.dummyJobs.forEach {
                orderJobs.add(OrderJob(it, false))
            }
        }
    }

    fun getAllJobs(): Flow<List<OrderJob>> {
        return flowOf(orderJobs)
    }

    fun getOrderJobById(jobId: Long): OrderJob {
        return orderJobs.first {
            it.job.id == jobId
        }
    }

    fun updateOrderJob(jobId: Long, isFavorite: Boolean): Flow<Boolean> {
        val index = orderJobs.indexOfFirst { it.job.id == jobId }
        val result = if (index >= 0) {
            val orderJob = orderJobs[index]
            orderJobs[index] = orderJob.copy(job = orderJob.job, isFavorite = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderJobs(): Flow<List<OrderJob>> {
        return getAllJobs()
            .map { orderJobs ->
                orderJobs.filter { orderJob ->
                    orderJob.isFavorite
                }
            }
    }

    companion object {
        @Volatile
        private var instance: JobRepository? = null

        fun getInstance(): JobRepository =
            instance ?: synchronized(this) {
                JobRepository().apply {
                    instance = this
                }
            }
    }
}
