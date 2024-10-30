package com.example.praktikumpapb

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.praktikumpapb.Screen.Matkul
import com.example.praktikumpapb.Screen.Profile
import com.example.praktikumpapb.Screen.Tugas
import com.example.praktikumpapb.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            auth = Firebase.auth
            val user = auth.currentUser

            if (user != null) {
                MainActivityContent(auth = auth, navController = navController, awake = "login")
            } else {
                MainActivityContent(
                    auth = auth,
                    navController = navController,
                    awake = Screen.Matkul.route
                )
            }
        }
    }
}

@Composable
fun MainActivityContent(
    navController: NavHostController = rememberNavController(),
    auth: FirebaseAuth,
    awake: String,
    application: Application = LocalContext.current.applicationContext as Application
) {

    Scaffold(
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route != "login") {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = awake,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Matkul.route) { Matkul(auth = auth, navController = navController) }
            composable(Screen.Profile.route) { Profile() }
            composable(Screen.Tugas.route) { Tugas(application = application) }
            composable("login") { Login(auth = auth, navController = navController) }
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavHostController) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { navController.navigate(Screen.Matkul.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Jadwal")
                        Text("Jadwal")
                    }
                }
                IconButton(
                    onClick = { navController.navigate(Screen.Tugas.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Tugas")
                        Text("Tugas")
                    }
                }
                IconButton(
                    onClick = { navController.navigate(Screen.Profile.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                        Text("Profile")
                    }
                }
            }


        }
    )
}
