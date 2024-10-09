package com.example.praktikumpapb


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List(auth: FirebaseAuth, navController: NavHostController) {
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

    TopAppBar(
        title = { },
        actions = {
            TextButton(
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = -10.dp),
                onClick = {
                    navController.navigate("profile")
                }) {
                Image(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = "Button Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Button(onClick = {
                auth.signOut()
                navController.navigate("login")
            }) {
                Text("Logout")
            }
        }
    )

}


