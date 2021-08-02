package com.example.rateofcurrency.models.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rateofcurrency.models.entities.AuthData
import com.example.rateofcurrency.models.entities.AuthResponse
import com.example.rateofcurrency.network.modules.NetworkModulePartner
import com.example.rateofcurrency.network.modules.NetworkModulePeanut
import com.example.rateofcurrency.utilits.Const.Companion.exceptionHandler
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class AuthViewModel : ViewModel() {

    private val _authResponsePeanut = MutableLiveData<AuthResponse>()
    val authResponsePeanut: LiveData<AuthResponse> = _authResponsePeanut

    private var _authResponsePartner = MutableLiveData<String>()
    val authResponsePartner: LiveData<String> = _authResponsePartner

    private val _login = MutableLiveData<Int>()
    val login: LiveData<Int> = _login

    private val _pass = MutableLiveData<String>()
    val pass: LiveData<String> = _pass

    // Retrofit interfaces
    @ExperimentalSerializationApi
    private val networkApiPartner = NetworkModulePartner().networkApiService

    @ExperimentalSerializationApi
    private val networkApiPeanut = NetworkModulePeanut().networkApiServicePeanut


    @ExperimentalSerializationApi
    fun authInPeanut(authData: AuthData) {
        /** Retrofit + Coroutines асинхронный запрос аутентификации */

        viewModelScope.launch(exceptionHandler) {
            _authResponsePeanut.postValue(
                networkApiPeanut.authenticate(authData = authData)
            )
        }
    }

    @ExperimentalSerializationApi
    fun authInPartner(authData: AuthData) {
        viewModelScope.launch(exceptionHandler) {
            _authResponsePartner.postValue(
                networkApiPartner.authenticate(authData = authData)
            )
        }
    }

    fun setLogin(login: Int) {
        _login.postValue(login)
    }

    fun setPasswd(login: String) {
        _pass.postValue(login)
    }
}