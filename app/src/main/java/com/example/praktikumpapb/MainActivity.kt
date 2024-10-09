package com.example.praktikumpapb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            var awake = ""

            if (user == null) {
                awake = "login"
            } else {
                awake = "list"
            }

            NavHost(navController = navController, startDestination = awake) {
                composable("login") { Login(auth, navController = navController) }
                composable("list") { List(auth, navController = navController) }
                composable("profile") { Profile(auth, navController = navController) }
            }

//            PraktikumPAPBTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Column (
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        val context = LocalContext.current
//                        Tugas4(context)
//                        NavHost(navController = navController, startDestination = ""){
//                            composable("login"){Login(navController = navController)}
//                            composable("list"){List(navController = navController)}
//                            composable("profile"){Profile(navController = navController)}
//                        }
//                    }
//                }
//            }
        }
    }
}


