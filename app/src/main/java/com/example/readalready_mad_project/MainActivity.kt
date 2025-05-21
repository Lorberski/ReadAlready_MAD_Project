package com.example.readalready_mad_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.readalready_mad_project.ui.theme.ReadAlready_MAD_ProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import com.example.readalready_mad_project.ui.Navigation
import com.example.readalready_mad_project.ui.screens.MyBooksScreenContent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadAlready_MAD_ProjectTheme {
                    BottomNavigationBarApp()
            }
        }
    }
}

@Composable
fun BottomNavigationBarApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = currentRoute(navController)
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = "My Books") },
                    label = { Text("My Books") },
                    selected = currentRoute == Navigation.BooksScreen.route,
                    onClick = { navController.navigate(Navigation.BooksScreen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search Books") },
                    label = { Text("Search Books") },
                    selected = currentRoute == Navigation.SearchScreen.route,
                    onClick = { navController.navigate(Navigation.SearchScreen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentRoute == Navigation.SettingsScreen.route,
                    onClick = { navController.navigate(Navigation.SettingsScreen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }}
                )
            }
        }
    ) { innerPadding ->

        NavHost(navController, startDestination = Navigation.BooksScreen.route, Modifier.padding(innerPadding)) {
            composable(Navigation.BooksScreen.route) { /* Home Screen Composable */
                MyBooksScreenContent()
            }
            composable(Navigation.SearchScreen.route) { /* Books Screen Composable */ }
            composable(Navigation.SettingsScreen.route) { /* Settings Screen Composable */ }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReadAlready_MAD_ProjectTheme {
        BottomNavigationBarApp()
    }
}