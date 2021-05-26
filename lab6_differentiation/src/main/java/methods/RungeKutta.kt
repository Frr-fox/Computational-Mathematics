package methods

import Interval
import Point
import java.util.function.BinaryOperator

class RungeKutta(var interval: Interval, private val y: Double, private val function: BinaryOperator<Double>) {
    val name: String = "Метод Рунге-Кутты"

    fun solve(n: Int): ArrayList<Point> {
        val list = ArrayList<Point>()
        var x = interval.a
        var n = n - 1
        list.add(Point(x, y))
        while (n > 0 && x <= interval.b) {
            val k1 = calculateK1(list, list.size)
            val k2 = calculateK2(list, list.size, k1)
            val k3 = calculateK3(list, list.size, k2)
            val k4 = calculateK4(list, list.size, k3)
            val y = list[list.size - 1].y + (k1 + 2 * k2 + 2 * k3 + k4) / 6
            x += interval.h
            list.add(Point(x, y))
            n--
        }
        return list
    }

    fun calculateK1(list: ArrayList<Point>, size: Int):Double {
        return interval.h * function.apply(list[size - 1].x, list[size - 1].y)
    }

    fun calculateK2(list: ArrayList<Point>, size: Int, k1: Double):Double {
        return interval.h * function.apply(list[size - 1].x + interval.h / 2, list[size - 1].y + k1/2)
    }

    fun calculateK3(list: ArrayList<Point>, size: Int, k2: Double):Double {
        return interval.h * function.apply(list[size - 1].x + interval.h / 2, list[size - 1].y + k2/2)
    }

    fun calculateK4(list: ArrayList<Point>, size: Int, k3: Double):Double {
        return interval.h * function.apply(list[size - 1].x + interval.h, list[size - 1].y + k3)
    }
}