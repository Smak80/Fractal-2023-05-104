package gui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun saveOpenMenuItems(imageSave: () -> Unit,
                      fractalSave: () -> Unit,
                      fractalLoad: () -> Unit,
                      close: () -> Unit) {
    var isDialogVisible by remember { mutableStateOf(false) }

    DropdownMenuItem({ isDialogVisible = true })
    { Text("Сохранить") }

    DropdownMenuItem({
        close()
        fractalLoad()
    }){ Text("Открыть") }

    if (isDialogVisible) {
        Dialog(
            onDismissRequest = {
                close()
                isDialogVisible = false
                               },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Как вы хотите сохранить файл?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            close()
                            isDialogVisible = false },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) { Icon(Icons.Default.Close, "Закрыть") }
                }

                // Кнопки в строку
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Кнопка "Изображение"
                    Button(
                        onClick = {
                            imageSave()
                            close()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text("Изображение")
                    }
                    // Кнопка "Фрактал"
                    Button(
                        onClick = {
                            fractalSave()
                            close()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text("Фрактал")
                    }
                }
            }
        }
    }
}