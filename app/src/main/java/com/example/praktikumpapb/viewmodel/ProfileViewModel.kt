package com.example.praktikumpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumpapb.data.GithubUser
import com.example.praktikumpapb.data.local.Tugas
import com.example.praktikumpapb.data.local.TugasRepo
import com.example.praktikumpapb.retrofit.GithubUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel (private val tugasRepository: TugasRepo): ViewModel() {
    private val githubUserRepository = GithubUserRepository()

    private val _user = MutableStateFlow<GithubUser?>(null)
    val user: StateFlow<GithubUser?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _tugasList = tugasRepository.getAllTugas()
    val tugasList: LiveData<List<Tugas>> get() = _tugasList

    init {
        fetchAllTugas()
    }

    private fun fetchAllTugas() {
        viewModelScope.launch {
            tugasRepository.getAllTugas()
        }
    }

    fun addTugas(matkul: String, detailTugas: String) {
        val newTugas = Tugas(matkul = matkul, detailTugas = detailTugas, selesai = false)
        viewModelScope.launch {
            tugasRepository.insert(newTugas)
        }
    }

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