package methods.rectangle

import Answer
import Functions
import methods.NewtonKotes

class Rectangle(private var a: Double, private var b: Double): NewtonKotes(a, b) {

    override fun solve(f: Functions, estimate: Double) {
        val massMethodRectangle = arrayOf(RectangleLeft(a, b), RectangleMiddle(a, b), RectangleRight(a, b))
        for (methodRectangle in massMethodRectangle) {
            val answer: Answer = methodRectangle.calculate(f, estimate)
            answer.printAnswer(methodRectangle.getName())
        }
    }
}