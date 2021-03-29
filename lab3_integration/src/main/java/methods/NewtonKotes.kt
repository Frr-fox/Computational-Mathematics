package methods

import Answer
import Functions
import kotlin.math.abs

open class NewtonKotes(private var a: Double, private var b: Double) {
    private var name: String = "Ньютона-Котеса"

    open fun solve(f: Functions, estimate: Double) {
        val answer: Answer = calculate(f, estimate)
        answer.printAnswer(name)
    }

    fun calculate(f: Functions, estimate: Double): Answer {
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

    open fun calculateIntegral(n: Int, f: Functions): Double {
        return 0.0
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return this.name
    }
}