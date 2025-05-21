package com.example.readalready_mad_project

sealed class Navigation (val route: String) {
    object MyBooksScreen : Navigation(route = "my_books_screen")
    object SearchBooksScreen : Navigation(route = "search_books_screen")
    object SettingsScreen : Navigation(route = "settings_screen")
}