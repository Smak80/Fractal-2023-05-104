package controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
fun showSaveDialog(text:String, imageSave:()->Unit, fractalSave:()->Unit){
    var isDialogVisible by remember { mutableStateOf(false) }
    DropdownMenuItem(onClick = { isDialogVisible = true }) {
        Text(text)
    }
    // Диалоговое окно
    if (isDialogVisible) {
        Dialog(
            onDismissRequest = { isDialogVisible = false },
            properties = DialogProperties(dismissOnClickOutside = false),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Текст в диалоговом окне
                    Text(
                        "Как вы хотите сохранить файл?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp).padding(top = 10.dp)
                    )
                    // Кнопки в строку
                    Row(
                        modifier = Modifier.run {
                            fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp )
                        },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Кнопка "Изображение"
                        Button(
                            onClick = {
                                imageSave()
                                isDialogVisible = false
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal =  8.dp)
                        ) {
                            Text("Изображение")
                        }

                        // Кнопка "Фрактал"
                        Button(
                            onClick = {
                                fractalSave()
                                isDialogVisible = false
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal =  8.dp)
                        ) {
                            Text("Фрактал")
                        }
                    }
                }
            }
        )
    }
}