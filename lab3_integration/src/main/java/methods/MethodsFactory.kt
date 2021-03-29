package methods

import methods.rectangle.Rectangle

class MethodsFactory() {

    fun createMethod(n: Int, a: Double, b: Double) : NewtonKotes {
        return when (n) {
            1 -> Rectangle(a, b)
            2 -> Trapezium(a, b)
            3 -> Simpson(a, b)
            else -> NewtonKotes(a, b)
        }
    }
}