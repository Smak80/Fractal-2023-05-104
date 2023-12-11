import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.GalacticRepublic
import compose.icons.lineawesomeicons.PaletteSolid
import compose.icons.lineawesomeicons.Save
import compose.icons.lineawesomeicons.UndoSolid
import drawing.FractalPainter
import drawing.convertation.ColorType
import drawing.convertation.Plane
import gui.controls.dropdownMenuIcon
import gui.mainFractalWindow
import gui.saveOpenMenuItems
import gui.video.workWithVideoDialog
import math.fractals.FractalData
import math.fractals.Mandelbrot
import tools.FileManager
import tools.FileManager.saveImageData
import video.Cadre
import java.awt.image.BufferedImage
import javax.swing.UIManager

@Composable
@Preview
fun App(){
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
//    var colorSchemeIndex by remember { mutableStateOf(2) }
//    var fractalSchemeIndex by remember { mutableStateOf(1) }
    val photoList = remember { SnapshotStateList<Cadre>() }
    val fp = remember {FractalPainter(Mandelbrot)}
    fp.colorFuncID = ColorType.First
    Mandelbrot.funcNum = 1
    fp.plane = Plane(-2.0,1.0,-1.0,1.0, 0f, 0f)
//    fp.plane?.let {
//        fp.xMin = it.xMin
//        fp.xMax = it.xMax
//        fp.yMax = it.yMax
//        fp.yMin = it.yMin
//    }

    MaterialTheme{
        Scaffold(
            topBar = {
                var dynamicIterationsCheck by remember { mutableStateOf(false) }
                var isMenuExpanded by remember { mutableStateOf(false) }

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
                                                val fractalData = FractalData(it.xMin,it.xMax,it.yMin,it.yMax, fp.colorFuncID.value)
                                                saveImageData(fractalData)
                                            }
                                            isMenuExpanded = false
                                        }, {
                                            fp.plane?.let{
                                                val fractalData = FractalData(it.xMin,it.xMax,it.yMin,it.yMax, fp.colorFuncID.value)
                                                FileManager.saveFractalData(fractalData)
                                            }
                                        }, {
                                            val resData = FileManager.loadFractalData()
                                            resData?.let { fd ->
                                                fp.plane?.let { plane ->
                                                    fp.plane = Plane(fd.xMin, fd.xMax, fd.yMin, fd.yMax, plane.width, plane.height)
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

                            IconButton(onClick = {fp.actionStack.pop()}
                            ) { Icon(LineAwesomeIcons.UndoSolid, "Назад") }
//                            IconButton(onClick = {
//                                fp.plane = when(Mandelbrot.funcNum){
//                                    2-> Plane(-1.0,2.0,-1.0,1.0, 0f, 0f)
//                                    else-> Plane(-2.0,1.0,-1.0,1.0, 0f, 0f)
//                                }
//                            }
//                            ) { Icon(FontAwesomeIcons.Solid.SyncAlt, "Обновить") }
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
                                        Dialog(
                                            onDismissRequest = { showVideoDialogBoolean = false },
                                            properties = DialogProperties(dismissOnClickOutside = true)
                                        ) {
                                            workWithVideoDialog(fp.colorFuncID,photoList) { showVideoDialogBoolean = false }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                IconButton(
                                    onClick = {
                                        fp.plane?.let {
                                            photoList.add(Cadre(it,fp.colorFuncID))
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Add, "Добавить Кадр")
                                }
                            }
                            //Выбор Цветовой Схемы!
                            dropdownMenuIcon(
                                mapOf(
                                    "Логарифм Папа" to {fp.apply {
                                        fp.actionStack.push(fp.colorFuncID)
                                        colorFuncID= ColorType.First
                                        refresh = true
                                    }},
                                    "Футболка Денчика" to{fp.apply {
                                        fp.actionStack.push(fp.colorFuncID)
                                        colorFuncID= ColorType.Second
                                        refresh = true
                                    }},
                                    "Болото Шрека" to {fp.apply {
                                        fp.actionStack.push(fp.colorFuncID)
                                        colorFuncID= ColorType.Third
                                        refresh = true
                                    }},
                                ),
                                LineAwesomeIcons.PaletteSolid
                            )
                            dropdownMenuIcon(
                                mapOf(
                                    "Оригинал" to {fp.apply {
                                        Mandelbrot.funcNum = 1
                                        plane = Plane(-2.0,1.0,-1.0,1.0, 0f, 0f)
                                        refresh = true
                                    }},
                                    "Перевертыш" to {fp.apply {
                                        Mandelbrot.funcNum = 2
                                        plane = Plane(-1.0,2.0,-1.0,1.0, 0f, 0f)
                                        refresh = true
                                    }},
                                    "Кубический" to {fp.apply {
                                        plane = Plane(-2.0,1.0,-1.0,1.0, 0f, 0f)
                                        Mandelbrot.funcNum = 3
                                        refresh = true
                                    }},
                                    "Дурацкий Кружок" to {fp.apply {
                                        Mandelbrot.funcNum = 4
                                        refresh = true
                                    }},
                                ),
                                LineAwesomeIcons.GalacticRepublic
                            )
                            // Checkbox для динамических итераций
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = dynamicIterationsCheck,
                                    onCheckedChange = { dynamicIterationsCheck = it
                                                      fp.dinamycIters = dynamicIterationsCheck },
                                    modifier = Modifier.padding(start = 8.dp),
                                )
                                if(dynamicIterationsCheck) fp.retDynIt()
                                //всё!
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

