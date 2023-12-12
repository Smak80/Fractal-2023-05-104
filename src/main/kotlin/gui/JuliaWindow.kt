import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import drawing.FractalPainter
import drawing.convertation.ColorType
import drawing.convertation.Plane
import drawing.convertation.colorFunc
import gui.fractalWindow
import math.fractals.JuliaSet
import math.fractals.Mandelbrot

@Composable
fun JuliaApp(mandelbrotFp:FractalPainter) {
    val plane = Plane(-2.0, 2.0, -2.0, 2.0, 0f, 0f)
    val fp = remember { FractalPainter(JuliaSet)}
    fp.colorFuncID = mandelbrotFp.colorFuncID
    fp.plane = plane
    fp.xMin = plane.xMin
    fp.xMax = plane.xMax
    fp.yMax = plane.yMax
    fp.yMin = plane.yMin
//    fp.plane?.let {
//        fp.xMin = it.xMin
//        fp.xMax = it.xMax
//        fp.yMax = it.yMax
//        fp.yMin = it.yMin
//    }

    MaterialTheme {
        fractalWindow(fp)
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