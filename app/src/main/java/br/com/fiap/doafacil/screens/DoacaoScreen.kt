package br.com.fiap.doafacil.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Pix
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.components.DoacaoItemCard
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.repository.getAllDoacaoItens
import br.com.fiap.doafacil.ui.theme.DoafacilTheme
import br.com.fiap.doafacil.ui.theme.GreyText
import br.com.fiap.doafacil.ui.theme.LightGreen


@Composable
fun DoacaoScreen(navController: NavController, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = { MyBottomNavigation(navController = navController) }
        ) { paddingValues ->
            ContentScreenDonation(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@Composable
fun ContentScreenDonation(modifier: Modifier = Modifier, navController: NavController) {
    val context   = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    var itens      by remember { mutableStateOf(getAllDoacaoItens()) }
    var tipoDoacao by remember { mutableStateOf("itens") }
    var tipoMetodo by remember { mutableStateOf(userPrefs.getMetodoEntrega()) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        item { HeaderDonation() }
        item { OngHeaderDoacao() }
        item {
            DonationChoices(
                tipoDoacao   = tipoDoacao,
                onTipoChange = { tipoDoacao = it }
            )
        }

        // Aba Itens
        if (tipoDoacao == "itens") {
            item {
                Text(
                    text = stringResource(R.string.selecione_itens),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
            items(itens) { item ->
                DoacaoItemCard(
                    item = item,
                    onSelecionadoChange = { checked ->
                        itens = itens.map { if (it.id == item.id) it.copy(selecionado = checked) else it }
                    },
                    onQuantidadeChange = { novaQtd ->
                        itens = itens.map { if (it.id == item.id) it.copy(quantidade = novaQtd) else it }
                    }
                )
            }
        }

        // ABA DINHEIRO
        if (tipoDoacao == "dinheiro") {
            item { MoneyDonationSection() }
        }

        item {
            DonationConfirmations(
                tipoMetodo   = tipoMetodo,
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

private val valoresSugeridos = listOf("R$ 10", "R$ 25", "R$ 50", "R$ 100")

@Composable
fun MoneyDonationSection(modifier: Modifier = Modifier) {
    var valorSelecionado by remember { mutableStateOf("R$ 25") }
    var valorCustom      by remember { mutableStateOf("") }
    var metodoPagamento  by remember { mutableStateOf("pix") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {

        Text(
            text = "Escolha o valor 💰",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            valoresSugeridos.forEach { valor ->
                val selecionado = valorSelecionado == valor && valorCustom.isEmpty()
                OutlinedButton(
                    onClick = {
                        valorSelecionado = valor
                        valorCustom = ""
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        1.5.dp,
                        if (selecionado) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.outline
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selecionado)
                            MaterialTheme.colorScheme.secondaryContainer
                        else Color.Transparent
                    ),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = valor,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selecionado) MaterialTheme.colorScheme.onBackground
                        else GreyText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = valorCustom,
            onValueChange = {
                valorCustom = it
                if (it.isNotBlank()) valorSelecionado = ""
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor    = MaterialTheme.colorScheme.outline,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor   = MaterialTheme.colorScheme.surface
            ),
            placeholder = {
                Text(
                    text = "Outro valor (ex: 35)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreyText
                )
            },
            leadingIcon = {
                Text(
                    text = "R$",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Forma de pagamento",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetodoPagamentoButton(
                label       = "Pix",
                icon        = Icons.Default.Pix,
                selecionado = metodoPagamento == "pix",
                onClick     = { metodoPagamento = "pix" },
                modifier    = Modifier.weight(1f)
            )
            MetodoPagamentoButton(
                label       = "Cartão",
                icon        = Icons.Default.CreditCard,
                selecionado = metodoPagamento == "cartao",
                onClick     = { metodoPagamento = "cartao" },
                modifier    = Modifier.weight(1f)
            )
            MetodoPagamentoButton(
                label       = "QR Code",
                icon        = Icons.Default.QrCode,
                selecionado = metodoPagamento == "qrcode",
                onClick     = { metodoPagamento = "qrcode" },
                modifier    = Modifier.weight(1f)
            )
        }

        val instrucao = when (metodoPagamento) {
            "pix"    -> "🔑  Chave Pix: doafacil@ong.org.br"
            "cartao" -> "🔒  Você será direcionado para o pagamento seguro ao confirmar."
            "qrcode" -> "📷  Um QR Code será gerado após confirmar a doação."
            else     -> ""
        }

        if (instrucao.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = instrucao,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun MetodoPagamentoButton(
    label: String,
    icon: ImageVector,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.5.dp,
            if (selecionado) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.outline
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selecionado)
                MaterialTheme.colorScheme.secondaryContainer
            else Color.Transparent
        ),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 12.dp),
        modifier = modifier.height(64.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selecionado) MaterialTheme.colorScheme.secondary else GreyText,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (selecionado) MaterialTheme.colorScheme.onBackground else GreyText
            )
        }
    }
}

@Composable
fun HeaderDonation(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logo_doafacil),
                contentDescription = "Logo DoaFácil",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(60.dp))
            Text(
                text = stringResource(R.string.montar_doacao),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun OngHeaderDoacao(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.family),
                contentDescription = "Foto da ONG",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Abrigo Vida Nova",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "",
                        tint = LightGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.verificado),
                        style = MaterialTheme.typography.bodySmall,
                        color = LightGreen
                    )
                }
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
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        listOf(
            "itens"    to "Itens 👕🧺",
            "dinheiro" to "Dinheiro 💰"
        ).forEach { (tipo, label) ->
            val selecionado = tipoDoacao == tipo
            Button(
                onClick = { onTipoChange(tipo) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selecionado) MaterialTheme.colorScheme.secondary
                    else Color.Transparent,
                    contentColor   = if (selecionado) MaterialTheme.colorScheme.onSecondary
                    else GreyText
                ),
                modifier = Modifier.weight(1f),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(text = label, style = MaterialTheme.typography.labelMedium)
            }
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
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

        Text(
            text = stringResource(R.string.metodo_entrega),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                stringResource(R.string.levar_ao_local)     to "🚗",
                stringResource(R.string.entrega_transporte) to "📦"
            ).forEach { (metodo, emoji) ->
                val selecionado = tipoMetodo == metodo
                OutlinedButton(
                    onClick = { onTipoChange(metodo) },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        1.5.dp,
                        if (selecionado) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.outline
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selecionado)
                            MaterialTheme.colorScheme.secondaryContainer
                        else Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = emoji, fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = metodo,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.endereco_horario),
            style = MaterialTheme.typography.bodySmall,
            color = GreyText,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = onGerarResumo,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor   = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = stringResource(R.string.doar),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DoacaoScreenPreview() {
    DoafacilTheme {
        DoacaoScreen(rememberNavController())
    }
}