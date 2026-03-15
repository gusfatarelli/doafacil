package br.com.fiap.doafacil.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.components.CampanhaItem
import br.com.fiap.doafacil.components.CategoryItem
import br.com.fiap.doafacil.components.Instituicao
import br.com.fiap.doafacil.components.InstituicaoCardItem
import br.com.fiap.doafacil.components.Necessidade
import br.com.fiap.doafacil.components.NecessidadeCardItem
import br.com.fiap.doafacil.model.Category
import br.com.fiap.doafacil.model.PriorityLevel
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.repository.getAllCampanhas
import br.com.fiap.doafacil.repository.getAllCategories
import br.com.fiap.doafacil.ui.theme.DoafacilTheme
import br.com.fiap.doafacil.ui.theme.GreyText

@Composable
fun HomeScreen(navController: NavController, email: String?) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val nome = userPrefs.getName()
        .takeIf { it.isNotBlank() }
        ?: email?.substringBefore("@")
        ?: ""

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = { MyBottomNavigation(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Novo")
                }
            }
        ) { paddingValues ->
            ContentScreen(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                nome = nome
            )
        }
    }
}

@Composable
fun HomeHeader(nome: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo + nome do app
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.logo_doafacil),
                    contentDescription = "Logo DoaFácil",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "DoaFácil",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(22.dp)
                )
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeSaudacao(nome: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Olá, ${nome.replaceFirstChar { it.uppercase() }}!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "O que você quer doar hoje?",
            style = MaterialTheme.typography.bodyMedium,
            color = GreyText
        )
    }
}

@Composable
fun HomeSearchBar() {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(50.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedBorderColor   = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor   = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(
                text = "Buscar instituições ou causas",
                style = MaterialTheme.typography.bodyMedium,
                color = GreyText
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = GreyText,
                modifier = Modifier.size(20.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction    = ImeAction.Search
        ),
        singleLine = true
    )
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    nome: String = ""
) {
    val necessidades = listOf(
        Necessidade(
            titulo = "Cestas Básicas 🧺",
            instituicaoNome = Instituicao("ONG Vida Nova", "", ""),
            prioridade = PriorityLevel.URGENTE, progress = 80, quantity = 20
        ),
        Necessidade(
            titulo = "Fraldas G 👶",
            instituicaoNome = Instituicao("Abrigo Esperança", "", ""),
            prioridade = PriorityLevel.URGENTE, progress = 60, quantity = 40
        ),
        Necessidade(
            titulo = "Agasalhos 🧥",
            instituicaoNome = Instituicao("ONG Vida Nova", "", ""),
            prioridade = PriorityLevel.URGENTE, progress = 45, quantity = 55
        ),
        Necessidade(
            titulo = "Leite em Pó 🥛",
            instituicaoNome = Instituicao("Abrigo Feliz", "", ""),
            prioridade = PriorityLevel.NORMAL, progress = 30, quantity = 70
        )
    )

    val instituicoes = listOf(
        Instituicao("Abrigo Feliz", "Crianças | Roupas | Alimentos", "3.2 km", PriorityLevel.URGENTE),
        Instituicao("Banco de Alimentos SP", "Fome | Alimentos", "4.1 km")
    )

    var selectedCategory by remember { mutableStateOf<Category?>(getAllCategories().first()) }

    LazyColumn(modifier = modifier.fillMaxSize()) {

        // Header
        item { HomeHeader(nome = nome) }

        // Saudação
        item { HomeSaudacao(nome = nome) }

        // Search bar
        item { HomeSearchBar() }

        item { Spacer(Modifier.height(6.dp)) }

        // Categorias
        item {
            SectionTitle("Categorias")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                items(getAllCategories().filter { it.name.isNotEmpty() }) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = selectedCategory?.id == category.id,
                        onCategoryClick = { selectedCategory = it }
                    )
                }
            }
        }

        item { Spacer(Modifier.height(4.dp)) }

        // Necessidades Urgentes
        item { SectionTitle("Necessidades Urgentes 🔥") }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(necessidades) { necessidade ->
                    NecessidadeCardItem(necessidade = necessidade)
                }
            }
        }

        // Instituições Próximas
        item { SectionTitle("Instituições Próximas 📍") }

        items(instituicoes) { instituicao ->
            InstituicaoCardItem(instituicao = instituicao)
        }

        // Campanhas em Destaque
        item { SectionTitle("Campanhas em Destaque ✨") }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(getAllCampanhas()) { campanha ->
                    CampanhaItem(campanha = campanha, onSaberMaisClick = {})
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    DoafacilTheme {
        HomeScreen(navController = rememberNavController(), email = "joao@exemplo.com")
    }
}