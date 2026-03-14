package br.com.fiap.doafacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import br.com.fiap.doafacil.navigation.NavigationRoutes
import br.com.fiap.doafacil.screens.LoginScreen
import br.com.fiap.doafacil.ui.theme.DoafacilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoafacilTheme {
                //NavigationRoutes()
                }
            }
        }
    }


