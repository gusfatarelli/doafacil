package br.com.fiap.doafacil.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.ui.theme.DoafacilTheme


data class ImpactStat(val value: String, val icon: ImageVector, val label: String)
data class Achievement(val value: String, val icon: ImageVector, val isUnlocked: Boolean)

@Composable
fun ProfileHeader(name: String, email: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.boy),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = email,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}) {
            Text("Editar Perfil")
        }
    }
}

@Composable
fun ImpactSection(stats: List<ImpactStat>) {
    Card(shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            stats.forEach { stat ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(stat.icon, contentDescription = stat.label, modifier = Modifier.size(32.dp))
                    Text(stat.value, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    Text(stat.label, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun AchievementItem(achievement: Achievement) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(if (achievement.isUnlocked) Color.Green.copy(alpha = 0.1f) else Color.LightGray)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                achievement.icon,
                contentDescription = null,
                tint = if (achievement.isUnlocked) Color.Green else Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = achievement.title, fontSize = 12.sp, maxLines = 1)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.doaf_cil),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    Card(
                        modifier = Modifier
                            .size(48.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_doafacil),
                            contentScale = "Logo do app"
                    }
                }
            }
        }
    )
}


@Preview
@Composable
private fun MyTopAppBarPreview() {
    DoafacilTheme {
        MyTopAppBar()
    }

}

data class BottomNavigationItems(val title: String, val icon: ImageVector)

@Composable
fun MyBottomAppBar(modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavigationItems("Home", icon = Icons.Default.Home),
        BottomNavigationItems("Explorar", icon = Icons.Default.Search),
        BottomNavigationItems("Perfil", icon = Icons.Default.PermIdentity),
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun MyBottomAppBarPreview() {
    DoafacilTheme {
        MyBottomAppBar()
    }

}

@Preview
@Composable
private fun ProfileHeaderPreview() {
    DoafacilTheme {
        ProfileHeader()
    }
}