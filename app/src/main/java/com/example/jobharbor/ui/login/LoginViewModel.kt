package com.example.jobharbor.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobharbor.data.AuthRepo
import com.example.jobharbor.data.responses.ResponseLogin
import com.example.jobharbor.data.responses.ResponseRegister
import com.example.jobharbor.preferences.usecases.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepo,  private val appEntryUseCases: AppEntryUseCases
)
    : ViewModel()
{

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.SaveAppEntry ->{
                saveUserEntry()
            }
        }
    }

    private fun saveUserEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }


    suspend fun loginVM(email: String, password: String): ResponseLogin {
        val response = authRepo.loginRepo(email, password)
        return response
    }

    suspend fun registerVM(name: String, email: String, password: String): ResponseRegister {
        val response = authRepo.registerRepo(name, email, password)
        return response
    }
}

