package methods

import Functions

class Trapezium(private var a: Double, private var b: Double): NewtonKotes(a, b) {
    init {
        super.setName("трапеций")
    }

    override fun calculateIntegral(n: Int, f: Functions): Double {
        val h = (b - a) / n
        var sum: Double = f.function(a) + f.function(b)
        var x: Double = a + h
        while (x < b) {
            val y: Double = f.function(x)
            sum += 2 * y
            x += h
        }
        sum *= h / 2
        return sum
    }
}