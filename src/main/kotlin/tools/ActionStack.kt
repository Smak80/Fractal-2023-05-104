package tools

import drawing.FractalPainter
import drawing.convertation.ColorType
import drawing.convertation.Plane
import drawing.convertation.colorFunc
import math.fractals.Mandelbrot
import java.util.*

class ActionStack(private val fp: FractalPainter){
    private val stack = Stack<Any>()
    fun push(obj: Any) = stack.push(obj)
    fun pop() {
        if (stack.isEmpty()) return
        when (val obj: Any = stack.pop()) {
            is CartCords -> {
                fp.plane?.let{
                    it.xEdges = Plane.Edges(obj.xMin,obj.xMax)
                    it.yEdges = Plane.Edges(obj.yMin,obj.yMax)
                    fp.refresh = true
                }
            }
            is ColorType -> {
                fp.colorFuncID = obj
                fp.refresh = true
            }
            else -> throw Exception("В стек попал объект неизвестного типа")
        }
    }
    data class CartCords(
        val xMin:Double,
        val xMax:Double,
        val yMin:Double,
        val yMax:Double,
    )
}

