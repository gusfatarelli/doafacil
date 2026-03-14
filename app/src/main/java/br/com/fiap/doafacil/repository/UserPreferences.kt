package br.com.fiap.doafacil.repository

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("doafacil_prefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, name: String = "") {
        prefs.edit()
            .putString("user_email", email)
            .putString("user_name", name)
            .apply()
    }

    fun getEmail(): String = prefs.getString("user_email", "") ?: ""

    fun getName(): String = prefs.getString("user_name", "") ?: ""

    fun isLoggedIn(): Boolean = getEmail().isNotEmpty()

    fun clearUser() {
        prefs.edit().clear().apply()
    }

    fun saveDoacaoItens(itensSelecionados: String) {
        prefs.edit().putString("doacao_itens", itensSelecionados).apply()
    }

    fun getDoacaoItens(): String = prefs.getString("doacao_itens", "") ?: ""

    fun saveMetodoEntrega(metodo: String) {
        prefs.edit().putString("metodo_entrega", metodo).apply()
    }

    fun getMetodoEntrega(): String = prefs.getString("metodo_entrega", "Levar ao Local") ?: "Levar ao Local"

}

