package controls

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun menu(
    saveImage:()->Unit,
    saveFractal:()->Unit,
    openF: ()->Unit,
    back: ()->Unit,
    showVideoDialog: ()->Unit,
    themesMap: Map<String,()->Unit>,
    dynamicIterationsCheck: Boolean,
    dynamicIterationsCheckChange: (Boolean)->Unit

){
    var isMenuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = "Множество Мондельброта")
        },

        navigationIcon = {
            IconButton(
                onClick = { isMenuExpanded = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Меню")
                if (isMenuExpanded) {
                    // Выпадающий список
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        showSaveDialog("Cохранить",
                            {
                                saveImage()
                                isMenuExpanded = false
                            }, {
                                saveFractal()
                                isMenuExpanded  = false
                            })
                        DropdownMenuItem(
                            onClick = openF
                        ){
                            Text("Открыть")
                        }
                    }
                }
            }
        },
        actions = {
            Row {
                //Кнопка Назад
                IconButton(
                    onClick = {
                        back()
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        "Назад"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                //Кнопка создания видео
                Button(
                    onClick = showVideoDialog,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)
                ) {
                    Text("Создать Видео")
                }
                Spacer(modifier = Modifier.width(10.dp))
                //Выбор Цветовой Схемы
                dropdownMenuIcon(
                    themesMap,
                )
                // Checkbox для динамических итераций
                Checkbox(
                    checked = dynamicIterationsCheck,
                    onCheckedChange = dynamicIterationsCheckChange,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)) {
                            append("Динамические\n")
                            append("     итерации")
                        }
                    }
                )
            }
        }
    )
}