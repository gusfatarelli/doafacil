package br.com.fiap.doafacil.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.doafacil.R
import br.com.fiap.doafacil.components.CategoryItem
import br.com.fiap.doafacil.components.OngCardItem
import br.com.fiap.doafacil.model.Category
import br.com.fiap.doafacil.repository.getAllCategories
import br.com.fiap.doafacil.repository.getAllOngs
import br.com.fiap.doafacil.ui.theme.DoafacilTheme
import br.com.fiap.doafacil.ui.theme.GreyText


@Composable
fun ExplorationScreen(navController: NavController, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = { MyBottomNavigation(navController = navController) }
        ) { paddingValues ->
            ContentScreenExploration(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@Composable
fun ContentScreenExploration(modifier: Modifier = Modifier, navController: NavController) {
    var selectedCategory by remember { mutableStateOf<Category?>(getAllCategories().first()) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        item { HeaderExploration() }
        item { SearchBarExploration() }
        item {
            CategoryLazyRow(
                selectedCategory = selectedCategory,
                onCategoryClick = { selectedCategory = it },
                // filtra vazios — igual ao que a HomeScreen já faz
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        items(getAllOngs()) { ong ->
            OngCardItem(
                ong = ong,
                onDoacaoClick = { navController.navigate("doacao") }
            )
        }
    }
}

// HEADER

@Composable
fun HeaderExploration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.logo_doafacil),
                contentDescription = "Logo DoaFácil",
                modifier = Modifier.size(80.dp)
            )
            Spacer( modifier = Modifier.width(30.dp) )
            Text(
                text = stringResource(R.string.explorar_instituicoes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,

            )
        }
    }
}

// SEARCH BAR

@Composable
fun SearchBarExploration(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(50.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor   = MaterialTheme.colorScheme.outline,
            focusedBorderColor     = MaterialTheme.colorScheme.primary,
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
            imeAction = ImeAction.Search
        ),
        singleLine = true
    )
}

// CATEGORIAS

@Composable
fun CategoryLazyRow(
    selectedCategory: Category?,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items(getAllCategories().filter { it.name.isNotEmpty() }) { category ->
            CategoryItem(
                category    = category,
                isSelected  = selectedCategory?.id == category.id,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

// PREVIEW

@Preview(showSystemUi = true)
@Composable
private fun ExplorationScreenPreview() {
    DoafacilTheme {
        ExplorationScreen(rememberNavController())
    }
}
