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
import br.com.fiap.doafacil.ui.theme.*

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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Header: imagem + info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = ong.image),
                    contentDescription = ong.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(text = ong.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (ong.verificado) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "",
                                tint = LightGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Verificado", fontSize = 12.sp, color = LightGreen)
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "",
                            tint = GreyText,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(text = "${ong.distancia} | ${ong.cidade}", fontSize = 12.sp, color = GreyText)
                    }

                    Text(text = ong.categorias, fontSize = 12.sp, color = GreyText)
                }
            }

            // Urgências
            if (ong.urgencias.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Urgências 🔥", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ong.urgencias.forEach { urgencia ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFFFFE0E0), RoundedCornerShape(50))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(text = "🔥", fontSize = 11.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = urgencia, fontSize = 12.sp, color = Color(0xFFE53935))
                        }
                    }
                }
            }

            // Descrição + barra de progresso
            if (ong.descricao.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = ong.descricao, fontSize = 13.sp, color = GreyText)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(DeepGrey, RoundedCornerShape(50))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(ong.progressDoacao / 100f)
                            .height(8.dp)
                            .background(LightGreen, RoundedCornerShape(50))
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(text = "${ong.progressDoacao}%", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Saber Mais", color = BluePrimary)
                }
                Button(
                    onClick = { onDoacaoClick(ong) },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Quero Doar", color = DarkBlue)
                }
            }
        }
    }
}