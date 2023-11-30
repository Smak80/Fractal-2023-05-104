import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import controls.mainFractalWindow
import controls.menu
import drawing.FractalPainter
import drawing.convertation.Plane
import math.fractals.Mandelbrot
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.sin

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
                var isVideoDialogVisible by remember { mutableStateOf(false) }
                menu(
                    saveImage = { TODO("ПЕРЕДАТЬ ФУНКЦИЮ ДЛЯ СОХРАНЕНИЯ КАК КАРТИНКИ")},
                    saveFractal = { TODO("ПЕРЕДАТЬ ФУНКЦИЮ ДЛЯ СОХРАНИНИЯ КАК СОБСТВЕННЫЙ ТИП")},
                    openF = { TODO("ДЛЯ ОТКРЫТИЯ ФАЙЛА В СОБСТВЕННОМ ТИПЕ")},
                    back = { TODO("ОТМЕНА ДЕЙСТВИЯ")},
                    showVideoDialog = {},
                    addFrames = {TODO("Добавления Кадров к Экскурсии")},
                    //ТУТ ПЕРЕДАЕТСЯ КАРТА {НАЗВАНИЕ -> ФУНКЦИЯ}, в неё мохно передавать цветовые схемы, сколько угодно.
                    //т.е когда пользователь будет нажимать на название, то вызывается функция, которая меняет фрактал
                    themesMap = mapOf(),
                    //Это Boolean значение для динамических итераций, Переключатель True/False. Тут менять ничего не нужно.
                    //Нужно просто реализовать логику изменения
                    dynamicIterationsCheck = dynamicIterationsCheck,
                    dynamicIterationsCheckChange =  {dynamicIterationsCheck = it},
                )
            },
            modifier = Modifier.fillMaxSize()){
            Box(
                Modifier.fillMaxSize()
            ){
                mainFractalWindow(fp)
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта",
        state = rememberWindowState(
            width = 800.dp,
            height = 600.dp,
            placement = WindowPlacement.Floating,
            position = WindowPosition(100.dp, 100.dp),
            isMinimized = false
        ),
    ) {
        App()
    }
}

fun testBranch(){
    println("Test Branch")
}