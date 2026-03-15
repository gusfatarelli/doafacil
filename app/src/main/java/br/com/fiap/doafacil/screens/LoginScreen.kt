package br.com.fiap.doafacil.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.navigation.Destination
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.ui.theme.DarkBlue
import br.com.fiap.doafacil.ui.theme.DoafacilTheme


@Composable
fun LoginScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.TopEnd),
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_doafacil),
                contentDescription = "",
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 60.dp)
            )
            Text(
                text = stringResource(R.string.doa_f_cil),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.doe_com_prop_sito_impacte_de_verdade),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkBlue,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Image(
                painter = painterResource(R.drawable.pessoas_doafacil),
                contentDescription = "Pessoas",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            LoginForm(navController = navController)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Composable
fun LoginForm(modifier: Modifier = Modifier, navController: NavController) {
    val context   = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showUserNotFound  by remember { mutableStateOf(false) }
    var showWrongPassword by remember { mutableStateOf(false) }

    if (showUserNotFound) {
        AlertDialog(
            onDismissRequest = { showUserNotFound = false },
            title   = { Text("Usuário não encontrado") },
            text    = { Text("Este e-mail não está cadastrado.") },
            confirmButton = {
                TextButton(onClick = { showUserNotFound = false }) { Text("Ok") }
            }
        )
    }
    if (showWrongPassword) {
        AlertDialog(
            onDismissRequest = { showWrongPassword = false },
            title   = { Text("Senha incorreta") },
            text    = { Text("A senha informada está incorreta.") },
            confirmButton = {
                TextButton(onClick = { showWrongPassword = false }) { Text("Ok") }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Campo e-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            label = {
                Text(stringResource(R.string.seu_e_mail), style = MaterialTheme.typography.labelSmall)
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),
            singleLine = true
        )

        // Campo senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            label = {
                Text(stringResource(R.string.sua_senha), style = MaterialTheme.typography.labelSmall)
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            trailingIcon = {
                Icon(Icons.Default.RemoveRedEye, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Done
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botão Entrar
        Button(
            onClick = {
                when {
                    !userPrefs.userExists(email) -> showUserNotFound = true
                    !userPrefs.checkPassword(email, password) -> showWrongPassword = true
                    else -> {
                        val nome = userPrefs.getRegisteredName(email)
                        userPrefs.saveUser(email, nome)
                        navController.navigate("${Destination.HomeScreen.route}/$email") {
                            popUpTo(Destination.LoginScreen.route) { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(stringResource(R.string.entrar), style = MaterialTheme.typography.labelMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Convidado
        Button(
            onClick = {
                userPrefs.saveUser("convidado", "Convidado")
                navController.navigate("${Destination.HomeScreen.route}/convidado") {
                    popUpTo(Destination.LoginScreen.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(stringResource(R.string.entrar_convidado), style = MaterialTheme.typography.labelMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.nao_tem_conta), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(
                text = stringResource(R.string.cadastre_se),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate(Destination.CadastroScreen.route) }
            )
        }
    }
}

@Preview
@Composable
private fun LoginFormPreview() {
    DoafacilTheme { LoginForm(navController = rememberNavController()) }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    DoafacilTheme { LoginScreen(rememberNavController()) }
}
