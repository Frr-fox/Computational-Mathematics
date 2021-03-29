class Answer(var result: Double, var n: Int, var estimate: Double,  var accuracy: Double) {

    fun printAnswer(name: String) {
        print("\nРезультат выполнения метода $name\nВычисленное значение интеграла: ${result.format(defineRangOfAccuracy(estimate)).replace(",", ".")}\n" +
                "Число разбиений интервала: $n\n" +
                "Заданная точность: $estimate\n" +
                "Погрешность: ${accuracy.format(defineRangOfAccuracy(estimate) + 1).replace(",", ".")}\n"
        )
    }
}

private fun defineRangOfAccuracy(estimate: Double): Int {
    var i: Int = 0
    var e = estimate
    while (e < 1) {
        i++
        e *= 10
    }
    return i
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)