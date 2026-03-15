package br.com.fiap.doafacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.doafacil.model.PriorityLevel
import br.com.fiap.doafacil.ui.theme.DarkBlue
import br.com.fiap.doafacil.ui.theme.GreyText
import br.com.fiap.doafacil.ui.theme.LightGreen

data class Necessidade(
    val id: Int = 0,
    val titulo: String = "",
    val instituicaoNome: Instituicao,
    val prioridade: PriorityLevel,
    val progress: Int = 0,
    val quantity: Int = 0
)

@Composable
fun NecessidadeCardItem(
    necessidade: Necessidade,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            // largura FIXA igual para todos os cards
            .width(160.dp)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                // altura mínima para garantir uniformidade
                .height(148.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Título + instituição
            Column {
                Text(
                    text = necessidade.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = necessidade.instituicaoNome.nome,
                    fontSize = 11.sp,
                    color = GreyText,
                    maxLines = 1
                )
            }

            // Badge prioridade
            if (necessidade.prioridade == PriorityLevel.URGENTE) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFFFE5E5), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "🔥", fontSize = 10.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "URGENTE",
                        color = Color(0xFFD32F2F),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // placeholder invisível para manter altura igual nos cards normais
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Barra de progresso + labels
            Column {
                val progressFraction = (necessidade.progress / 100f).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(50))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressFraction)
                            .height(6.dp)
                            .background(LightGreen, RoundedCornerShape(50))
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Faltam", fontSize = 11.sp, color = GreyText)
                    Text(
                        text = "${necessidade.progress}%",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${necessidade.quantity}/100",
                        fontSize = 11.sp,
                        color = GreyText
                    )
                }
            }
        }
    }
}