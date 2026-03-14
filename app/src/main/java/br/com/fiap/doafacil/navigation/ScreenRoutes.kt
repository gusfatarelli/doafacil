package br.com.fiap.doafacil.navigation

sealed class Destination(val route: String) {
    object LoginScreen : Destination("login")
    object CadastroScreen : Destination("cadastro")
    object HomeScreen : Destination("home")
    object ExplorationScreen : Destination("exploration")
    object DoacaoScreen : Destination("doacao")
    object ProfileScreen : Destination("profile")
}