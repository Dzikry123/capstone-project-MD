package com.example.jobharbor.data

import com.example.jobharbor.data.remote.JobApi
import com.example.jobharbor.data.responses.ResponseLogin
import com.example.jobharbor.data.responses.ResponseRegister

class AuthRepo(private val apiService: JobApi) {
    suspend fun registerRepo(name: String, email: String, password:String) : ResponseRegister {
        return apiService.register(name, email, password)
    }

    suspend fun loginRepo(email: String, password:String) : ResponseLogin {
        return apiService.login(email, password)
    }
}