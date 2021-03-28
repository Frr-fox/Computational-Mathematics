import kotlin.math.pow

class Functions(number: Int) {
    var function = { x: Double -> x}
    init {
        function = when (number) {
            1 -> {
                {x: Double -> 2 * x.pow(3.0) - 5 * x.pow(2.0) - 3 * x + 21}
            }
            2 -> {
                {x: Double -> - x.pow(3.0) - x.pow(2.0) - 2 * x + 20}
            }
            else -> {
                {x: Double -> - 3 * x.pow(3.0) - 5 * x.pow(2.0) + 4 * x - 21}
            }
        }
    }
}

fun printFunctions() {
    print("Выберите функцию для интегрирования:\n")
    var i = 1
    print("$i) 2x³ - 5x² - 3x + 21\n")
    i++
    print("$i) -x³ - x² - 2x + 20\n")
    i++
    print("$i) - 3x³ - 5x² + 4x - 21\n")
    print("Ваш выбор: ")
}