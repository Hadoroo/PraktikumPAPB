package com.example.praktikumpapb.navigation

sealed class Screen(val route: String) {
    object Matkul : Screen("Matkul")
    object Tugas : Screen("Tugas")
    object Profile : Screen("Profile")
}