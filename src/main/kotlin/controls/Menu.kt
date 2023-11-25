package controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun menu(
    saveImage:()->Unit,
    saveFractal:()->Unit,
    openF: ()->Unit,
    back: ()->Unit,
    showVideoDialog: ()->Unit,
    addFrames: ()->Unit,
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
                            },{
                                isMenuExpanded = false
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
                //Для Вызова Окна с Видео
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.secondaryVariant,
                            shape = RoundedCornerShape(50.dp)
                        )
                ) {
                    var showVideoDialogBoolean by remember { mutableStateOf(false) }
                    Button(
                        modifier =  Modifier.
                        padding(start = 16.dp),
                        onClick = {showVideoDialogBoolean = true},
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text("Создать Видео")
                        if (showVideoDialogBoolean) {
                            // Диалоговое окно
                            Dialog(
                                onDismissRequest = { showVideoDialogBoolean = false },
                                properties = DialogProperties(dismissOnClickOutside = true))
                            {
                                workWithVideoDialog()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(
                        onClick = addFrames
                    ) {
                        Icon(
                            Icons.Default.Add,
                            "Добавить Кадр"
                        )
                    }
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