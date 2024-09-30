package com.example.praktikumpapb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktikumpapb.ui.theme.PraktikumPAPBTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ListActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumPAPBTheme {
                var receivedValue by remember { mutableStateOf("") }

                LaunchedEffect(Unit) {
                    receivedValue = intent?.getStringExtra("key") ?: ""
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    List(value = receivedValue)
                }
            }
        }
    }
}

@Composable
fun List(value: String) {
    val db = Firebase.firestore

    var items by remember { mutableStateOf<List<Matkul>>(emptyList()) }

    LaunchedEffect(Unit) {
        val result = db.collection("matkul").get().await()
        items = result.documents.map { doc ->
            Matkul(
                hari = doc.getString("hari") ?: "",
                matkul = doc.getString("matkul") ?: "",
                praktikum = doc.getBoolean("praktikum") ?: false,
                ruang = doc.getString("ruang") ?: "",
                jam = doc.getString("jam") ?: ""
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Text("Jadwal Kuliah")

            items.forEach { matkul ->
                Text(text = "Hari: ${matkul.hari}")
                Text(text = "Mata Kuliah: ${matkul.matkul}")
                Text(text = "Praktikum: ${if (matkul.praktikum) "Yes" else "No"}")
                Text(text = "Ruang: ${matkul.ruang}")
                Text(text = "Waktu: ${matkul.jam}")
            }
        }
    }
}


