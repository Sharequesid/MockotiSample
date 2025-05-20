package com.example.mockotisample.util

import com.example.mockotisample.FileReader
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit


class MockWebServerDispatcher {

    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/pokedex/2/" ->
                    MockResponse().setResponseCode(200)
                        .setBody(FileReader.getStringFromFile("pokemontest.json"))
                        .throttleBody(1024, 200L, TimeUnit.MILLISECONDS)
                "/pokedex/" ->
                    MockResponse().setResponseCode(200)
                        .setBody(FileReader.getStringFromFile("pokemonempty.json"))
                        .throttleBody(1024, 200L, TimeUnit.MILLISECONDS)
                else -> MockResponse().setResponseCode(400).setBody("Something went Wrong")
            }
        }
    }

    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
                .setBody("Something went Wrong")
        }
    }
}