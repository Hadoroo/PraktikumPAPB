package com.example.praktikumpapb.retrofit

import com.example.praktikumpapb.data.GithubUser
import retrofit2.HttpException

class GithubUserRepository {
    suspend fun getUser(user: String): GithubUser {
        return try{
            val response = ApiConfig.getApiService().getUser(user)
            response
        }catch(e: HttpException){
            throw Exception(e.response()?.errorBody()?.string())
        }catch (e: Exception){
            throw Exception(e.message)
        }
    }
}