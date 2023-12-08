package math

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.pow

class CatmullRomInterpolation
private constructor(private val controlPoints: PersistentList<Complex>) {
    constructor(vararg pathPoints: Complex) : this(pathPoints.asList())

    constructor(pathPoints: List<Complex>) : this(
        buildList {
            val n = pathPoints.size

            add(pathPoints[0] - (pathPoints[1] - pathPoints[0]))
            addAll(pathPoints)
            add(pathPoints[n - 1] - (pathPoints[n - 2] - pathPoints[n - 1]))
        }.toPersistentList()
    )

    val size get() = this.controlPoints.size - 2

    val lastIndex get() = size - 1

    val indices get() = 0..lastIndex

    val last get() = this[lastIndex]

    // Points that define the path and are saved to the file
    operator fun get(i: Int) = controlPoints[i + 1]

    fun points() = controlPoints.subList(1, controlPoints.size - 1)

    fun interp(offset: Double): Complex = when {
        offset <= 0 -> controlPoints[1]
        offset >= size - 1 -> controlPoints[controlPoints.size - 2]
        else -> {
            val i = offset.toInt()
            val t = offset - i
            val cpi = i + 1

            Complex(
                re = interpCoord(cpi, t, Complex::re),
                im = interpCoord(cpi, t, Complex::im)
            )
        }
    }

    private fun interpCoord(cpi: Int, t: Double, coord: Complex.() -> Double): Double {
        val p1 = controlPoints[cpi - 1].coord()
        val p2 = controlPoints[cpi].coord()
        val p3 = controlPoints[cpi + 1].coord()
        val p4 = controlPoints[cpi + 2].coord()

        return (
                (p2 * 2) +
                        (-p1 + p3) * t +
                        (p1 * 2 - p2 * 5 + p3 * 4 - p4) * t.pow(2) +
                        (-p1 + p2 * 3 - p3 * 3 + p4) * t.pow(3)
                ) / 2.0
    }

    fun withControlPoint(i: Int, cp: Complex): CatmullRomInterpolation =
        CatmullRomInterpolation(controlPoints.set(i + 1, cp).smoothEnds())

    fun withoutControlPoint(i: Int): CatmullRomInterpolation =
        CatmullRomInterpolation(controlPoints.removeAt(i + 1).smoothEnds())

    fun insertControlPoint(i: Int, cp: Complex): CatmullRomInterpolation =
        CatmullRomInterpolation(controlPoints.add(i + 1, cp).smoothEnds())

    fun splitSegment(i: Int): CatmullRomInterpolation =
        insertControlPoint(i + 1, interp(i + 0.5))

    fun reversed(): CatmullRomInterpolation =
        CatmullRomInterpolation(points().reversed())

    private fun PersistentList<Complex>.smoothEnds(): PersistentList<Complex> =
        mutate {
            val n = it.lastIndex
            it[0] = it[1] - (it[2] - it[1])
            it[n] = it[n - 1] - (it[n - 2] - it[n - 1])
        }

    override fun hashCode(): Int =
        controlPoints.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatmullRomInterpolation

        if (controlPoints != other.controlPoints) return false

        return true
    }

    override fun toString(): String =
        controlPoints.joinToString(
            prefix = "[",
            separator = ", ",
            postfix = "]",
            transform = { "(${it.re}, ${it.im})" }
        )
}