package com.example.rateofcurrency.models.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rateofcurrency.models.entities.AuthDataRequest
import com.example.rateofcurrency.models.entities.SignalResponse
import com.example.rateofcurrency.models.entities.UserProfile
import com.example.rateofcurrency.network.modules.NetworkModulePartner
import com.example.rateofcurrency.network.modules.NetworkModulePeanut
import com.example.rateofcurrency.utilits.Const.Companion.KEY_TOKEN_PARTNER
import com.example.rateofcurrency.utilits.Const.Companion.KEY_TOKEN_PEANUT
import com.example.rateofcurrency.utilits.Const.Companion.KEY_USER_LOGIN
import com.example.rateofcurrency.utilits.Const.Companion.SHARED_PREF_NAME
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalSerializationApi
@ExperimentalTime
class GeneralViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPref =
        getApplication<Application>().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    private val _tokenPartner = MutableLiveData<String>()
    val tokenPartner: LiveData<String> = _tokenPartner

    private val _signalList = MutableLiveData<ArrayList<SignalResponse>>()
    val signalList: LiveData<ArrayList<SignalResponse>> = _signalList

    private val _dateFrom = MutableLiveData<LocalDateTime>()
    val dateFrom: LiveData<LocalDateTime> = _dateFrom

    private val _dateTo = MutableLiveData<LocalDateTime>()
    val dateTo: LiveData<LocalDateTime> = _dateTo

    private val _pair = MutableLiveData<String>()
    val pair: LiveData<String> = _pair

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> = _userProfile

    private val _lastNumPhone = MutableLiveData<String>()
    val lastNumPhone: LiveData<String> = _lastNumPhone

    private lateinit var authUserDate: AuthDataRequest

    private val _exceptionMessage = MutableLiveData<String>()
    val exceptionMessage: LiveData<String> = _exceptionMessage

    init {
        authUserDate = AuthDataRequest(login = getUserLogin(), token = getTokenPeanut())
    }

    @ExperimentalSerializationApi
    private val networkApiPartner = NetworkModulePartner().networkApiService

    @ExperimentalSerializationApi
    private val networkApiPeanut = NetworkModulePeanut().networkApiServicePeanut


    @ExperimentalTime
    @ExperimentalSerializationApi
    fun getSignal(
        authToken: String = tokenPartner.value ?: "",
        pairs: String = pair.value ?: "EURUSD",
        from: Long = getDateFromTimestamp(),
        to: Long = getDateToTimestamp()
    ) {

        viewModelScope.launch {
            _signalList.postValue(
                networkApiPartner.getSignalsArchive(
                    authToken = authToken,
                    pairs = pairs,
                    from = from,
                    to = to
                )
            )
        }
    }

    @ExperimentalSerializationApi
    fun getUserProfile(authData: AuthDataRequest = authUserDate) {
        viewModelScope.launch(exceptionHandlerHTTP) {
            _userProfile.postValue(networkApiPeanut.getAccInfo(authData))
        }
    }


    @ExperimentalSerializationApi
    fun getLastPhone(authData: AuthDataRequest = authUserDate) {
        viewModelScope.launch {
            _lastNumPhone.postValue(networkApiPeanut.getLastNumPhone(authData))
        }
    }

    fun setPair(pair: String) {
        _pair.postValue(pair)
    }

    fun setDateFrom(day: Int, month: Int, year: Int) {
        _dateFrom.postValue(
            LocalDateTime(year, month, day, 0, 0)
        )
    }

    fun setDateTo(day: Int, month: Int, year: Int) {
        _dateTo.postValue(
            LocalDateTime(year, month, day, 0, 0)
        )
    }

    fun getTokenPartner() {
        _tokenPartner.postValue(sharedPref.getString(KEY_TOKEN_PARTNER, ""))
    }

    private fun getTokenPeanut(): String {
        return sharedPref.getString(KEY_TOKEN_PEANUT, "") ?: ""
    }

    private fun getUserLogin(): Int {
        return sharedPref.getInt(KEY_USER_LOGIN, 0)
    }

    private fun getDateFromTimestamp(): Long =
        dateFrom.value?.toInstant(TimeZone.of("UTC+6"))?.epochSeconds ?: 1479860023


    private fun getDateToTimestamp(): Long =
        dateTo.value?.toInstant(TimeZone.of("UTC+6"))?.epochSeconds ?: 1480066860


    @ExperimentalTime
    private fun getDateToday(): Long = Clock.System.now().epochSeconds


    @ExperimentalTime
    private fun getDateYesterday(): Long = Clock.System.now().minus(Duration.days(1)).epochSeconds

    private val exceptionHandlerHTTP = CoroutineExceptionHandler { _, throwable ->

        when (throwable) {
            is HttpException -> {
                _exceptionMessage.postValue(" ${throwable.message()} ${throwable.response()?.body().toString()} ${throwable.response().toString()}"
                )
            }
        }
    }

}