package GUI

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import drawing.FractalPainter
import drawing.convertation.Plane
import math.Complex
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun Julia(selectedPoint: Offset, fp: FractalPainter){
    val plane = Plane(-2.0, 2.0, -2.0, 2.0, 0f, 0f)
    val jpcomplex = math.fractals.Julia(Complex(selectedPoint.x.toDouble(), selectedPoint.y.toDouble()),  fp.fractal.function)

    val color = remember { mutableStateOf("color1") }
    val function = remember { mutableStateOf("Mandelbrot") }
    val jp = remember { mutableStateOf( FractalPainter(jpcomplex){
        if (it == 1f) Color.Black
        else {
            val r = sin(it*15f).absoluteValue
            val g = (sin(-8f*it)* cos(it*5f+12f)).absoluteValue
            val b = sin(2f - cos(sin(18*-it))).absoluteValue
            Color(r, g, b)
        }
    })
    }
    jp.value.plane = plane
    MaterialTheme {
        DrawingPanel(jp, color, function){size ->
            jp.value.width = size.width.toInt()
            jp.value.height = size.height.toInt()
            jp.value.refresh = true
        }
    }
}