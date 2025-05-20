package com.example.mockotisample.repository

import androidx.lifecycle.MutableLiveData
import com.example.mockotisample.api.APIService
import com.example.mockotisample.model.PokedexResponse
import com.example.mockotisample.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: APIService) {

    private val _pokLiveData = MutableLiveData<NetworkResult<PokedexResponse>>()
    val pokLiveData get() = _pokLiveData

    suspend fun getPokemon() {
        _pokLiveData.postValue(NetworkResult.Loading())
        val response = apiService.getPokemon()
        if (response.isSuccessful && response.body() != null) {
            _pokLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _pokLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _pokLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}