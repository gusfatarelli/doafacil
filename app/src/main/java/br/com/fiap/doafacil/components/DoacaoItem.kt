package br.com.fiap.doafacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.doafacil.ui.theme.*

data class DoacaoItem(
    val id: Int = 0,
    val nome: String = "",
    val icone: String = "",
    var quantidade: Int = 1,
    var selecionado: Boolean = false
)

@Composable
fun DoacaoItemCard(
    item: DoacaoItem,
    onSelecionadoChange: (Boolean) -> Unit,
    onQuantidadeChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (item.selecionado) GreenBackground else Color.Transparent
    val borderColor = if (item.selecionado) LightGreen else DeepGrey

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        // Checkbox
        Checkbox(
            checked = item.selecionado,
            onCheckedChange = onSelecionadoChange,
            colors = CheckboxDefaults.colors(
                checkedColor = LightGreen,
                uncheckedColor = GreyText
            )
        )

        // Ícone emoji
        Text(text = item.icone, fontSize = 24.sp, modifier = Modifier.padding(horizontal = 8.dp))

        // Nome
        Text(
            text = item.nome,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            color = DarkBlue
        )

        // Controle de quantidade
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { if (item.quantidade > 1) onQuantidadeChange(item.quantidade - 1) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Diminuir", tint = BluePrimary)
            }

            Text(
                text = "${item.quantidade}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            IconButton(
                onClick = { onQuantidadeChange(item.quantidade + 1) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Aumentar", tint = BluePrimary)
            }
        }
    }
}