package br.com.fiap.doafacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import br.com.fiap.doafacil.ui.theme.DeepGrey
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
            .width(180.dp)
            .padding(8.dp)
            .border( 1.dp, Color.LightGray, RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = necessidade.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = necessidade.instituicaoNome.nome, fontSize = 13.sp, color = DarkBlue)

            Spacer(modifier = Modifier.height(8.dp))

            // Badge prioridade
            if (necessidade.prioridade == PriorityLevel.URGENTE) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFFFE0E0), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(text = "🔥", fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = necessidade.prioridade.description.uppercase(),
                        color = Color(0xFFE53935),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progresso
            val progressFraction = necessidade.progress / 100f
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.White, RoundedCornerShape(50))
                    .border( 1.dp, Color.LightGray, RoundedCornerShape(50))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction)
                        .height(8.dp)
                        .background(LightGreen, RoundedCornerShape(50))
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Faltam", fontSize = 12.sp, color = DarkBlue)
                Text(text = "${necessidade.progress}%", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${necessidade.quantity}/100",
                    fontSize = 12.sp,
                    color = DarkBlue
                )
            }
        }
    }
}