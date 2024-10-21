package com.example.praktikumpapb.data

data class GithubUser(
    val login: String,   // Username
    val id: Int,
    val avatar_url: String,
    val name: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
)
