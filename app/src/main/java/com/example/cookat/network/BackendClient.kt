package com.example.cookat.network

import android.content.Context
import android.util.Log
import com.example.cookat.BuildConfig
import com.example.cookat.data.local.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendClient {

	fun create(context: Context): BackendEndpoints {
		val logging = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

		val authInterceptor = Interceptor { chain ->
			val token = runBlocking { SessionManager(context).getAccessToken() } ?: ""
			//Log.d("AUTH_TOKEN", "Bearer $token")
			val request = chain.request().newBuilder()
				.addHeader("Authorization", "Bearer $token")
				.build()
			chain.proceed(request)
		}

		val client = OkHttpClient.Builder()
			.connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
			.readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
			.writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
			.addInterceptor(logging)
			.addInterceptor(authInterceptor)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(BuildConfig.COOKAT_URL)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		return retrofit.create(BackendEndpoints::class.java)
	}
}