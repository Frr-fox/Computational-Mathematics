package methods

import Interval
import Point
import java.util.function.BinaryOperator

class Euler(override var interval: Interval, private val y: Double,
            override val function: BinaryOperator<Double>, private val estimate: Double): Method(interval, y, function, estimate) {
    init {
        super.name = "Метод Эйлера"
    }

    override fun solve(): ArrayList<Point> {
        val list = ArrayList<Point>()
        var x = interval.a
        list.add(Point(x, y))
        while (x < interval.b) {
            val y = list[list.size - 1].y + interval.h * function.apply(list[list.size - 1].x, list[list.size - 1].y)
            x += interval.h
            list.add(Point(x, y))
        }
        return list
    }

    override fun printTable(list: ArrayList<Point>, list2: ArrayList<Point>) {
        println()
        println(name)
        println("------------------------------------------------------------------------------")
        println("|  i  |     xi     |     yi     |   f(xi, yi)   |  h f(xi, yi)  |      R     |")
        println("------------------------------------------------------------------------------")
        for ((i, p) in list.withIndex()) {
            println(String.format("|%4d |%11.4f |%11.4f |%14.4f |%14.4f |%11s |", i, p.x, p.y, function.apply(p.x, p.y),
                    function.apply(p.x, p.y) * interval.h, if (calculateR(list, list2, i) != -1.0)
                String.format("%11.5f", calculateR(list, list2, i)) else "     -     "))
        }
        println("------------------------------------------------------------------------------")
        println("\nШаг дифференцирования ${interval.h}")
    }
}