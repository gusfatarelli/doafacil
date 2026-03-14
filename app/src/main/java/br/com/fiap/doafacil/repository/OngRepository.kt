package br.com.fiap.doafacil.repository

import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.model.Ong

fun getAllOngs() = listOf(
    Ong(
        id = 1000,
        nome = "Abrigo Vida Nova",
        distancia = "4.1 km",
        cidade = "Osasco - SP",
        categorias = "Crianças | Roupas | Alimentos",
        urgencias = listOf("Agasalhos P & M", "Fraldas G"),
        image = R.drawable.no_photo
    ),
    Ong(
        id = 2000,
        nome = "Banco de Alimentos Osasco",
        distancia = "2.8 km",
        cidade = "Osasco - SP",
        categorias = "Fome | Alimentos",
        descricao = "Alimentos não perecíveis",
        progressDoacao = 85,
        image = R.drawable.no_photo
    ),
    Ong(
        id = 3000,
        nome = "Projeto Roupas do Bem",
        distancia = "3.5 km",
        cidade = "Osasco - SP",
        categorias = "Roupas | Calçados",
        urgencias = listOf("Agasalhos Adulto"),
        image = R.drawable.no_photo
    ),
    Ong(
        id = 4000,
        nome = "Casa do Idoso Feliz",
        distancia = "5.2 km",
        cidade = "Osasco - SP",
        categorias = "Idosos | Saúde | Higiene",
        urgencias = listOf("Fraldas Adulto", "Remédios"),
        image = R.drawable.no_photo
    ),
    Ong(
        id = 5000,
        nome = "ONG Pequenos Passos",
        distancia = "1.9 km",
        cidade = "Osasco - SP",
        categorias = "Crianças | Educação",
        descricao = "Material escolar e brinquedos",
        progressDoacao = 62,
        image = R.drawable.no_photo
    )
)