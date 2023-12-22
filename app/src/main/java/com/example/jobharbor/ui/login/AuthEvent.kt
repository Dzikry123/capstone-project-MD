package com.example.jobharbor.ui.login

sealed class AuthEvent {

    object SaveAppEntry: AuthEvent()

}