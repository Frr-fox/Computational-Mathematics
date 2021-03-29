package methods.rectangle

import Functions
import methods.NewtonKotes

class RectangleLeft(private var a: Double, private var b: Double): NewtonKotes(a, b) {
    init {
        super.setName("прямоугольников (левых)")
    }

    override fun calculateIntegral(n: Int, f: Functions): Double {
        val h = (b - a) / n
        var sum = 0.0
        var x: Double = a
        while (x < b) {
            val y: Double = f.function(x)
            sum += y * h
            x += h
        }
        return sum
    }
}