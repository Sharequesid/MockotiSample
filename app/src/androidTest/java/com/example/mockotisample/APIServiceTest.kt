package com.example.mockotisample

import androidx.multidex.BuildConfig
import com.example.mockotisample.api.APIService
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var baseUrl: String
    lateinit var apiService: APIService

    @Before
    fun setup(){

        mockWebServer = MockWebServer()
        baseUrl = mockWebServer.url("").toString()


        val clientBuilder = OkHttpClient.Builder()

        //if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                ))
        //}


        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(APIService::class.java)

    }


    @Test
    fun test_get_All_pokemon() = runTest {
        val mockResponse = MockResponse()
        val content = FileReader.getStringFromFile("pokemontest.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getPokemon()
        mockWebServer.takeRequest()

        Assert.assertEquals(5, response.body()?.pokemonEntries?.size)
    }

    @After
    fun tearDown(){

    }
}