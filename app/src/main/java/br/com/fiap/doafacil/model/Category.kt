package br.com.fiap.doafacil.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val id: Int = 0,
    val name: String = "",
    val icon: ImageVector,
    val background: Color = Color.Gray
)
