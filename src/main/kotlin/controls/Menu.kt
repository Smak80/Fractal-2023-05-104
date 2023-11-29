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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.awt.image.BufferedImage

@Composable
fun menu(
    saveImage:()->Unit,
    saveFractal:()->Unit,
    openF: ()->Unit,
    back: ()->Unit,
    showVideoDialog: ()->Unit,
    addFrames: BufferedImage,
    themesMap: Map<String,()->Unit>,
    dynamicIterationsCheck: Boolean,
    dynamicIterationsCheckChange: (Boolean)->Unit

){
    var isMenuExpanded by remember { mutableStateOf(false) }
    val list = remember { SnapshotStateList<BufferedImage>() }

    TopAppBar(
        title = {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = "Множество Мондельброта"
            )
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
                            onClick = {
                                isMenuExpanded = false
                                openF()}
                        ){
                            Text("Открыть")
                        }
                    }
                }
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                //Кнопка Назад
                IconButton(
                    onClick = back
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        "Назад"
                    )
                }
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
                        modifier = Modifier.padding(start = 16.dp),
                        onClick = { showVideoDialogBoolean = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text("Создать Видео")
                        if (showVideoDialogBoolean) {
                            // Диалоговое окно
                            Dialog(
                                onDismissRequest = { showVideoDialogBoolean = false },
                                properties = DialogProperties(dismissOnClickOutside = true)
                            ) {
                                workWithVideoDialog(list) { showVideoDialogBoolean = false }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(
                        onClick = {
                            list.add(addFrames)
                            if(list.size > 0){
                                println(list[0].width)
                                println(list[0].height)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            "Добавить Кадр"
                        )
                    }
                }
                //Выбор Цветовой Схемы
                dropdownMenuIcon(
                    themesMap,
                )
                // Checkbox для динамических итераций
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = dynamicIterationsCheck,
                        onCheckedChange = dynamicIterationsCheckChange,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "D. итерации",
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }
            }
        },
        modifier = Modifier.height(65.dp)
    )
}