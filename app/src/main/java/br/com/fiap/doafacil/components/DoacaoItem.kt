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
import br.com.fiap.doafacil.ui.theme.GreyText
import br.com.fiap.doafacil.ui.theme.LightGreen

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
    val backgroundColor = if (item.selecionado)
        MaterialTheme.colorScheme.primaryContainer
    else
        Color.Transparent

    val borderColor = if (item.selecionado)
        MaterialTheme.colorScheme.secondary
    else
        MaterialTheme.colorScheme.outlineVariant

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        // Checkbox
        Checkbox(
            checked = item.selecionado,
            onCheckedChange = onSelecionadoChange,
            colors = CheckboxDefaults.colors(
                checkedColor   = MaterialTheme.colorScheme.secondary,
                uncheckedColor = GreyText
            )
        )

        Text(
            text = item.icone,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 6.dp)
        )

        Text(
            text = item.nome,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground
        )

        // Controle +/-
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { if (item.quantidade > 1) onQuantidadeChange(item.quantidade - 1) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Diminuir",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "${item.quantidade}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            IconButton(
                onClick = { onQuantidadeChange(item.quantidade + 1) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Aumentar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}