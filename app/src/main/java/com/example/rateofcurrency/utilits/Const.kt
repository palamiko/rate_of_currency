package com.example.rateofcurrency.utilits

import android.system.ErrnoException
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException

class Const {

    companion object {

        const val SHARED_PREF_NAME: String = "USER_DATA_ALL"  // Название файла настроек
        const val KEY_USER_LOGIN: String = "USER_LOGIN"
        const val KEY_USER_PASSWD: String = "USER_PASSWD"
        const val KEY_TOKEN_PEANUT: String = "TOKEN_PEANUT"
        const val KEY_TOKEN_PARTNER: String = "TOKEN_PARTNER"

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

            val errorTextId = when (throwable) {
                is ConnectException, is ErrnoException -> "Нет интернета"
                is IOException, is HttpException -> "Ошибка интернет соединения"
                is SerializationException -> "Ошибка сериализации"
                else -> "Неизвестная ошибка"
            }
            Log.d("Coroutine Fail", errorTextId)
        }
    }
}