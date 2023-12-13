package tools

import drawing.FractalPainter
import drawing.convertation.ColorFuncs
import math.fractals.FractalData
import math.fractals.Mandelbrot
import java.util.*

class ActionStack(private val fp: FractalPainter){
    private val stack = Stack<Any>()
    fun push(obj: Any) = stack.push(obj)
    fun pop() {
        if (stack.isEmpty()) return
        when (val obj: Any = stack.pop()) {
            is CartCords -> rollBackPlane(obj)
            is ColorFuncs -> {
                fp.colorFuncID = obj
                fp.refresh = true
            }
            is FractalData -> {
                fp.colorFuncID = obj.colorscheme
                Mandelbrot.funcNum = obj.fractalFunk
                rollBackPlane(obj)
            }
            else -> throw Exception("В стек попал объект неизвестного типа")
        }

    }
    private fun rollBackPlane(obj:CartCords){
        fp.plane?.let{
            it.xMin = obj.xMin
            it.xMax = obj.xMax
            it.yMin = obj.yMin
            it.yMax = obj.yMax
        }
        fp.xMin = obj.xMin
        fp.xMax = obj.xMax
        fp.yMin = obj.yMin
        fp.yMax = obj.yMax
        fp.refresh = true
    }
    private fun rollBackPlane(obj:FractalData) =
        rollBackPlane(CartCords(obj.xMin,obj.xMax,obj.yMin,obj.yMax))
    fun copy(): ActionStack {
        val a = ActionStack(fp)
        a.stack.addAll(stack)
        return a
    }
    data class CartCords(
        val xMin:Double,
        val xMax:Double,
        val yMin:Double,
        val yMax:Double,
    )

}

