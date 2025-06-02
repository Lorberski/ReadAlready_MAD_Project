package com.example.readalready_mad_project

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.readalready_mad_project.ui.theme.ReadAlreadyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.readalready_mad_project.data.dataStore.AppTheme
import com.example.readalready_mad_project.data.dataStore.ThemePreferences
import com.example.readalready_mad_project.ui.Navigation
import com.example.readalready_mad_project.ui.screens.BookDetailScreenContent
import com.example.readalready_mad_project.ui.screens.BooksScreenContent
import com.example.readalready_mad_project.ui.screens.SearchScreenContent
import com.example.readalready_mad_project.ui.screens.SettingsScreenContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.core.content.edit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val themeFlow = ThemePreferences.getSavedTheme(applicationContext)
            val theme by themeFlow.collectAsState(initial = AppTheme.System)

            ReadAlreadyTheme(
                darkTheme = when(theme){
                    is AppTheme.Light -> false
                    is AppTheme.Dark  -> true
                    is AppTheme.System -> isSystemInDarkTheme()
                }
            ) {
                BottomNavigationBarApp()
            }
        }
    }
}

// Hilfsfunktion zum Speichern der letzten Route
fun saveLastRoute(context: Context, route: String) {
    context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        .edit {
            putString("last_route", route)
        }
}

@Composable
fun BottomNavigationBarApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Lade gespeicherte Route (z. B. book_detail/123) oder Standard-Screen
    val startDestination = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("last_route", Navigation.BooksScreen.route)
            ?: Navigation.BooksScreen.route
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = currentRoute(navController)

                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = "My Books") },
                    label = { Text(stringResource(id = R.string.my_books)) },
                    selected = currentRoute == Navigation.BooksScreen.route || currentRoute?.startsWith("book_detail") == true,
                    onClick = {
                        saveLastRoute(context, Navigation.BooksScreen.route)
                        navController.navigate(Navigation.BooksScreen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search Books") },
                    label = { Text(stringResource(id = R.string.search_books)) },
                    selected = currentRoute == Navigation.SearchScreen.route,
                    onClick = {
                        saveLastRoute(context, Navigation.SearchScreen.route)
                        navController.navigate(Navigation.SearchScreen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text(stringResource(id = R.string.settings)) },
                    selected = currentRoute == Navigation.SettingsScreen.route,
                    onClick = {
                        saveLastRoute(context, Navigation.SettingsScreen.route)
                        navController.navigate(Navigation.SettingsScreen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Navigation.BooksScreen.route) {
                saveLastRoute(context, Navigation.BooksScreen.route)
                BooksScreenContent(navController)
            }

            composable(Navigation.SearchScreen.route) {
                saveLastRoute(context, Navigation.SearchScreen.route)
                SearchScreenContent(navController)
            }
            composable(Navigation.SettingsScreen.route) {
                SettingsScreenContent()
            }

            composable("book_detail/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId")
                if (bookId != null) {
                    val route = "book_detail/$bookId"
                    saveLastRoute(context, route)
                    BookDetailScreenContent(bookId = bookId, navController = navController)
                }
            }

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
fun PreviewBottomBarApp() {
    ReadAlreadyTheme {
        BottomNavigationBarApp()
    }
}


