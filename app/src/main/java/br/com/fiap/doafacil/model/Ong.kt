package br.com.fiap.doafacil.model

import androidx.annotation.DrawableRes
import br.com.fiap.doafacil.R

data class Ong(
    val id: Int = 0,
    val nome: String = "",
    val verificado: Boolean = true,
    val distancia: String = "",
    val cidade: String = "",
    val categorias: String = "",
    val urgencias: List<String> = emptyList(),
    val descricao: String = "",
    val progressDoacao: Int = 0,
    @DrawableRes val image: Int = R.drawable.no_photo
)
