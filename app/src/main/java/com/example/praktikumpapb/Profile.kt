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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun Profile(modifier: Modifier = Modifier) {
    val viewModel: ProfileVIewModel = viewModel()

    val user by viewModel.user.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getGithubProfile("Hadoroo")
    }

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
                painter = rememberAsyncImagePainter(user?.avatar_url),
                contentDescription = "Profile Picture"
            )

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp),
            ) {
                user?.let {
                    Text(it.login)
                } ?: Text("Loading...")
            }

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp),
            ) {
                user?.let {
                    Text(it.name)
                }
            }

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp),
            ) {
                user?.let {
                    Text("Followers: ${it.followers}")
                }
            }

            Spacer(modifier = modifier.padding(0.dp, 10.dp))

            Box(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                    .padding(5.dp),
            ) {
                user?.let {
                    Text("Following: ${it.following}")
                }
            }
        }
    }
}