package com.example.readalready_mad_project.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


interface FilterOption {
    @Composable
    fun label(): String
}



@Composable
fun <T> FilterBar(
    selected: T,
    onFilterSelected: (T) -> Unit,
    options: Array<T>
) where T : Enum<T>, T : FilterOption {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { expanded = true }) {
            Text("Filter: ${selected.label()}")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label()) },
                    onClick = {
                        onFilterSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
