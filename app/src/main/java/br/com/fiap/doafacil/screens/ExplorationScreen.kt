package br.com.fiap.doafacil.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import br.com.fiap.doafacil.ui.theme.BluePrimary
import br.com.fiap.doafacil.ui.theme.DoafacilTheme

@Composable
fun ExplorationScreen(navController: NavController, modifier: Modifier = Modifier) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(

            topBar = {},

            bottomBar = { MyBottomNavigation() },

            floatingActionButton = {}
        )
        {
                paddingValues ->
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
                onCategoryClick = { selectedCategory = it }
            )
        }
        items(getAllOngs()) { ong ->
            OngCardItem(ong = ong,
                onDoacaoClick = {navController.navigate("doacao") })
        }
    }
}

@Composable
fun HeaderExploration(modifier: Modifier = Modifier) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = BluePrimary)) {

        Image(
            painter = painterResource(R.drawable.logo_doafacil),
            contentDescription = "",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Explorar Instituições",
            color = MaterialTheme.colorScheme.secondary
        )

    }

}

@Composable
fun SearchBarExploration(modifier: Modifier = Modifier) {

    //variavel para lembrar do texto
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = {newText ->
            searchText = newText
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(50.dp),
        placeholder = {
            Text(text = "Buscar instituições ou causas", style = MaterialTheme.typography.labelSmall)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = BluePrimary)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        )
    )
}

@Composable
fun CategoryLazyRow(
    selectedCategory: Category?,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        items(getAllCategories()) { category ->
            CategoryItem(
                category = category,
                isSelected = selectedCategory?.id == category.id,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Preview
@Composable
private fun ExplorationScreenPreview() {
    DoafacilTheme {
        ExplorationScreen(rememberNavController())
    }
}