package br.com.fiap.doafacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.doafacil.model.Ong
import br.com.fiap.doafacil.ui.theme.GreyText
import br.com.fiap.doafacil.ui.theme.LightGreen

@Composable
fun OngCardItem(
    modifier: Modifier = Modifier,
    ong: Ong,
    onSaberMaisClick: (Ong) -> Unit = {},
    onDoacaoClick: (Ong) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = ong.image),
                    contentDescription = ong.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = ong.nome,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (ong.verificado) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "",
                                tint = LightGreen,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Verificado",
                                style = MaterialTheme.typography.bodySmall,
                                color = LightGreen
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "",
                            tint = GreyText,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "${ong.distancia} · ${ong.cidade}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GreyText
                        )
                    }

                    Text(
                        text = ong.categorias,
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyText
                    )
                }
            }

            // Urgências
            if (ong.urgencias.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Urgências 🔥",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                // Usa FlowRow emulado com LazyRow para não quebrar layout
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ong.urgencias.forEach { urgencia ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFFFFE5E5), RoundedCornerShape(50))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = "🔥", fontSize = 11.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = urgencia,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFD32F2F)
                            )
                        }
                    }
                }
            }

            // Descrição + barra de progresso
            if (ong.descricao.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = ong.descricao,
                    style = MaterialTheme.typography.bodySmall,
                    color = GreyText
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(50))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth((ong.progressDoacao / 100f).coerceIn(0f, 1f))
                            .height(6.dp)
                            .background(LightGreen, RoundedCornerShape(50))
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "${ong.progressDoacao}%",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onSaberMaisClick(ong) },
                    shape = RoundedCornerShape(50),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Saber Mais",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    onClick = { onDoacaoClick(ong) },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor   = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Quero Doar",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}