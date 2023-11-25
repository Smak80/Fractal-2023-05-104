import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import androidx.compose.ui.zIndex
import controls.*
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
                var isVideoDialogVisible by remember { mutableStateOf(false) }
                menu(
                    saveImage = { TODO("ПЕРЕДАТЬ ФУНКЦИЮ ДЛЯ СОХРАНЕНИЯ КАК КАРТИНКИ")},
                    saveFractal = { TODO("ПЕРЕДАТЬ ФУНКЦИЮ ДЛЯ СОХРАНИНИЯ КАК СОБСТВЕННЫЙ ТИП")},
                    openF = { TODO("ДЛЯ ОТКРЫТИЯ ФАЙЛА В СОБСТВЕННОМ ТИПЕ")},
                    back = { TODO("ОТМЕНА ДЕЙСТВИЯ")},
                    showVideoDialog = {

                                      },
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
            content = {
                Row{
                    Box(
                    ){
                        mainFractalWindow(fp)
                    }
                }
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
