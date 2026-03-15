package br.com.fiap.doafacil.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.components.CampanhaItem
import br.com.fiap.doafacil.components.Instituicao
import br.com.fiap.doafacil.components.InstituicaoCardItem
import br.com.fiap.doafacil.components.Necessidade
import br.com.fiap.doafacil.components.NecessidadeCardItem
import br.com.fiap.doafacil.model.Category
import br.com.fiap.doafacil.model.PriorityLevel
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.repository.getAllCampanhas
import br.com.fiap.doafacil.repository.getAllCategories
import br.com.fiap.doafacil.ui.theme.BluePrimary
import br.com.fiap.doafacil.ui.theme.DarkBlue
import br.com.fiap.doafacil.ui.theme.DeepGrey
import br.com.fiap.doafacil.ui.theme.DoafacilTheme
import br.com.fiap.doafacil.ui.theme.GreenBackground
import br.com.fiap.doafacil.ui.theme.GreyText
import br.com.fiap.doafacil.ui.theme.LightGreen

@Composable
fun HomeScreen(navController: NavController, email: String?) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val nome = userPrefs.getName().ifEmpty { email ?: "" }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            containerColor = Color.White,
            bottomBar = { MyBottomNavigation(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    shape = CircleShape,
                    containerColor = LightGreen
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Novo",
                        tint = DarkBlue
                    )
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
            .background(BluePrimary)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.logo_doafacil),
                    contentDescription = "Logo",
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(R.drawable.boy),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border( 1.dp, Color.White, CircleShape )
                )
            }
        }
    }
}

// ── SAUDAÇÃO ────────────────────────────────────────────────────────────────

@Composable
fun HomeSaudacao(nome: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Olá, ${nome.replaceFirstChar { it.uppercase() }}! 👋",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = DarkBlue
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "O que você quer doar hoje?",
            fontSize = 14.sp,
            color = GreyText
        )
    }
}

// ── SEARCH BAR ──────────────────────────────────────────────────────────────

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
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = BluePrimary,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        placeholder = {
            Text(
                text = "Buscar instituições ou causas...",
                style = MaterialTheme.typography.labelSmall,
                color = GreyText
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = BluePrimary
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        singleLine = true
    )
}

// ── TÍTULO DE SEÇÃO ─────────────────────────────────────────────────────────

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = DarkBlue,
        modifier = modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )
}

// ── CONTEÚDO PRINCIPAL ───────────────────────────────────────────────────────

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    nome: String = ""
) {
    val necessidades = listOf(
        Necessidade(
            titulo = "Cestas Básicas 🧺",
            instituicaoNome = Instituicao(nome = "ONG Vida Nova", categorias = "", distancia = ""),
            prioridade = PriorityLevel.URGENTE, progress = 80, quantity = 20
        ),
        Necessidade(
            titulo = "Fraldas G 👶",
            instituicaoNome = Instituicao(nome = "Abrigo Esperança", categorias = "", distancia = ""),
            prioridade = PriorityLevel.URGENTE, progress = 60, quantity = 40
        ),
        Necessidade(
            titulo = "Agasalhos",
            instituicaoNome = Instituicao(nome = "ONG Vida Nova", categorias = "", distancia = ""),
            prioridade = PriorityLevel.URGENTE, progress = 45, quantity = 55
        ),
        Necessidade(
            titulo = "Leite em Pó",
            instituicaoNome = Instituicao(nome = "Abrigo Feliz", categorias = "", distancia = ""),
            prioridade = PriorityLevel.NORMAL, progress = 30, quantity = 70
        )
    )

    val instituicoes = listOf(
        Instituicao(
            nome = "Abrigo Feliz",
            categorias = "Crianças | Roupas | Alimentos",
            distancia = "3.2 km",
            prioridade = PriorityLevel.URGENTE
        ),
        Instituicao(
            nome = "Banco de Alimentos SP",
            categorias = "Fome | Alimentos",
            distancia = "4.1 km"
        )
    )

    var selectedCategory by remember { mutableStateOf<Category?>(getAllCategories().first()) }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        // Header azul
        item { HomeHeader(nome = nome) }

        // Saudação
        item { HomeSaudacao(nome = nome) }

        // Search bar
        item { HomeSearchBar() }

        item { Spacer(Modifier.height(4.dp)) }

        // Categorias
        item {
            SectionTitle("Categorias")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(getAllCategories().filter { it.name.isNotEmpty() }) { category ->
                    br.com.fiap.doafacil.components.CategoryItem(
                        category = category,
                        isSelected = selectedCategory?.id == category.id,
                        onCategoryClick = { selectedCategory = it }
                    )
                }
            }
        }

        item { Spacer(Modifier.height(4.dp)) }

        // Necessidades Urgentes
        item { SectionTitle("Necessidades Urgentes") }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(necessidades) { necessidade ->
                    NecessidadeCardItem(necessidade = necessidade)
                }
            }
        }

        // Instituições Próximas
        item { SectionTitle("Instituições Próximas 📍") }

        items(instituicoes) { instituicao ->
            InstituicaoCardItem(
                instituicao = instituicao,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // Campanhas em Destaque
        item { SectionTitle("Campanhas em Destaque") }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(getAllCampanhas()) { campanha ->
                    CampanhaItem(
                        campanha = campanha,
                        onSaberMaisClick = {}
                    )
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Preview
@Composable
private fun ContentScreenPreview() {
    DoafacilTheme {
        ContentScreen(navController = rememberNavController())
    }
}