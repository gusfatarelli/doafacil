package br.com.fiap.doafacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.doafacil.model.Category
import br.com.fiap.doafacil.ui.theme.BluePrimary
import br.com.fiap.doafacil.ui.theme.DarkBlue

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean = false,
    onCategoryClick: (Category) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) category.background else Color.Transparent
    val borderColor = if (isSelected) Color.Transparent else BluePrimary

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable { onCategoryClick(category) }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = category.name, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = DarkBlue)
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            imageVector = category.icon,
            contentDescription = category.name,
            modifier = Modifier.size(18.dp),
            tint = DarkBlue
        )
    }
}
