package br.com.fiap.doafacil.repository

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("doafacil_prefs", Context.MODE_PRIVATE)

    // Sessão

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
        prefs.edit()
            .remove("user_email")
            .remove("user_name")
            .apply()
    }

    // Cadastro

    fun registerUser(email: String, name: String, password: String) {
        val emails = prefs.getStringSet("registered_emails", emptySet())
            ?.toMutableSet() ?: mutableSetOf()
        emails.add(email)
        prefs.edit()
            .putStringSet("registered_emails", emails)
            .putString("user_name_$email", name)
            .putString("user_password_$email", password)
            .apply()
    }

    fun userExists(email: String): Boolean {
        val emails = prefs.getStringSet("registered_emails", emptySet()) ?: emptySet()
        return email in emails
    }

    fun checkPassword(email: String, password: String): Boolean {
        val saved = prefs.getString("user_password_$email", null)
        return saved == password
    }

    fun getRegisteredName(email: String): String =
        prefs.getString("user_name_$email", "") ?: ""

    // Doação

    fun saveDoacaoItens(itensSelecionados: String) {
        prefs.edit().putString("doacao_itens", itensSelecionados).apply()
    }

    fun getDoacaoItens(): String = prefs.getString("doacao_itens", "") ?: ""

    fun saveMetodoEntrega(metodo: String) {
        prefs.edit().putString("metodo_entrega", metodo).apply()
    }

    fun getMetodoEntrega(): String =
        prefs.getString("metodo_entrega", "Levar ao Local") ?: "Levar ao Local"
}

