package com.example.praktikumpapb.Screen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikumpapb.data.local.TugasRepo
import com.example.praktikumpapb.viewmodel.MainViewModelFactory
import com.example.praktikumpapb.viewmodel.ProfileViewModel

@Composable
fun Tugas(application: Application) {
    val tugasRepository = TugasRepo(application)
    val mainViewModel: ProfileViewModel = viewModel(factory = MainViewModelFactory(tugasRepository))

    var matkul by remember { mutableStateOf("") }
    var detailTugas by remember { mutableStateOf("") }
    val tugasList by mainViewModel.tugasList.observeAsState(emptyList())

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    fun showSnackbar(message: String) {
        snackbarMessage = message
        snackbarVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Tambah tugas", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = matkul,
                onValueChange = { matkul = it },
                label = { Text("Matkul") },
                modifier = Modifier.fillMaxSize()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = detailTugas,
                onValueChange = { detailTugas = it },
                label = { Text("Detail Tugas") },
                modifier = Modifier.fillMaxSize()
            )

            Button(
                onClick = {
                    if (matkul.isNotEmpty() && detailTugas.isNotEmpty()) {
                        mainViewModel.addTugas(matkul, detailTugas)
                        showSnackbar("Tugas sudah ditambahkan.")
                        matkul = ""
                        detailTugas = ""
                    } else {
                        showSnackbar("Mohon isi semua kolom.")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Tambah tugas.")
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            Text(text = "Daftar Tugas", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))

            if (tugasList.isEmpty()) {
                Text(text = "Tidak ada tugas.", fontSize = 16.sp)
            } else {
                for ((index, tugas) in tugasList.withIndex()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Matkul ${tugas.matkul}", fontSize = 18.sp)
                            Text(text = "Detail ${tugas.detailTugas}", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }

    if (snackbarVisible) {
        Snackbar(
            action = {
                TextButton(onClick = { snackbarVisible = false }) {
                    Text("Tutup")
                }
            }
        ) {
            Text(text = snackbarMessage)
        }
    }
}