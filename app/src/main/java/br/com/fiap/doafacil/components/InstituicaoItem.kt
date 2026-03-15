package br.com.fiap.doafacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import br.com.fiap.doafacil.ui.theme.BluePrimary
import br.com.fiap.doafacil.ui.theme.DarkBlue
import br.com.fiap.doafacil.ui.theme.DeepGrey
import br.com.fiap.doafacil.ui.theme.GreenBackground
import br.com.fiap.doafacil.ui.theme.LightGreen

data class Instituicao(
    val nome: String,
    val categorias: String,
    val distancia: String,
    val prioridade: PriorityLevel = PriorityLevel.NORMAL,
    val verificado: Boolean = true
)

@Composable
fun InstituicaoCardItem(
    instituicao: Instituicao,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border( 1.dp, Color.LightGray, RoundedCornerShape(16.dp) ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "",
                modifier = Modifier
                    .size(56.dp)
                    .background(GreenBackground, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                tint = GreenBackground
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = instituicao.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = instituicao.categorias, fontSize = 13.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(6.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (instituicao.verificado) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(color = LightGreen, RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Verificado", color = Color.Black, fontSize = 11.sp)
                        }
                    }

                    if (instituicao.prioridade == PriorityLevel.URGENTE) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(DeepGrey, RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(text = instituicao.prioridade.description, color = DarkBlue, fontSize = 11.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "",
                                tint = BluePrimary,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    } else {
                        // Badge Normal
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFFFFF9C4), RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(text = instituicao.prioridade.description, color = Color(0xFF795548), fontSize = 11.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = "",
                                tint = Color(0xFFFFA000),
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(text = instituicao.distancia, fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.Directions, contentDescription = "Como chegar", tint = Color.Gray, modifier = Modifier.size(22.dp))
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Ver abrigo", tint = Color.Gray, modifier = Modifier.size(22.dp))
                }
            }
        }
    }
}