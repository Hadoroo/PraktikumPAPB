package com.example.praktikumpapb

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikumpapb.ui.theme.PraktikumPAPBTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumPAPBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        val context = LocalContext.current
                        Tugas4(context)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun TextAwal(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Halo " + name + "!",
        modifier = modifier
    )
}

@Composable
fun TombolMasuk(onClick: () -> Unit) {
    FloatingActionButton (onClick = onClick) {
        Text("+")
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    PraktikumPAPBTheme {
        TombolMasuk {  }
    }
}

@Preview
@Composable
private fun TextView() {
    PraktikumPAPBTheme {
        TextAwal("hadi")
    }
}

@Composable
fun Tugas2(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("Input: ") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Masukkan Teks") },
        modifier = modifier
    )

    Button(onClick = {
        displayText = "Input: " + text
    }) {
        Text("Submit")
    }

    Text(displayText)
}

@Composable
fun Tugas3(modifier: Modifier = Modifier) {
    var namaText by remember { mutableStateOf("") }
    var nimText by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("") }
    val personIcon = painterResource(id = R.drawable.person)
    val numberIcon = painterResource(id = R.drawable.number)

    TextField(
        value = namaText,
        onValueChange = { namaText = it.filter { it.isLetter() } },
        label = { Text("Nama") },
        leadingIcon = {
            Icon(
                painter = personIcon,
                contentDescription = "Person"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier.fillMaxWidth() // Make TextFields fill width
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
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(10.dp))

    Button(onClick = {
        displayText = "Halo " + namaText + " dengan NIM " + nimText + " 👋"
    }) {
        Text("Submit")
    }

    Text(displayText)
}

@Composable
fun Tugas4(context: Context, modifier: Modifier = Modifier) {

    val auth: FirebaseAuth = Firebase.auth
    var emailText by remember { mutableStateOf("") }
    var nimText by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("") }
    val personIcon = painterResource(id = R.drawable.person)
    val numberIcon = painterResource(id = R.drawable.number)

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
        modifier = Modifier.fillMaxWidth() // Make TextFields fill width
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
        modifier = Modifier.fillMaxWidth()
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
                        val intent = Intent(context, ListActivity::class.java)
                        context.startActivity(intent)
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



