package com.example.praktikumpapb.retrofit

import com.example.praktikumpapb.data.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubGetAPI {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): GithubUser
}