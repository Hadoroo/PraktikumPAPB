package com.example.praktikumpapb

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Base URL untuk GitHub API
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Membuat service dari Retrofit
val service = retrofit.create(GithubGetAPI::class.java)

// Mengambil data user dari GitHub
fun fetchGitHubUser(username: String) {
    val call = service.getUser(username)
    call.enqueue(object : Callback<GithubUser> {
        fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
            if (response.isSuccessful) {
                val user = response.body()
                // Menampilkan username
                user?.let {
                    println("Username: ${it.login}")
                }
            }
        }

        fun onFailure(call: Call<GithubUser>, t: Throwable) {
            println("Error: ${t.message}")
        }
    })
}

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

@Composable
fun Profile(auth: FirebaseAuth, modifier: Modifier = Modifier, navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = modifier
                    .size(150.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.github),
                contentDescription = "Profile Picture"
            )

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
            ) {
                Text("test")
            }

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
            ) {
                Text("test")
            }

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
            ) {
                Text("test")
            }

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
            ) {
                Text("test")
            }
        }
    }
}