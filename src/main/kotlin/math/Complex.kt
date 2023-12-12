package math

import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.sqrt

class Complex(var re: Double = 0.0, var im: Double = 0.0) {

    fun abs() = sqrt(re * re + im * im)
    fun abs2() = re * re + im * im
    fun arg() = atan2(im, re)

    operator fun plus(other: Complex) = Complex(re + other.re, im + other.im)
    operator fun plusAssign(other: Complex){
        re += other.re
        im += other.im
    }

    operator fun minus(other: Complex) = Complex(re - other.re, im - other.im)
    operator fun minusAssign(other: Complex){
        re -= other.re
        im -= other.im
    }

    operator fun times(other: Complex) = Complex(re * other.re - im * other.im, re * other.im + im * other.re)
    operator fun times(other: Double) = Complex(re * other - im * other, re * other + im * other)
    operator fun div(other: Double) = this * (1/other)
    operator fun timesAssign(other: Complex){
        val r = re * other.re - im * other.im
        im = re * other.im + im * other.re
        re = r
    }

    operator fun div(other: Complex) = Complex((re * other.re + im * other.im) / other.abs2(), (im * other.re - re * other.im) / other.abs2())
    operator fun divAssign(other: Complex){
        val r = (re * other.re + im * other.im) / other.abs2()
        im = (im * other.re - re * other.im) / other.abs2()
        re = r
    }

    override fun toString() = buildString {
        if ((re != 0.0) || (im == 0.0)) append(re)
        if(im != 0.0) {
            append(if(im < 0.0) " - " else if(re != 0.0) " + " else "")
            if(im.absoluteValue != 1.0) append(im)
            append("i")
        }

    }

    fun sin(): Complex {
        return Complex(kotlin.math.sin(re) * kotlin.math.cosh(im), kotlin.math.cos(re) * kotlin.math.sinh(im))
    }

    fun cos(): Complex {
        return Complex(kotlin.math.cos(re) * kotlin.math.cosh(im), -kotlin.math.sin(re) * kotlin.math.sinh(im))
    }

}