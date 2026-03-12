package br.com.fiap.doafacil.navigation

sealed class Destination(val route: String){
    object CadastroScreen: Destination("cadastro")
    object LoginScreen: Destination("login")
    object HomeScreen: Destination("home")
    object ProfileScreen: Destination("profile")
}