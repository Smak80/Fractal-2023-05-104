package controls

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun fileMenu() {
    var saveAsMenuExpanded by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = saveAsMenuExpanded,
        onDismissRequest = { saveAsMenuExpanded = false }
    ) {
        // Open
        DropdownMenuItem(onClick = {
            // Implement logic for opening a file
        }) {
            Text("Open")
        }

        // Save As
        DropdownMenu(
            expanded = saveAsMenuExpanded,
            onDismissRequest = { saveAsMenuExpanded = false }
        ) {
            // Image
            DropdownMenuItem(onClick = {
                // Implement logic for saving as image
            }) {
                Text("Image")
            }

            // Fractal
            DropdownMenuItem(onClick = {
                // Implement logic for saving as fractal
            }) {
                Text("Fractal")
            }
        }

        // Save As menu item
        DropdownMenuItem(
            onClick = { saveAsMenuExpanded = !saveAsMenuExpanded },
        ) {
            Text("Save As")
        }
    }
}