package com.example.jobharbor.data.responses

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null
)
