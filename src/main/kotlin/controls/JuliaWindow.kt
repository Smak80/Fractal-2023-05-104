import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import controls.drawingPanel
import drawing.FractalPainter
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.Complex
import math.fractals.JuliaSet
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.sin

@Composable
fun JuliaApp(point: Offset) {
    val plane = Plane(-2.0, 2.0, -2.0, 2.0, 0f, 0f)
    val xCart = Converter.xScr2Crt(point.x,plane)
    val yCart = Converter.yScr2Crt(point.y,plane)
    JuliaSet.selectedPoint = Complex(xCart,yCart)

    val fp = remember { FractalPainter(JuliaSet){
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
            val b = log2(2f - cos(sin(18*-it)))
            Color(r, g, b)
        }
    }}
    fp.plane = plane
    MaterialTheme {
        drawingPanel(fp){size ->
            fp.width = size.width.toInt()
            fp.height = size.height.toInt()
            fp.refresh = true
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