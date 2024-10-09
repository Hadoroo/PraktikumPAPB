package com.example.praktikumpapb

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubGetAPI {
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<GitHubUser>
}