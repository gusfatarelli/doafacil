package br.com.fiap.doafacil.repository

import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.model.Campanha

fun getAllCampanhas() = listOf(
    Campanha(
        id = 1000,
        titulo = "Aqueça SP: Campanha de Agasalhos 🎀",
        descricao = "Ajude as famílias mais vulneráveis a enfrentar o inverno.",
        image = R.drawable.family
    ),
    Campanha(
        id = 2000,
        titulo = "Doe Alimentos para ajudar os desabrigados das enchentes de MG 🥫",
        descricao = "Contribua com cestas básicas para famílias em situação de vulnerabilidade.",
        image = R.drawable.rice
    ),
    Campanha(
        id = 3000,
        titulo = "Kit Bebê Brasil 👶",
        descricao = "Fraldas, roupinhas e itens essenciais para recém-nascidos carentes de todo o Brasil.",
        image = R.drawable.baby
    )
)