package methods

import Interval
import Point
import java.util.function.BinaryOperator
import kotlin.math.pow

class Adams(override var interval: Interval, private val y: Double,
            override val function: BinaryOperator<Double>, private val estimate: Double): Method(interval, y, function, estimate) {
    init {
        super.name = "Метод Адамса"
        super.p = 4
    }
    val rungeKutta = RungeKutta(interval, y, function)

    override fun solve(): ArrayList<Point> {
        val list = rungeKutta.solve(4)
        var x = interval.a + 4 * interval.h
        while (x <= interval.b) {
            val df = calculateDf(list, list.size)
            val df2 = calculateDf2(list, list.size)
            val df3 = calculateDf3(list, list.size)
            val y = list[list.size - 1].y + interval.h * function.apply(list[list.size - 1].x, list[list.size - 1].y) +
                    interval.h.pow(2) * df / 2 + 5 * interval.h.pow(3) * df2/ 12 + 3 * interval.h.pow(4) * df3 / 8
            list.add(Point(x, y))
            x += interval.h
        }
        return list
    }

    override fun setIntervals(interval: Double) {
        this.interval.h = interval
        this.rungeKutta.interval.h = interval
    }

    override fun printTable(list: ArrayList<Point>, list2: ArrayList<Point>) {
        println()
        println(rungeKutta.name)
        var i = 0
        println("--------------------------------------------------------------------------------------------------------------")
        println("|  i  |     xi     |     yi     |       k1      |       k2      |       k3      |       k4      |      R     |")
        println("--------------------------------------------------------------------------------------------------------------")
        while (i < 4) {
            val k1 = rungeKutta.calculateK1(list, i + 1)
            val k2 = rungeKutta.calculateK2(list, i + 1, k1)
            val k3 = rungeKutta.calculateK3(list, i + 1, k2)
            val k4 = rungeKutta.calculateK4(list, i + 1, k3)
            println(String.format("|%4d |%11.4f |%11.4f |%14.4f |%14.4f |%14.4f |%14.4f |%11.5f |", i, list[i].x, list[i].y,
                    k1, k2, k3, k4, calculateR(list, list2, i)))
            i++
        }
        println("--------------------------------------------------------------------------------------------------------------")
        println()
        println(name)
        println("--------------------------------------------------------------------------------------------------------------")
        println("|  i  |     xi     |     yi     |   f(xi, yi)   |       Δfi     |      Δ²fi     |      Δ³fi     |      R     |")
        println("--------------------------------------------------------------------------------------------------------------")
        while (i < list.size) {
            println(String.format("|%4d |%11.4f |%11.4f |%14.4f |%14.4f |%14.4f |%14.4f |%11s |", i, list[i].x, list[i].y,
                    function.apply(list[i].x, list[i].y), calculateDf(list, i + 1), calculateDf2(list, i + 1),
                    calculateDf3(list, i + 1), if (calculateR(list, list2, i) != -1.0)
                String.format("%11.5f", calculateR(list, list2, i)) else "     -     "))
            i++
        }
        println("--------------------------------------------------------------------------------------------------------------")
        println("\nШаг дифференцирования ${interval.h}")
    }

    private fun calculateDf(list: ArrayList<Point>, size: Int): Double {
        return function.apply(list[size - 1].x, list[size - 1].y) - function.apply(list[size - 2].x,
                list[size - 2].y)
    }

    private fun calculateDf2(list: ArrayList<Point>, size: Int): Double {
        return function.apply(list[size - 1].x, list[size - 1].y) - 2 * function.apply(list[size - 2].x,
                list[size - 2].y) + function.apply(list[size - 3].x, list[size - 3].y)
    }

    private fun calculateDf3(list: ArrayList<Point>, size: Int): Double {
        return function.apply(list[size - 1].x, list[size - 1].y) - 3 * function.apply(list[size - 2].x,
                list[size - 2].y) + 3 * function.apply(list[size - 3].x, list[size - 3].y) -
                function.apply(list[size - 4].x, list[size - 4].y)
    }
}