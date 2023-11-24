import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import controls.dropdownMenuIcon
import controls.fileMenu
import controls.mainFractalWindow
import drawing.FractalPainter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.*

@Composable
@Preview
fun App(){
    var isMenuExpanded by remember { mutableStateOf(false) }
    val fp = remember { FractalPainter(Mandelbrot){
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
            val b = log2(2f - cos(sin(18*-it)))
            Color(r, g, b)
        }
    }}
    fp.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)
    MaterialTheme{
        Scaffold(
            topBar = {
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
                                DropdownMenu(
                                    expanded = isMenuExpanded,
                                    onDismissRequest = { isMenuExpanded = false },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    // Add menu items here
                                    DropdownMenuItem(onClick = {
                                        // Handle menu item click
                                        isMenuExpanded = false
                                    }) {
                                        Text("Menu Item 1")
                                    }

                                    DropdownMenuItem(onClick = {
                                        // Handle menu item click
                                        isMenuExpanded = false
                                    }) {
                                        Text("Menu Item 2")
                                    }
                                }
                            }
                        }
                    },
                    actions = {
                        //Кнопка Назад
                        IconButton(
                            onClick = {
                                TODO()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Назад")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        //Кнопка создания видео
                        Button(
                            onClick = { TODO("") },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)
                        ) {
                            Text("Создать Видео")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        //Выбор Цветовой Схемы
                        dropdownMenuIcon(
                            mapOf(
                                "Желта Сине Бюрюзовая" to { println("ssdasdasfafsafd") },
                                "Синяя" to { println("sda") }
                            ),
                        )

                        // Checkbox для динамических итераций
                        var isChecked by remember { mutableStateOf(false) }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { isChecked = it },
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                        append("Динамические\n")
                                        append("итерации")
                                    }
                                }
                            )
                        }


                    }
                )
            },
            content = {
                mainFractalWindow(fp)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}




fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта"
    ) {
        App()
    }
}

