import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import controls.dropdownMenuIcon
import controls.mainFractalWindow
import controls.showSaveDialog
import drawing.FractalPainter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.*

@Composable
@Preview
fun App(){
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
                var dynamicIterationsCheck by remember { mutableStateOf(false) }
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
                                            println("image")
                                            isMenuExpanded = false
                                        }, {
                                            println("fractal")
                                            isMenuExpanded  = false
                                        })
                                    DropdownMenuItem(
                                        onClick = {TODO()}
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
                                    TODO()
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
                                onClick = { TODO("") },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)
                            ) {
                                Text("Создать Видео")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            //Выбор Цветовой Схемы
                            dropdownMenuIcon(
                                mapOf(
                                    "Желта Сине Бюрюзовая" to { println("ssdasdasfafsafd") },
                                    "Синяя" to { println("sda") }
                                ),
                            )
                            // Checkbox для динамических итераций
                            Checkbox(
                                checked = dynamicIterationsCheck,
                                onCheckedChange = { dynamicIterationsCheck = it },
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
