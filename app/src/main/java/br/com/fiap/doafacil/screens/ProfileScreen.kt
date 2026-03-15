package br.com.fiap.doafacil.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.navigation.Destination
import br.com.fiap.doafacil.repository.UserPreferences
import br.com.fiap.doafacil.ui.theme.LightGreen

data class ImpactStat(val value: String, val icon: ImageVector, val label: String, val hasBadge: Boolean = false)
data class AchievementItemData(val title: String, val icon: ImageVector, val isUnlocked: Boolean)

val PrimaryBlue  = Color(0xFF1A4789)
val SuccessGreen = Color(0xFF4CAF50)


@Composable
fun ProfileScreen(navController: NavController) {
    val context  = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val email    = userPrefs.getEmail()
    val nome     = userPrefs.getName().takeIf { it.isNotBlank() } ?: email
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = { MyBottomNavigation(navController = navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(PrimaryBlue)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CloudQueue, contentDescription = null, tint = Color.White)
                    Text(
                        text = stringResource(R.string.meu_perfil),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 70.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Foto de perfil
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Image(
                                painter = painterResource(id = R.drawable.boy),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))


                        Text(
                            text = if (email == "convidado") "Convidado" else nome,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (email != "convidado") {
                            Text(text = email, color = Color.Gray, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = { },
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(stringResource(R.string.editar_perfil), fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(stringResource(R.string.caminho_impacto))
                        ImpactCard()

                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(stringResource(R.string.suas_conquistas))
                        AchievementsRow()

                        Spacer(modifier = Modifier.height(24.dp))
                        MenuOptionsSection(navController)

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}

@Composable
fun ImpactCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ImpactItem("15",  Icons.Default.Handshake,  "Doações",   true)
            ImpactItem("120", Icons.Default.Restaurant, "Refeições")
            ImpactItem("3",   Icons.Default.Groups,     "Famílias")
        }
    }
}

@Composable
fun ImpactItem(value: String, icon: ImageVector, label: String, badge: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            if (badge) {
                Icon(
                    Icons.Default.CheckCircle,
                    null,
                    tint = SuccessGreen,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 2.dp)
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, modifier = Modifier.size(14.dp), tint = Color(0xFFB47C00))
            Text(label, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
fun AchievementsRow() {
    val items = listOf(
        AchievementItemData("Doador\nIniciante", Icons.Default.Eco,    true),
        AchievementItemData("Pets",              Icons.Default.Pets,   true),
        AchievementItemData("Padrinho\nde Livros", Icons.Default.Book, true),
        AchievementItemData("Apoio",             Icons.Default.Groups, false)
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(70.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                        .background(
                            if (item.isUnlocked) Color(0xFFE8F5E9) else Color(0xFFF0F0F0)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        item.icon,
                        null,
                        tint = if (item.isUnlocked) SuccessGreen else Color.LightGray,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(item.title, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
fun MenuOptionsSection(navController: NavController) {
    val context   = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    val options = listOf(
        stringResource(R.string.historico_doacoes)    to Icons.Default.BarChart,
        stringResource(R.string.instituicoes_favoritas) to Icons.Default.StarBorder,
        stringResource(R.string.configuracoes)        to Icons.Default.Settings,
        stringResource(R.string.sair_conta)           to Icons.Default.Output
    )

    options.forEach { (title, icon) ->
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth()
                    .clickable {
                        if (title == context.getString(R.string.sair_conta)) {
                            userPrefs.clearUser()
                            navController.navigate(Destination.LoginScreen.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, null, modifier = Modifier.size(20.dp), tint = Color.Gray)
                Text(title, modifier = Modifier.padding(start = 12.dp), fontSize = 14.sp)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
            }
        }
    }
}


@Composable
fun MyBottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentBase = currentRoute?.substringBefore("/")

    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = currentBase == Destination.HomeScreen.route,
            onClick = {
                // Navega para Home usando a rota com argumento guardada no back-stack,
                // ou busca o e-mail salvo caso não exista. Evita sair do app.
                val context = navController.context
                val userPrefs = UserPreferences(context)
                val email = userPrefs.getEmail().ifBlank { "convidado" }
                navController.navigate("${Destination.HomeScreen.route}/$email") {
                    // Volta até a raiz do grafo sem destruir
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(stringResource(R.string.nav_inicio)) },
            icon  = { Icon(Icons.Default.Home, null) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LightGreen,
                selectedTextColor = LightGreen,
                indicatorColor    = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentBase == Destination.ExplorationScreen.route,
            onClick = {
                navController.navigate(Destination.ExplorationScreen.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(stringResource(R.string.nav_explorar)) },
            icon  = { Icon(Icons.Default.Search, null) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LightGreen,
                selectedTextColor = LightGreen,
                indicatorColor    = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentBase == Destination.ProfileScreen.route,
            onClick = {
                navController.navigate(Destination.ProfileScreen.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = { Text(stringResource(R.string.nav_perfil)) },
            icon  = { Icon(Icons.Default.Person, null) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LightGreen,
                selectedTextColor = LightGreen,
                indicatorColor    = Color.Transparent
            )
        )
    }
}
