package methods

import Interval
import Point
import java.util.function.BinaryOperator
import kotlin.math.abs
import kotlin.math.pow

open class Method(open var interval: Interval, private val y: Double,
                  open val function: BinaryOperator<Double>, private val estimate: Double) {
    var name: String = "Метод"
    var p: Int = 1

    open fun solve(): ArrayList<Point> {
        val list = ArrayList<Point>()
        list.add(Point(interval.a, y))
        return list
    }

    open fun setIntervals(interval: Double) {
        this.interval.h = interval
    }

    fun checkEstimate(listH: ArrayList<Point>, list2H: ArrayList<Point>, p: Int): Boolean {
        for (i in 0 until listH.size - 1) {
            if (abs(listH[i].y - list2H[2 * i].y) / (2.0.pow(p) - 1) > estimate) {
                return false
            }
        }
        return true
    }

    fun calculateR(list: ArrayList<Point>, list2: ArrayList<Point>, i: Int): Double {
        return if (2 * i < list2.size)
            abs(list[i].y - list2[2 * i].y) / (2.0.pow(p) - 1)
        else -1.0
    }

    open fun printTable(list: ArrayList<Point>, list2: ArrayList<Point>) {
        print(name)
        println("-----------------------------------------------------------------")
    }
}