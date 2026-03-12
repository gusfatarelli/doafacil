package br.com.fiap.doafacil.screens

import android.graphics.Bitmap
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachEmail
import androidx.compose.material.icons.filled.Beenhere
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.ui.theme.DarkBlue
import br.com.fiap.doafacil.ui.theme.DoafacilTheme

@Composable
fun CadastroScreen(modifier: Modifier = Modifier) {

}

@Composable
fun TituloCadastro(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_doafacil),
            contentDescription = "",
            modifier = Modifier
                .width(300.dp)
                .padding(top = 50.dp)
        )
        Text(
            text = stringResource(R.string.doa_f_cil),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(R.string.toda_ajuda_conta_comece_hoje),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkBlue,
        )
        Image(
            painter = painterResource(R.drawable.pessoas_doafacil),
            contentDescription = "",
        )

    }

}

@Preview
@Composable
private fun TituloCadastroPrevier() {
    DoafacilTheme{
        TituloCadastro()
    }
}

@Composable
fun SignupUserForm(navController: NavController?, profileImage: Bitmap) {

    //Variáveis de estado para controlar os valores exibidos no
    //OutlinedTextField
    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    //Criar variáveis de estado para verificar se os dados estão corretos
    var isNameError by remember {
        mutableStateOf(false)
    }
    var isEmailError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }

    //Variável de estado para controlar a exibição da mensagem de erro

    var showDialogError by remember { mutableStateOf(false) }
    var showDialogSuccess by remember { mutableStateOf(false) }

    // Função para verificar se os dados estão corretos
    fun validate(): Boolean {
        isNameError = name.length < 3
        isEmailError = email.length < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordError = password.length < 3

        return !isNameError && !isEmailError && !isPasswordError
    }

    //Criar uma instância do sharedPreferencesUserRepository
    //val userRepository = SharedPreferencesUserRepository(LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    )
    //Caixa de Texto do Usuário
    {

        Text(text = stringResource(R.string.crie_sua_conta))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            label = {
                Text(
                    text = stringResource(R.string.your_name),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Beenhere,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),

            isError = isNameError,
            trailingIcon = {
                if (isNameError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isNameError) {

                    Text(
                        text = "Name must have at least 3 letters",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        )

        //Caixa de Texto do E-mail
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            label = {
                Text(
                    text = "Your Email",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AttachEmail,
                    contentDescription = "Email",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),

            isError = isEmailError,
            trailingIcon = {
                if (isEmailError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isEmailError) {

                    Text(
                        text = "E-mail must have at least 3 letters",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        )

        //Caixa de Texto de Senha

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults
                .colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),

            label = {

                Text(
                    text = stringResource(R.string.your_password),
                    style = MaterialTheme.typography.labelSmall,
                )

            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },

            //trailingIcon = {
            //Icon(
            //imageVector = Icons.Default.RemoveRedEye,
            //contentDescription = "",
            //tint = MaterialTheme.colorScheme.tertiary
            //)
            //},

            isError = isPasswordError,
            trailingIcon = {
                if (isPasswordError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (isPasswordError) {

                    Text(
                        text = "Password must have at least 3 letters",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),

            )

        //Botão Create Account
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (validate()){
//
//                    userRepository.saveUser(
//                        User(
//                            name = name,
//                            password = password,
//                            email = email,
//                            userImage = convertBitmapToByteArray(profileImage)
//                        )
//                    )
                    showDialogSuccess = true
                } else {
                    showDialogError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {

            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Row {
            Text(
                text = "Não tem conta?"
            )

            TextButton(
                onClick = {}
            ) { Text(
                text = "Cadastre-se"
            ) }
        }

    }
    //Caixa de Diálogo de Sucesso
    if (showDialogSuccess) {

        AlertDialog(
            onDismissRequest = { showDialogError = false },
            title = { Text(text = "Success") },
            text = { Text(text = "Account Created with Success") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialogSuccess = false
                        navController?.navigate(Destination.LoginScreen.route)
                    }
                ) { }
            }
        )
    }
    //caixa de diálogo de erro
    if (showDialogError) {
        AlertDialog(
            onDismissRequest = { showDialogError = false},
            title = { Text(text = "Error") },
            text = { Text(text = "Please fill in all fields correctly") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialogError = false
                    }
                ) { Text(text = "Ok") }
            }
        )
    }
}

@Preview
@Composable
private fun SignupUserFormPreviwe() {
    DoafacilTheme{
        SignupUserForm()
    }
}