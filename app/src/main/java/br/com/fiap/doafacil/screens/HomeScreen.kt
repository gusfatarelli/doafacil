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
import br.com.fiap.doafacil.R

@Composable
fun HomeScreen(navController: NavController, email: String?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { br.com.fiap.doafacil.screens.MyTopAppBar(email!!, navController) },
            bottomBar = { BottomAppBar() },
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

    val user = userRespository.getUserByEmail(email)

    val profileBitmap by remember {
        mutableStateOf<Bitmap>(convertByteArrayToBitmap(
            user!!.userImage!!)
        )
    }

    TopAppBar(
        colors = TopAppBarColors = TopAppBarDefaults.topAppBarColors = (
                containerColor = Color.Blue
                ),
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
                            onClick = { navController.navigate("profile/${user!!.email}") }
                        )
                ) {
                    Image(
                        bitmap = profileBitmap.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                text = "Hello, ${user!!.name}!",
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

@Composable
fun BottomAppBar() {
    val itens = listOf(
        BottomNavigationItem("Home", icon = Icons.Default.Home),
        BottomNavigationItem("Explorar", icon = Icons.Default.Search),
        BottomNavigationItem("Profile", icon = Icons.Default.Person),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        itens.forEach { NavigationItem ->
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(imageVector = NavigationItem.icon,
                        contentDescription = NavigationItem.title,
                        tint = MaterialTheme.colorScheme.primary)
                },
                label = {
                    Text(
                    text = NavigationItem.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )}
            )
        }
    }
}

@Composable
fun BottomNavigationItem(x0: String, icon: ImageVector) {
    TODO("Not yet implemented")
}

//TELA DE CONTEÚDO

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController) {

    //Variavel da lista de necessidades
    val categories = getAllCategories()
    //Variavel da lista de campanhas
    val recipes = getAllRecipes()

    Text(
        text = "Olá, $user!!.name",
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displayLarge
    )

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 0.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                //focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray
            ),
            label = {
                Text(
                    text = "Buscar instituições ou causas.",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )

        // Lista de Categorias
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category = category, onClick = {
                    navController.navigate(route = Destination.CategoryRecipeScreen
                        .createRoute(categoryId = category.id))
                })
            }
        }

        Spacer(Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp).height(116.dp)) {
            Image(
                painter = painterResource(R.drawable.speedcard),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillBounds,
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Necessidades Urgentes",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Lista de Necessidades
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category = category, onClick = {
                    navController.navigate(route = Destination.CategoryRecipeScreen
                        .createRoute(categoryId = category.id))
                })
            }
        }

        Text(
            text = "Instituições Próximas",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        Column() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Ícone da casa
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "",
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFFFFF3E0), RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        tint = Color(0xFFE65100)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Coluna central
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Abrigo Feliz",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Crianças | Roupas | Alimentos",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Badges
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // Badge Verificado
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(Color(0xFF4CAF50), RoundedCornerShape(50))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Verificado", color = Color.White, fontSize = 11.sp)
                            }

                            // Badge Urgente
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(Color(0xFFFFE0E0), RoundedCornerShape(50))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(text = "Urgente", color = Color(0xFFE53935), fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.ShoppingBag,
                                    contentDescription = "",
                                    tint = Color(0xFF1565C0),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Coluna direita: distância + ações
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "",
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(text = "3.2 km", fontSize = 12.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Default.Directions,
                                contentDescription = "Como chegar",
                                tint = Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Ver abrigo",
                                tint = Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Campanhas em Destaque",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        LazyRow(contentPadding = PaddingValues(
            vertical = 10.dp,
            horizontal = 16.dp
        ),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(recipes) {recipe ->
                RecipeItem(recipe)
            }
        }

    }
}