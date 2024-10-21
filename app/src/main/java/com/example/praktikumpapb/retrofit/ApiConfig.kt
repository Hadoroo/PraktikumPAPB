package com.example.praktikumpapb.retrofit

import com.example.praktikumpapb.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): GithubGetAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubGetAPI::class.java)
        }
    }
}