package com.example.rateofcurrency.network.apiservice

import com.example.rateofcurrency.models.entities.AuthData
import com.example.rateofcurrency.models.entities.AuthDataRequest
import com.example.rateofcurrency.models.entities.AuthResponse
import com.example.rateofcurrency.models.entities.UserProfile
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkApiServicePeanut {

    @POST(AUTH_URL)
    suspend fun authenticate(@Body authData: AuthData): AuthResponse

    @POST(GET_ACC_INFO)
    suspend fun getAccInfo(@Body authData: AuthDataRequest): UserProfile

    @POST(GET_LAST_NUM_PHONE)
    suspend fun getLastNumPhone(@Body authData: AuthDataRequest): String

    companion object {
        const val BASE_URL_PEANUT = "https://peanut.ifxdb.com/"

        private const val AUTH_URL = "api/ClientCabinetBasic/IsAccountCredentialsCorrectAsync"
        private const val GET_ACC_INFO = "api/ClientCabinetBasic/GetAccountInformation"
        private const val GET_LAST_NUM_PHONE = "api/ClientCabinetBasic/GetLastFourNumbersPhone"
    }
}