package br.com.fiap.doafacil.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.graphics.Color
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.model.Category

fun getAllCategories() = listOf<Category>(
    Category(id = 1000, name = "Alimentos",
        icon = Icons.Default.Fastfood, background = Color(0xFFABF2E9)),
    Category(id = 2000, name = "Roupas",
        icon = Icons.Default.AssignmentInd, background = Color(0xFFF4D6C0)),
    Category(id = 3000, name = "Higiene",
        icon = Icons.Default.AssignmentInd, background = Color(0xFFC6DAFA)),
    Category(id = 4000, name = "Infantil",
        icon = Icons.Default.AssignmentInd, background = Color(0xFFF8D9D9)),
    Category(id = 5000, name = "",
        icon =Icons.Default.AssignmentInd, background = Color(0xFFABF2E9)),
    Category(id = 6000, name = "",
        icon = Icons.Default.AssignmentInd, background = Color(0xFF72412B)),
    Category(id = 7000, name = "",
        icon = Icons.Default.AssignmentInd, background = Color(0xFF80DEEA))
)