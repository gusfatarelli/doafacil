package br.com.fiap.doafacil.model

import androidx.annotation.DrawableRes
import br.com.fiap.doafacil.R

data class Campanha(
    val id: Int = 0,
    val titulo: String = "",
    val descricao: String = "",
    @DrawableRes val image: Int? = R.drawable.no_photo,
)
