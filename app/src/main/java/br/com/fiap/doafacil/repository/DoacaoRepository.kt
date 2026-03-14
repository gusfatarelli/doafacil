package br.com.fiap.doafacil.repository

import br.com.fiap.doafacil.components.DoacaoItem

fun getAllDoacaoItens() = listOf(
    DoacaoItem(id = 1000, nome = "Cesta Básica", icone = "🧺"),
    DoacaoItem(id = 2000, nome = "Agasalho P", icone = "🧥"),
    DoacaoItem(id = 3000, nome = "Fraldas G", icone = "🧷"),
    DoacaoItem(id = 4000, nome = "Kit Higiene", icone = "🧴")
)