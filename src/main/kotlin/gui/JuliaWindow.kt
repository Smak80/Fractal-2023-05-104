import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.*
import drawing.FractalPainter
import drawing.convertation.ColorFuncs
import drawing.convertation.Plane
import gui.controls.dropdownMenuIcon
import gui.fractalWindow
import gui.mainFractalWindow
import gui.saveOpenMenuItems
import gui.video.workWithVideoDialog
import math.fractals.FractalData
import math.fractals.FractalFunks
import math.fractals.JuliaSet
import math.fractals.Mandelbrot
import tools.ActionStack
import tools.FileManager
import video.Cadre
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.UIManager

@Composable
fun JuliaApp(mandelbrotFp:FractalPainter) {
    val plane = Plane(-2.0, 2.0, -2.0, 2.0, 0f, 0f)
    val fp = remember { FractalPainter(JuliaSet)}
    val actionStack = ActionStack(fp)
    var dynamicIterationsCheck by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    fp.colorFuncID = mandelbrotFp.colorFuncID
    fp.initPlane(plane)

    fun preSaveFractalData(){
        fp.plane?.let {
            val currConf = FractalData(
                it.xMin,
                it.xMax,
                it.yMin,
                it.yMax,
                fp.colorFuncID,
                Mandelbrot.funcNum
            )
            actionStack.push(currConf)
        }
    }

    MaterialTheme{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            text = "FractaLAB"
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { isMenuExpanded = true }) {
                            Icon(LineAwesomeIcons.Save, "Меню")
                            if (isMenuExpanded) {
                                // Выпадающий список
                                DropdownMenu(
                                    expanded = isMenuExpanded,
                                    onDismissRequest = { isMenuExpanded = false }
                                ) {
                                    saveOpenMenuItems(
                                        {
                                            fp.plane?.let{
                                                FileManager.saveImageData(fp)
                                            }
                                            isMenuExpanded = false
                                        }, {
                                            fp.plane?.let{
                                                val fractalData = FractalData(it.xMin,it.xMax,it.yMin,it.yMax, fp.colorFuncID,Mandelbrot.funcNum)
                                                FileManager.saveFractalData(fractalData)
                                            }
                                        }, {
                                            val resData = FileManager.loadFractalData()
                                            resData?.let { fd ->
                                                fp.plane?.let { plane ->
                                                    preSaveFractalData()
                                                    fp.initPlane(Plane(fd.xMin, fd.xMax, fd.yMin, fd.yMax, plane.width, plane.height))
                                                    fp.colorFuncID = fd.colorscheme
                                                    Mandelbrot.funcNum = fd.fractalFunk
                                                }
                                            }
                                            fp.refresh = true
                                        }, { isMenuExpanded = false }
                                    )
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

                            IconButton(onClick = {actionStack.pop()}
                            ) { Icon(LineAwesomeIcons.UndoSolid, "Назад") }

//                            //Для Вызова Окна с Видео
//                            Row(
//                                modifier = Modifier
//                                    .background(
//                                        color = MaterialTheme.colors.secondaryVariant,
//                                        shape = RoundedCornerShape(50.dp)
//                                    )
//                            ) {
//                                var showVideoDialogBoolean by remember { mutableStateOf(false) }
//                                Button(
//                                    modifier = Modifier.padding(start = 16.dp),
//                                    onClick = { showVideoDialogBoolean = true },
//                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
//                                ) {
//                                    Text("Создать Видео")
//                                    if (showVideoDialogBoolean) {
//                                        Dialog(
//                                            onDismissRequest = { showVideoDialogBoolean = false },
//                                            properties = DialogProperties(dismissOnClickOutside = true)
//                                        ) {
//                                            workWithVideoDialog(fp.colorFuncID,photoList) { showVideoDialogBoolean = false }
//                                        }
//                                    }
//                                }
//                                Spacer(modifier = Modifier.width(5.dp))
//                                IconButton(
//                                    onClick = {
//                                        fp.plane?.let {
//                                            photoList.add(Cadre(it,fp.colorFuncID))
//                                        }
//                                    }
//                                ) {
//                                    Icon(Icons.Default.Add, "Добавить Кадр")
//                                }
//                            }
                        }
                        // Checkbox для динамических итераций
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = dynamicIterationsCheck,
                                onCheckedChange = { dynamicIterationsCheck = it
                                    fp.dynamicIterations = dynamicIterationsCheck },
                                modifier = Modifier.padding(start = 8.dp),
                            )
                            fp.refresh = true
                            //fp.retDynIt()
                            //всё!
                            Text(
                                text = "D. итерации",
                                style = MaterialTheme.typography.body1.copy(
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            )
                        }

                    },
                    modifier = Modifier.height(65.dp)
                )
            },
            modifier = Modifier.fillMaxSize()){
            Box(
                Modifier.fillMaxSize()
            ){
                mainFractalWindow(fp,actionStack)
            }
        }
    }

}



//fun JuliaWindow(){
//    var juliaDialogVisible by remember { mutableListOf(false) }
//    if (juliaDialogVisible) {
//        Window(onCloseRequest = { juliaDialogVisible = false } ) {
//            App()
//        }
//
//
//        Dialog(
//            onDismissRequest = ,
//            properties = DialogProperties(dismissOnClickOutside = true)
//        ){
//            val plane = Plane(-2.0, 2.0, 2.0, 2.0, 0f, 0f)
//            val yCart = Converter.yScr2Crt(pt.y,plane)
//            val xCart = Converter.xScr2Crt(pt.x,plane)
//            JuliaSet.selectedPoint = Complex(xCart,yCart)
//            val fp = remember { FractalPainter(JuliaSet){
//                if (it == 1f) Color.Black
//                else {
//                    val r = sin(it*15f).absoluteValue
//                    val g = (sin(-8f*it) * cos(it*5f+12f)).absoluteValue
//                    val b = log2(2f - cos(sin(18*-it)))
//                    Color(r, g, b)
//                }
//            }
//            }
//            fp.plane = plane
//            drawingPanel(fp){ size ->
//                fp.width = size.width.toInt()
//                fp.height = size.height.toInt()
//                fp.refresh = true
//            }
//        }
//    }
//}