package com.example.praktikumpapb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumpapb.data.GithubUser
import com.example.praktikumpapb.retrofit.GithubUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileVIewModel : ViewModel() {
    private val githubUserRepository = GithubUserRepository()

    private val _user = MutableStateFlow<GithubUser?>(null)
    val user: StateFlow<GithubUser?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getGithubProfile(user: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profile = githubUserRepository.getUser(user)
                _user.value = profile
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
                _user.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

}