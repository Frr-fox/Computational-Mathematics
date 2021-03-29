package methods

import Functions

class Simpson(private var a: Double, private var b: Double): NewtonKotes(a, b) {
    init {
        super.setName("Симпсона")
    }

    override fun calculateIntegral(n: Int, f: Functions): Double {
        val h = (b - a) / n
        var sum: Double = f.function(a) + f.function(b)
        var x: Double = a + h
        var i = 1
        while (x < b) {
            val y: Double = f.function(x)
            sum += if (i % 2 == 0)  4 * y else 2 * y
            x += h
            i++
        }
        sum *= h / 3
        return sum
    }
}
