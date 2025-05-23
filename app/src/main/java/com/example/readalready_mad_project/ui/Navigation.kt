package com.example.readalready_mad_project.ui

sealed class Navigation (val route: String) {
    object BooksScreen : Navigation(route = "my_books_screen")
    object SearchScreen : Navigation(route = "search_books_screen")
    object SettingsScreen : Navigation(route = "settings_screen")
    object SingleBookScreen: Navigation(route = "single_book_screen")
}