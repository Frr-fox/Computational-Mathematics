import java.lang.NumberFormatException

fun main() {
    /* Выбор функции */
    var check = false
    var function = Functions(1)
    while (!check) {
        try {
            printFunctions()
            val line = readLine()
            var number = 0
            if (line != null) {
                number = line.trim().toInt()
            }
            if (number !in 1 .. 3) {
                throw IllegalArgumentException()
            }
            function = Functions(number)
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы. Введите число от 1 до 3\n")
        }
    }
    /* Ввод пределов интегрирования */
    check = false
    var a = 0.0
    var b = 0.0
    while (!check) {
        try {
            print("Введите пределы интегрирования: ")
            val line = readLine() ?: throw IllegalArgumentException()
            val boarders = line.trim().replace(Regex(" +"), " ").split(" ").toList()
            if (boarders.size != 2) throw IllegalArgumentException()
            a = boarders[0].toDouble()
            b = boarders[1].toDouble()
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы\n")
        } catch (e: NumberFormatException) {
            print("Введенные данные некорректы\n")
        }
    }
    val simpson = Simpson(a, b)
    /* Ввод точности вычислений */
    check = false
    var estimate = 0.01
    while (!check) {
        try {
            print("Введите значение точности вычислений (по умолчанию $estimate): ")
            val line = readLine().toString()
            if (line == "") break
            estimate = line.trim().toDouble()
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы. Введите число, меньшее 1\n")
        } catch (e: NumberFormatException) {
            print("Введенные данные некорректы\n")
        }
    }
    val answer: Answer = simpson.solve(function, estimate)
    answer.printAnswer()
}