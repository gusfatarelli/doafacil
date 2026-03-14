package br.com.fiap.doafacil.screens

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.components.Instituicao
import br.com.fiap.doafacil.components.InstituicaoCardItem
import br.com.fiap.doafacil.components.Necessidade
import br.com.fiap.doafacil.components.NecessidadeCardItem
import br.com.fiap.doafacil.model.PriorityLevel
import br.com.fiap.doafacil.navigation.Destination
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.components.CampanhaItem
import br.com.fiap.doafacil.components.CategoryItem
import br.com.fiap.doafacil.model.Category
import br.com.fiap.doafacil.repository.getAllCampanhas
import br.com.fiap.doafacil.repository.getAllCategories
import br.com.fiap.doafacil.ui.theme.DoafacilTheme

@Composable
fun HomeScreen(navController: NavController, email: String?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { br.com.fiap.doafacil.screens.MyTopAppBar(email!!, navController) },
            bottomBar = { MyBottomNavigation(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Button"
                    )
                }
            }
        )
        {
                paddingValues ->
            ContentScreen(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(email: String, navController: NavController) {

//    val user = userRespository.getUserByEmail(email)
//
//    val profileBitmap by remember {
//        mutableStateOf<Bitmap>(convertByteArrayToBitmap(
//            user!!.userImage!!)
//        )
//    }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.logo_doafacil),
                    contentDescription = "",
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 50.dp)
                )

                Text(text = "DoaFácil")

                Card(
                    shape = CircleShape,
                    colors = CardDefaults
                        .cardColors(
                            containerColor = Color.Transparent
                        ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            //onClick = { navController.navigate("profile/${user!!.email}") }
                            onClick = {}
                        )
                ) {
                    Image(
                        //bitmap = profileBitmap.asImageBitmap(),
                        painter = painterResource(R.drawable.logo_doafacil),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                //text = "Hello, ${user!!.name}!",
                text = "Hello João!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = email,
                style = MaterialTheme.typography.displaySmall
            )
        }
    )
}

//TELA DE CONTEÚDO

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController) {

    val necessidades = listOf(
        Necessidade(titulo = "Cestas Básicas 🧺", instituicaoNome = Instituicao(nome = "ONG Vida Nova", categorias = "", distancia = ""), prioridade = PriorityLevel.URGENTE, progress = 80, quantity = 20),
        Necessidade(titulo = "Fraldas G 👶", instituicaoNome = Instituicao(nome = "Abrigo Esperança", categorias = "", distancia = ""), prioridade = PriorityLevel.URGENTE, progress = 60, quantity = 40),
        Necessidade(titulo = "Agasalhos", instituicaoNome = Instituicao(nome = "ONG Vida Nova", categorias = "", distancia = ""), prioridade = PriorityLevel.URGENTE, progress = 45, quantity = 55),
        Necessidade(titulo = "Leite em Pó", instituicaoNome = Instituicao(nome = "Abrigo Feliz", categorias = "", distancia = ""), prioridade = PriorityLevel.NORMAL, progress = 30, quantity = 70)
    )

    val instituicoes = listOf(
        Instituicao(nome = "Abrigo Feliz", categorias = "Crianças | Roupas | Alimentos", distancia = "3.2 km", prioridade = PriorityLevel.URGENTE),
        Instituicao(nome = "Banco de Alimentos SP", categorias = "Fome | Alimentos", distancia = "4.1 km"),
    )

    var selectedCategory by remember { mutableStateOf<Category?>(getAllCategories().first()) }

    LazyColumn(modifier = modifier.fillMaxSize()) {

        // Search bar
        item {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.LightGray
                ),
                label = {
                    Text(text = "Buscar instituições ou causas.", style = MaterialTheme.typography.labelSmall)
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.tertiary)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search)
            )
        }

        // Categorias
        item {
            CategoryLazyRow(
                selectedCategory = selectedCategory,
                onCategoryClick = { selectedCategory = it }
            )
        }

        item { Spacer(Modifier.height(16.dp)) }

        // Título Necessidades
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Necessidades Urgentes",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // LazyRow de Necessidades
        item {
            LazyRow {
                items(necessidades) { necessidade ->
                    NecessidadeCardItem(necessidade = necessidade)
                }
            }
        }

        // Título Instituições
        item {
            Text(
                text = "Instituições Próximas",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }

        // Lista de Instituições
        items(instituicoes) { instituicao ->
            InstituicaoCardItem(instituicao = instituicao)
        }

        // Título Campanhas
        item {
            Text(
                text = "Campanhas em Destaque",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        // LazyRow de Campanhas
        item {
            LazyRow {
                items(getAllCampanhas()) { campanha ->
                    CampanhaItem(
                        campanha = campanha,
                        onSaberMaisClick = { }
                    )
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Preview
@Composable
private fun ContentScreenPreview() {
    DoafacilTheme {
        ContentScreen(navController = rememberNavController())
    }
}