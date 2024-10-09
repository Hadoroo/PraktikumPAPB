package com.example.praktikumpapb

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Login(auth: FirebaseAuth, navController: NavHostController) {

    var emailText by remember { mutableStateOf("") }
    var nimText by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("") }
    val personIcon = painterResource(id = R.drawable.person)
    val numberIcon = painterResource(id = R.drawable.number)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            TextField(
                value = emailText,
                onValueChange = { emailText = it },
                label = { Text("Nama") },
                leadingIcon = {
                    Icon(
                        painter = personIcon,
                        contentDescription = "Person"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = nimText,
                onValueChange = {
                    if (it.isEmpty() || it.matches(Regex("^\\d+\$"))) {
                        nimText = it
                    }
                },
                label = { Text("NIM") },
                leadingIcon = {
                    Icon(
                        painter = numberIcon,
                        contentDescription = "Number"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            val isButtonEnabled by remember(emailText, nimText) {
                derivedStateOf {
                    emailText.isNotBlank() && nimText.isNotBlank()
                }
            }

            Button(
                onClick = {
                    auth.signInWithEmailAndPassword(emailText, nimText)
                        .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("list")
                    } else {
                        displayText = "email atau password salah"
                    }
                        }
                },
                enabled = isButtonEnabled
            ) {
                Text("Submit")
            }

            Text(displayText)
        }
    }



}