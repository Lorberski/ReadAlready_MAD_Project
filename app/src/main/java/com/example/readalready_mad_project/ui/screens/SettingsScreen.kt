package com.example.readalready_mad_project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.readalready_mad_project.data.dataStore.AppTheme
import com.example.readalready_mad_project.data.dataStore.ThemePreferences
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.readalready_mad_project.R


@Composable
fun SettingsScreenContent(){
    MainContent()
}

@Composable
fun MainContent() {
    val context = LocalContext.current
    val themeFlow = remember { ThemePreferences.getSavedTheme(context) }
    val currentTheme by themeFlow.collectAsState(initial = null)

    var expanded by remember { mutableStateOf(false) }

    val themeOptions = listOf(AppTheme.Light, AppTheme.Dark, AppTheme.System)

    var selectedTheme by remember(currentTheme) {
        mutableStateOf(currentTheme ?: AppTheme.System)
    }

    LaunchedEffect(selectedTheme) {
        if (currentTheme != null && selectedTheme != currentTheme) {
            ThemePreferences.saveTheme(context, selectedTheme)
        }
    }

    if (currentTheme == null) return

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = stringResource(id = R.string.select_theme), style = MaterialTheme.typography.titleLarge)

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(text = selectedTheme.name)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                themeOptions.forEach { theme ->
                    DropdownMenuItem(
                        text = { Text(theme.name) },
                        onClick = {
                            selectedTheme = theme
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
