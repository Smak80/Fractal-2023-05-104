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
import compose.icons.lineawesomeicons.*
import drawing.FractalPainter
import drawing.convertation.ColorFuncs
import drawing.convertation.Plane
import gui.controls.dropdownMenuIcon
import gui.mainFractalWindow
import gui.saveOpenMenuItems
import gui.video.workWithVideoDialog
import math.fractals.FractalData
import math.fractals.FractalFunks
import math.fractals.Mandelbrot
import tools.ActionStack
import tools.FileManager
import tools.FileManager.saveImageData
import video.Cadre

@Composable
@Preview
fun App(){
    var dynamicIterationsCheck by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var reBoot by remember { mutableStateOf(false) }
    val photoList = remember { SnapshotStateList<Cadre>() }
    val fp = remember {FractalPainter(Mandelbrot)}
    fp.colorFuncID = ColorFuncs.First
    Mandelbrot.funcNum = FractalFunks.Classic
    fp.plane = Plane(-2.0,1.0,-1.0,1.0, 0f, 0f)
    val actionStack = ActionStack(fp)

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
            reBoot = true
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
                                                saveImageData(fp)
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
//                            IconButton(onClick = {
//
//                                val bi = Cadre.getImageFromPlane(fp.plane!!,1920f,1080f,fp.colorFuncID)
//                                try {
//                                    val file = File("C:/Users/Lev Grekov/OneDrive/Изображения/test.png")
//                                    ImageIO.write(bi, "png", file) // Вы можете выбрать другой формат, если это необходимо
//                                    println("Изображение успешно сохранено")
//                                } catch (e: IOException) {
//                                    println("Ошибка при сохранении изображения: ${e.message}")
//                                }
//                            }
//                            ) { Icon(LineAwesomeIcons.PhotoVideoSolid, "Назад") }
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
                                        actionStack.push(fp.colorFuncID)
                                        colorFuncID= ColorFuncs.First
                                        refresh = true
                                    }},
                                    "Tame Impala" to{fp.apply {
                                        actionStack.push(fp.colorFuncID)
                                        colorFuncID= ColorFuncs.Second
                                        refresh = true
                                    }},
                                    "Болото Шрека" to {fp.apply {
                                        actionStack.push(fp.colorFuncID)
                                        colorFuncID= ColorFuncs.Third
                                        refresh = true
                                    }},
                                ),
                                LineAwesomeIcons.PaletteSolid
                            )
                            dropdownMenuIcon(
                                mapOf(
                                    "Оригинал" to {fp.apply {
                                        preSaveFractalData()
                                        Mandelbrot.funcNum = FractalFunks.Classic
                                        initPlane(Plane(-2.0,1.0,-1.0,1.0, 0f, 0f))
                                        refresh = true
                                    }},
                                    "Кубический" to {fp.apply {
                                        preSaveFractalData()
                                        Mandelbrot.funcNum = FractalFunks.Cube
                                        initPlane(Plane(-1.0,1.0,-1.5,1.5, 0f, 0f))
                                        refresh = true
                                    }},
                                    "Четверка" to {fp.apply {
                                        preSaveFractalData()
                                        initPlane(Plane(-2.0,1.0,-1.1,1.1, 0f, 0f))
                                        Mandelbrot.funcNum = FractalFunks.FourthP
                                        refresh = true
                                    }},
//                                    "Дурацкий Кружок" to {fp.apply {
//                                        Mandelbrot.funcNum = 4
//                                        refresh = true
//                                    }},
                                    "Жора" to {fp.apply {
                                        preSaveFractalData()
                                        initPlane(Plane(-2.0,1.0,-1.0,1.0, 0f, 0f))
                                        Mandelbrot.funcNum = FractalFunks.Jora
                                        refresh = true
                                    }},
                                    "Синус" to {fp.apply {
                                        preSaveFractalData()
                                        Mandelbrot.funcNum = FractalFunks.Sin
                                        initPlane(Plane(-2.0,1.0,-1.0,1.0, 0f, 0f))
                                        refresh = true
                                    }},
                                    "Птеродактель" to {fp.apply {
                                        preSaveFractalData()
                                        Mandelbrot.funcNum = FractalFunks.Dino
                                        initPlane(Plane(-2.0,1.0,-1.0,1.0, 0f, 0f))
                                        refresh = true
                                    }},
                                    "Толстяк" to {fp.apply {
                                        preSaveFractalData()
                                        Mandelbrot.funcNum = FractalFunks.Fat
                                        initPlane(Plane(-1.0,1.0,-1.0,1.0, 0f, 0f))
                                        refresh = true
                                    }},
                                    "Дурацкий Кружок" to {fp.apply {
                                        preSaveFractalData()
                                        Mandelbrot.funcNum = FractalFunks.Zero
                                        initPlane(Plane(-1.0,1.0,-1.0,1.0, 0f, 0f))
                                        refresh = true
                                    }},
                                ),
                                LineAwesomeIcons.DrawPolygonSolid
                            )
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

