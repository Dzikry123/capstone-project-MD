package com.example.jobharbor.preferences.usecases


import com.example.jobharbor.preferences.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}