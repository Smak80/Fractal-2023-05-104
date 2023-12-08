import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import gui.*
import drawing.FractalPainter
import drawing.convertation.Plane
import gui.controls.dropdownMenuIcon
import gui.video.workWithVideoDialog
import math.fractals.FractalData
import math.fractals.Mandelbrot
import tools.FileManager
import video.Cadre
import javax.swing.UIManager
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.sin

@Composable
@Preview
fun App(){
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val colorScheme : (Float)-> Color = {
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
            val b = log2(2f - cos(sin(18*-it)))
            Color(r, g, b)
        }
    }

    val fp = remember { FractalPainter(Mandelbrot,colorScheme)}
    val photoList = remember { SnapshotStateList<Cadre>() }
    fp.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)
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
                                    SaveOpenMenuItems(
                                        {
                                            isMenuExpanded = false
                                            TODO("Реализовать функцию для сохранения изображения")
                                        }, {
                                            fp.plane?.let{
                                                val fractalData = FractalData(it.xMin,it.xMax,it.yMin,it.yMax, 1)
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
                            IconButton(
                                onClick = {TODO("Отмена действий!")}
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
                                        Dialog(
                                            onDismissRequest = { showVideoDialogBoolean = false },
                                            properties = DialogProperties(dismissOnClickOutside = true)
                                        ) {
                                            workWithVideoDialog(fp.colorFunc,photoList) { showVideoDialogBoolean = false }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                IconButton(
                                    onClick = {
                                        fp.plane?.let {
                                            photoList.add(Cadre(it,fp.colorFunc))
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Add, "Добавить Кадр")
                                }
                            }
                            //Выбор Цветовой Схемы
                            dropdownMenuIcon(
                                mapOf(),
                            )
                            // Checkbox для динамических итераций
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = dynamicIterationsCheck,
                                    onCheckedChange = {dynamicIterationsCheck = it},
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
            },
            modifier = Modifier.fillMaxSize()){
            Box(
                Modifier.fillMaxSize()
            ){
                mainFractalWindow(fp,photoList)
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
