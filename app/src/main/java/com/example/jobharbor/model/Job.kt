package com.example.jobharbor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey val id: Long,
    val logo: Int,
    val bgImg: Int,
    val companyName: String,
    val jobTitle: String,
    val location: String,
    val category: List<String>,
    val salary: String,
    val isFavorite: Boolean,
    val description: String,
    val requirements: String,
    val companyProfile: String,

)

