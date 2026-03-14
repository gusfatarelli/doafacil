package br.com.fiap.doafacil.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.doafacil.screens.DoacaoScreen
import br.com.fiap.doafacil.screens.ExplorationScreen
import br.com.fiap.doafacil.screens.HomeScreen
import br.com.fiap.doafacil.screens.LoginScreen
import br.com.fiap.doafacil.screens.ProfileScreen

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LoginScreen.route
    ) {
        composable(Destination.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Destination.CadastroScreen.route) {
            // CadastroScreen ainda está vazia, placeholder por ora
        }
        composable(
            route = "${Destination.HomeScreen.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            HomeScreen(navController, email)
        }
        composable(Destination.ExplorationScreen.route) {
            ExplorationScreen(navController)
        }
        composable(Destination.DoacaoScreen.route) {
            DoacaoScreen(navController)
        }
        composable(Destination.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}