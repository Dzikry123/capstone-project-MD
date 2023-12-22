package com.example.jobharbor.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.jobharbor.preferences.manager.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfilePreferences(context: Context) {

    // Inisialisasi DataStore
    val dataStore = context.dataStore

    val KEY_FULL_NAME = stringPreferencesKey("full_name")
    val KEY_EMAIL = stringPreferencesKey("email")
    val KEY_DATE_OF_BIRTH = stringPreferencesKey("date_of_birth")
    val KEY_PHONE_NUMBER = stringPreferencesKey("phone_number")
    val KEY_GENDER = stringPreferencesKey("gender")

    // Menyimpan data menggunakan DataStore
    suspend fun saveDataToDataStore(
        fullName: String,
        email: String,
        dateOfBirth: String,
        phoneNumber: String,
        gender: String
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_FULL_NAME] = fullName
            preferences[KEY_EMAIL] = email
            preferences[KEY_DATE_OF_BIRTH] = dateOfBirth
            preferences[KEY_PHONE_NUMBER] = phoneNumber
            preferences[KEY_GENDER] = gender
        }
    }

    // Membaca data dari DataStore
    val fullNameFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[KEY_FULL_NAME] ?: ""
    }

    val emailFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[KEY_EMAIL] ?: ""
    }

    val dateOfBirthFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[KEY_DATE_OF_BIRTH] ?: ""
    }

    val phoneNumberFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[KEY_PHONE_NUMBER] ?: ""
    }

    val genderFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[KEY_GENDER] ?: ""
    }

}