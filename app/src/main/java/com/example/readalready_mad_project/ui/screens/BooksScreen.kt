package com.example.readalready_mad_project.ui.screens

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.readalready_mad_project.viewmodel.BooksViewModel

@Composable
fun MyBooksScreenContent(
    viewModel: BooksViewModel = hiltViewModel()
){
    Greeting("Hallo from Content")
    SimpleButton(
        onClick = {
            viewModel.searchBooks("harry")
            Log.d("BookRepository", "Button clicked")
        },
        text = "Search Books"
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}




@Composable
fun SimpleButton(onClick: () -> Unit, text: String) {
    Button(onClick = onClick) {
        Text(text)
    }
}
