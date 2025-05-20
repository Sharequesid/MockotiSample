package com.example.mockotisample

import androidx.multidex.BuildConfig

import dagger.Provides
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.mockotisample.api.APIService
import com.example.mockotisample.di.NetworkModule
import com.example.mockotisample.ui.MainActivity
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.BeforeClass
import org.junit.Rule
import dagger.Module
import org.junit.rules.RuleChain
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep

@LargeTest
@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class MainActivityTest {

 companion object {
  lateinit var mockWebServer: MockWebServer
  lateinit var baseUrl: String

  @BeforeClass
  @JvmStatic
  fun setup() {

   mockWebServer = MockWebServer()
   mockWebServer.enqueue(
    MockResponse().setResponseCode(200)
     .setBody(FileReader.getStringFromFile("pokemonResponse.json"))
   )
   mockWebServer.start()


   baseUrl = mockWebServer.url("").toString()

  }

 }


 @InstallIn(ActivityRetainedComponent::class)
 @Module
 class TestModule {


  @Provides
  fun bindAnalyticsService(): APIService{

   var clientBuilder = OkHttpClient.Builder()

   if (BuildConfig.DEBUG) {
    clientBuilder.addNetworkInterceptor(
     HttpLoggingInterceptor().setLevel(
      HttpLoggingInterceptor.Level.BODY
     ))
   }


   val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(clientBuilder.build())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

   return retrofit.create(APIService::class.java)
  }

 }

 @get:Rule
 var rule = RuleChain.outerRule(HiltAndroidRule(this)).
 around(ActivityTestRule(MainActivity::class.java))

 @Test
 fun mainActivityTest() {//Adding or calling ui of main activity
  sleep(5000)

 }


}