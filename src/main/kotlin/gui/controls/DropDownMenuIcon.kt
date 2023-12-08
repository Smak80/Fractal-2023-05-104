package gui.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import javax.swing.Icon

@Composable
fun dropdownMenuIcon(
    itemFunctions: Map<String, () -> Unit>,
    icon: ImageVector
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        IconButton(onClick = { expanded = !expanded }) {
            Icon( icon, "Dropdown Icon")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            itemFunctions.entries.forEach { (itemName, function) ->
                DropdownMenuItem(onClick = {
                    function()
                    expanded = false
                }) {
                    Text(text = itemName)
                }
            }
        }
    }
}
