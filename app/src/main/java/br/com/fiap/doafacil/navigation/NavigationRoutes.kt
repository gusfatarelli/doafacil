package br.com.fiap.doafacil.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.screens.CadastroScreen
import br.com.fiap.doafacil.screens.DoacaoScreen
import br.com.fiap.doafacil.screens.ExplorationScreen
import br.com.fiap.doafacil.screens.HomeScreen
import br.com.fiap.doafacil.screens.LoginScreen
import br.com.fiap.doafacil.screens.ProfileScreen

@Composable
fun NavigationRoutes() {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val navController = rememberNavController()

    val startDestination = if (userPrefs.isLoggedIn()) {
        "${Destination.HomeScreen.route}/${userPrefs.getEmail()}"
    } else {
        Destination.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Destination.CadastroScreen.route) {
            CadastroScreen(navController)
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