import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.Painter
import drawing.SelectionRect
import drawing.convertation.Converter
import drawing.convertation.Plane
import kotlinx.coroutines.launch
import math.fractals.Mandelbrot
import java.awt.FileDialog
import kotlin.math.*

@Composable
@Preview
fun App() {
//    val fp = remember { FractalPainter(Mandelbrot){
//        if (it == 1f) Color.Black
//        else {
//            val r = sin(it*15f).absoluteValue
//            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
//            val b = log2(2f - cos(sin(18*-it)))
//            Color(r, g, b)
//        }
//    }}
//    fp.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)
//    MaterialTheme {
//        DrawingPanel(fp){size ->
//            fp.width = size.width.toInt()
//            fp.height = size.height.toInt()
//            fp.refresh = true
//        }
//        SelectionPanel{
//            fp.plane?.let{ plane ->
//                val xMin = Converter.xScr2Crt(it.topLeft.x, plane)
//                val xMax = Converter.xScr2Crt(it.topLeft.x+it.size.width, plane)
//                val yMax = Converter.yScr2Crt(it.topLeft.y, plane)
//                val yMin = Converter.yScr2Crt(it.topLeft.y+it.size.height, plane)
//                plane.xMin = xMin
//                plane.xMax = xMax
//                plane.yMin = yMin
//                plane.yMax = yMax
//                fp.refresh = true
//            }
//        }
//    }

    menu()
}


@Composable
fun menu(){
    var expandedColors by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val checkedState = remember { mutableStateOf(true) }
    val juliaButtonState = remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth().background(Color.Blue)) {
                Box {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }){
                        Icon(Icons.Filled.Menu, contentDescription = "Кнопка меню")
                    }


                }
                Text("Фрактал", modifier = Modifier.padding(5.dp), color = Color.White)

                Spacer(Modifier.weight(1f, true))

                Button(onClick = {}, enabled = juliaButtonState.value){
                    Text("Построить множество Жюлиа")
                }

                Spacer(Modifier.weight(0.5f, true))

                IconButton(onClick = {}){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Вернуться на шаг назад")
                }

                Box {
                    IconButton(onClick = {expandedMenu = true}){
                        Icon(Icons.Filled.MoreVert, contentDescription = "Сохранение")
                    }
                    DropdownMenu(expanded = expandedMenu, onDismissRequest = {expandedMenu = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {}
                        ) {
                            Text("Сохранить", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {}
                        ) {
                            Text("Сохранить в формате", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {}
                        ) {
                            Text("Выгрузить фрактал", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {checkedState.value = !checkedState.value},
                            //enabled = false
                        ) {
                            Text("Динамическое изменение\nчисла итераций", fontSize = 11.sp)
                            Checkbox(checked = checkedState.value, onCheckedChange = {checkedState.value = it})
                        }
                    }
                }
            }
        },
        drawerContent = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.fillMaxWidth().height(60.dp), contentAlignment = Alignment.CenterStart) {
                    Text("Выбор цветовой схемы", fontSize = 26.sp)
                }
                Button(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                        TODO()
                    }
                }){
                    Text("Цветовая схема №1", fontSize = 10.sp)
                }
                Button(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                        TODO()
                    }
                }){
                    Text("Цветовая схема №2", fontSize = 10.sp)
                }
                Button(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                        TODO()
                    }
                }){
                    Text("Цветовая схема №3", fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.height(30.dp))
                Divider()
                Button(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                        TODO()
                    }
                }){
                    Text("Создать видео(Coming Soon . . .)", fontSize = 10.sp)
                }
            }
        }
    ) {
        Canvas(modifier = Modifier.background(Color.LightGray).fillMaxSize()){

        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionPanel(
    onSelected: (SelectionRect)->Unit
) {
    var rect by remember {mutableStateOf(SelectionRect(Offset.Zero))}
    val fd = remember {
        FileDialog(
            ComposeWindow(),
            "Открыть файл",
            FileDialog.LOAD
        )}
    var showW2 by remember { mutableStateOf(false) }
    if (showW2){
        Window(
            onCloseRequest = { showW2 = false },
            title = "Второе окно"
        ){
            App()
        }
    }
    Canvas(Modifier.fillMaxSize().padding(8.dp).pointerInput(Unit){
        detectDragGestures(
            onDragStart = {
                rect = SelectionRect(it)
            },
            onDrag = {
                rect.addPoint(it)
            },
            onDragEnd = {
                onSelected(rect)
                rect = SelectionRect(Offset.Zero)
            },
            matcher = PointerMatcher.Primary)
    }.clickable {
        //fd.isVisible = true
        showW2 = true
    }){
        drawRect(Color(0f, 1f, 1f, 0.3f), rect.topLeft, rect.size)
    }
}

@Composable
fun DrawingPanel(
    fp: Painter,
    onResize: (Size)-> Unit = {},
) {
    Canvas(Modifier.fillMaxSize().padding(8.dp)) {
        if(fp.width != size.width.toInt() || fp.height != size.height.toInt() )
            onResize(size)

        fp.paint(this)
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