package br.com.fiap.doafacil.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.components.DoacaoItemCard
import br.com.fiap.doafacil.components.OngCardItem
import br.com.fiap.doafacil.model.Category
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.repository.getAllCategories
import br.com.fiap.doafacil.repository.getAllDoacaoItens
import br.com.fiap.doafacil.repository.getAllOngs
import br.com.fiap.doafacil.ui.theme.BluePrimary
import br.com.fiap.doafacil.ui.theme.DarkBlue
import br.com.fiap.doafacil.ui.theme.DeepGrey
import br.com.fiap.doafacil.ui.theme.DoafacilTheme
import br.com.fiap.doafacil.ui.theme.GreenBackground
import br.com.fiap.doafacil.ui.theme.GreyText
import br.com.fiap.doafacil.ui.theme.LightGreen


@Composable
fun DoacaoScreen(navController: NavController, modifier: Modifier = Modifier) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(

            topBar = {},

            bottomBar = { MyBottomNavigation(navController = navController) },

            floatingActionButton = {}
        )
        {
                paddingValues ->
            ContentScreenDonation(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@Composable
fun ContentScreenDonation(modifier: Modifier = Modifier, navController: NavController) {

    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    var itens by remember { mutableStateOf(getAllDoacaoItens()) }
    var tipoDoacao by remember { mutableStateOf("itens") }
    var tipoMetodo by remember { mutableStateOf(userPrefs.getMetodoEntrega()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            HeaderDonation()
            OngHeaderDoacao()

            LazyColumn(modifier = modifier.fillMaxWidth()) {
                item { DonationChoices(tipoDoacao = tipoDoacao, onTipoChange = { tipoDoacao = it }) }
                if (tipoDoacao == "itens") {
                    item {
                        Text(
                            text = "Selecione os Itens: 👕🧺",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    items(itens) { item ->
                        DoacaoItemCard(
                            item = item,
                            onSelecionadoChange = { checked ->
                                itens =
                                    itens.map { if (it.id == item.id) it.copy(selecionado = checked) else it }
                            },
                            onQuantidadeChange = { novaQtd ->
                                itens =
                                    itens.map { if (it.id == item.id) it.copy(quantidade = novaQtd) else it }
                            }
                        )
                    }
                }
            }

            DonationConfirmations(
                tipoMetodo = tipoMetodo,
                onTipoChange = {
                    tipoMetodo = it
                    userPrefs.saveMetodoEntrega(it)
                },
                onGerarResumo = {
                    val selecionados = itens
                        .filter { it.selecionado }
                        .joinToString(";") { "${it.nome}:${it.quantidade}" }
                    userPrefs.saveDoacaoItens(selecionados)
                }
            )

        }
    }
}

@Composable
fun HeaderDonation(modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = BluePrimary)) {

        Image(
            painter = painterResource(R.drawable.logo_doafacil),
            contentDescription = "",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Montar Doação",
            color = MaterialTheme.colorScheme.secondary
        )

    }
}

@Composable
fun OngHeaderDoacao(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.no_photo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = "Abrigo Vida Nova", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "",
                    tint = LightGreen,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Verificado", fontSize = 12.sp, color = LightGreen)
            }
        }
    }
}

@Composable
fun DonationChoices(
    tipoDoacao: String,
    onTipoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(50))
            .background(DeepGrey)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Botão Itens
        Button(
            onClick = { onTipoChange("itens") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (tipoDoacao == "itens") LightGreen else Color.Transparent,
                contentColor = if (tipoDoacao == "itens") DarkBlue else GreyText
            ),
            modifier = Modifier.weight(1f),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text(text = "Itens 👕🧺", fontWeight = FontWeight.Medium)
        }

        // Botão Dinheiro
        Button(
            onClick = { onTipoChange("dinheiro") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (tipoDoacao == "dinheiro") LightGreen else Color.Transparent,
                contentColor = if (tipoDoacao == "dinheiro") DarkBlue else GreyText
            ),
            modifier = Modifier.weight(1f),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text(text = "Dinheiro 💰", fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun DonationConfirmations(
    modifier: Modifier = Modifier,
    tipoMetodo: String,
    onTipoChange: (String) -> Unit,
    onGerarResumo: () -> Unit = {}
) {
    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "Método de Entrega",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Botão Levar ao Local
            OutlinedButton(
                onClick = { onTipoChange("Levar ao Local") },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.5.dp,
                    if (tipoMetodo == "Levar ao Local") LightGreen else GreyText
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (tipoMetodo == "Levar ao Local") GreenBackground else Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🚗", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Levar ao Local",
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue,
                        fontSize = 13.sp
                    )
                }
            }

            // Botão Entrega via Transporte
            OutlinedButton(
                onClick = { onTipoChange("Entrega via Transporte") },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.5.dp,
                    if (tipoMetodo == "Entrega via Transporte") LightGreen else GreyText
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (tipoMetodo == "Entrega via Transporte") GreenBackground else Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📦", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Entrega via Transporte",
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Rua das Flores, 123 | Seg-Sex, 9h-17h",
            fontSize = 13.sp,
            color = GreyText,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Botão Gerar Resumo
        Button(
            onClick = onGerarResumo,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "Gerar Resumo 📋",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = DarkBlue
            )
        }
    }
}

@Preview
@Composable
private fun DoacaoScreenPreview() {
    DoafacilTheme {
        DoacaoScreen(rememberNavController())
    }
}