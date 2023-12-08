import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.SelectionRect
import drawing.colors.colors
import drawing.convertation.Converter
import drawing.convertation.Plane
import java.awt.Dimension
import math.fractals.Fractal
import math.fractals.funcs



@Composable
@Preview
fun App() {

    //val fractalColor = remember { mutableStateOf("color1")  }
    val fp = remember { mutableStateOf(FractalPainter(Fractal, colors["color1"]!!))}
    

    fp.value.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)

    fp.value.plane?.let {
        fp.value.xMin = it.xMin
        fp.value.xMax = it.xMax
        fp.value.yMax = it.yMax
        fp.value.yMin = it.yMin
    }

    MaterialTheme {
        menu(fp)
    }
}


@Composable
fun menu(fp:  MutableState<FractalPainter>){
    val fractalColor = remember { mutableStateOf("color1")  }
    var expandedMenu by remember { mutableStateOf(false) }
    var expandedMenuColor by remember { mutableStateOf(false) }
    var expandedFractalFunctions by remember { mutableStateOf(false) }
    val checkedState = remember { mutableStateOf(true) }
    val juliaButtonState = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth().background(Color.Blue)) {

                Box {
                    Button(onClick = {expandedMenuColor = true}){
                        Text("Цвета")
                    }
                    DropdownMenu(expanded = expandedMenuColor, onDismissRequest = {expandedMenuColor = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fp.value.colorFunc != colors["color1"]){
                                    fractalColor.value = "color1"
                                    println("Changing color to color1")
//                                    fp.value.colorFunc = colors["color1"]!!
                                }
                            }
                        ) {
                            Text("1 цвет", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fp.value.colorFunc != colors["color2"]){
                                    fractalColor.value = "color2"
                                    println("Changing color to color2")
//                                    fp.value.colorFunc = colors["color2"]!!
                                }
                            }
                        ) {
                            Text("2 цвет", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fp.value.colorFunc != colors["color3"]){
                                    fractalColor.value = "color3"
                                    println("Changing color to color3")
//                                    fp.value.colorFunc = colors["color3"]!!

                                }
                            }
                        ) {
                            Text("3 цвет", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                }

                Box {
                    Button(onClick = {expandedFractalFunctions = true}){
                        Text("Функции")
                    }
                    DropdownMenu(expanded = expandedFractalFunctions, onDismissRequest = {expandedFractalFunctions = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = { Fractal.function = funcs["Mandelbrot"]!! }
                        ) {
                            Text("1 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {Fractal.function = funcs["square"]!!}
                        ) {
                            Text("2 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {Fractal.function = funcs["third_pow"]!!}
                        ) {
                            Text("3 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {Fractal.function = funcs["multiply_and_plus"]!!}
                        ) {
                            Text("4 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                }


                Spacer(Modifier.weight(1f, true))

                Button(onClick = {TODO()}, enabled = juliaButtonState.value){
                    Text("Построить множество Жюлиа")
                }

                Spacer(Modifier.weight(0.5f, true))

                var i = 0                                                                                   //i убрать. Сделал так, чтобы ошибка не вылетаал
                IconButton(onClick = {i++}){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Вернуться на шаг назад")
                }

                Box {
                    IconButton(onClick = {expandedMenu = true}){
                        Icon(Icons.Filled.MoreVert, contentDescription = "Сохранение")
                    }
                    DropdownMenu(expanded = expandedMenu, onDismissRequest = {expandedMenu = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {TODO()}
                        ) {
                            Text("Сохранить", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {TODO()}
                        ) {
                            Text("Сохранить в формате", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {TODO()}
                        ) {
                            Text("Выгрузить фрактал", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {checkedState.value = !checkedState.value}
                        ) {
                            Text("Динамическое изменение\nчисла итераций", fontSize = 11.sp)
                            Checkbox(checked = checkedState.value, onCheckedChange = {checkedState.value = it})
                        }
                    }
                }
            }
        }
    ) {
            DrawingPanel(fp.value, fractalColor){size ->
                fp.value.width = size.width.toInt()
                fp.value.height = size.height.toInt()
                fp.value.refresh = true
            }
            SelectionPanel{
                fp.value.plane?.let{ plane ->
                    val xMin = Converter.xScr2Crt(it.topLeft.x, plane)
                    val xMax = Converter.xScr2Crt(it.topLeft.x+it.size.width, plane)
                    val yMax = Converter.yScr2Crt(it.topLeft.y, plane)
                    val yMin = Converter.yScr2Crt(it.topLeft.y+it.size.height, plane)

                    //fp.value.plane = fp.value.plane
                    //println(fpPlane.value)

                    fp.value.xMin = xMin
                    fp.value.xMax = xMax
                    fp.value.yMin = yMin
                    fp.value.yMax = yMax
                    fp.value.refresh = true
                }
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionPanel(
    onSelected: (SelectionRect)->Unit
) {
    var rect by remember {mutableStateOf(SelectionRect(Offset.Zero))}
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
    }){
        drawRect(Color(0f, 1f, 1f, 0.3f), rect.topLeft, rect.size)
    }
}

@Composable
fun DrawingPanel(
    fp: FractalPainter,
    fpcolors:  MutableState<String>,
    onResize: (Size)-> Unit = {},
) {

    Canvas(Modifier.fillMaxSize().padding(8.dp)) {


        if(fp.width != size.width.toInt() || fp.height != size.height.toInt() ||  fp.colorFunc != colors[fpcolors.value]) {

            onResize(size)
            fp.setColorFunction(colors[fpcolors.value]!!) // Это добавленная строка
        }
//        if(fp.colorFunc != colors[fpcolors.value]){
//            fp.setColorFunction(colors[fpcolors.value]!!) // Это добавленная строка
//            println(fpcolors.value)
//
//        }
        fp.paint(this)
    }
}


fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта"
    ) {
        this.window.minimumSize = Dimension(600, 400)
        App()
    }
}