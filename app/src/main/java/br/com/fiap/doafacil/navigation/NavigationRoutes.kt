package br.com.fiap.doafacil.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import br.com.fiap.doafacil.screens.CadastroScreen
import br.com.fiap.doafacil.screens.HomeScreen
import br.com.fiap.doafacil.screens.LoginScreen

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LoginScreen.route
    ){
        composable(Destination.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(Destination.CadastroScreen.route){
            CadastroScreen()
        }
        composable(Destination.HomeScreen.route){
            HomeScreen()
        }
//        composable(Destination.ProfileScreen.route){
//            ProfileScreen()
//        }
        composable(Destination.HomeScreen.route){
            HomeScreen()
        }
    }

}