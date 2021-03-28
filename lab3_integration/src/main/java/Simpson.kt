import kotlin.math.abs

class Simpson(private var a: Double, private var b: Double) {

    fun solve(f: Functions, estimate: Double): Answer {
        var n = 4
        var inverse = false
        if (a > b) {
            val t = a
            a = b
            b = t
            inverse = true
        }
        var i0: Double = calculateIntegral(n, f)
        var i1: Double
        var q: Double
        do {
            n *= 2
            i1 = calculateIntegral(n, f)
            q = abs(i0 - i1)
            i0 = i1
        } while (q > estimate)
        if (inverse) {
            i1 *= -1
        }
        return Answer(i1, n, estimate, q)
    }

    private fun calculateIntegral(n: Int, f: Functions): Double {
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
