import methods.Adams
import methods.Euler
import methods.Method
import java.lang.NumberFormatException
import java.util.function.BinaryOperator
import kotlin.math.*

fun main() {
    /* Выбор ОДУ */
    println("Программа для решения задачи Коши численными методами")
    var check = false
    var function: BinaryOperator<Double> = BinaryOperator {x: Double, y: Double -> 1 / x}
    var coeffitient: BinaryOperator<Double> = BinaryOperator {x: Double, y: Double -> 1 / x}
    var realFunction: BinaryOperator<Double> = BinaryOperator {x: Double, c: Double -> 1 / x}
    while (!check) {
        try {
            println("Выберите ОДУ из приведенного ниже списка:\n" +
                    "1) y' = y + (1 + x)y²\n" +
                    "2) y' = e^2x + y\n" +
                    "3) y' = x^2 - 2y")
            print("Ваш выбор: ")
            val line = readLine()
            var number = 0
            if (line != null) {
                number = line.trim().toInt()
            }
            if (number !in 1 .. 3) {
                throw IllegalArgumentException()
            }
            function = when (number) {
                1 -> BinaryOperator {x: Double, y: Double -> y + (1 + x) * y.pow(2.0)}
                2 -> BinaryOperator {x: Double, y: Double -> exp(2 * x) + y}
                else -> BinaryOperator {x: Double, y: Double -> x.pow(2) - 2 * y}
            }
            coeffitient = when (number) {
                1 -> BinaryOperator {x: Double, y: Double -> - exp(x) * (1 / y + x)}
                2 -> BinaryOperator {x: Double, y: Double -> 1/ exp(x) * (y - exp(2 * x))}
                else -> BinaryOperator {x: Double, y: Double -> exp(2 * x) * (y - x.pow(2) / 2 + x / 2 - 1 / 4)}
            }
            realFunction = when (number) {
                1 -> BinaryOperator {x: Double, c: Double -> - exp(x) / (c + exp(x) * x)}
                2 -> BinaryOperator {x: Double, c: Double -> c * exp(x) + exp(2 * x)}
                else -> BinaryOperator {x: Double, c: Double -> c * exp(- 2 * x) + x.pow(2) / 2 - x / 2 + 1/ 4}
            }
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы. Введите число от 1 до 3\n")
        }
    }
    /* Выбор метода */
    check = false
    var chosenMethod = 0
    while (!check) {
        try {
            println("Численные методы:\n1) Метод Эйлера\n2) Метод Адамса\n")
            print("Выберите метод дифференцирования: ")
            val line = readLine()
            if (line != null) {
                chosenMethod = line.trim().toInt()
            }
            if (chosenMethod !in 1 .. 2) {
                throw IllegalArgumentException()
            }
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы. Введите число от 1 до 2\n")
        }
    }
    /* Начальные условия y(x0)*/
    check = false
    var y = 0.0
    while (!check) {
        try {
            print("Введите начальное условие y(x0): ")
            val line = readLine() ?: throw IllegalArgumentException()
            y = line.trim().replace(",", ".").toDouble()
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы\n")
        } catch (e: NumberFormatException) {
            print("Введенные данные некорректы\n")
        }
    }
    /* Интервал дифференцирования */
    check = false
    var a = 0.0
    var b = 0.0
    while (!check) {
        try {
            print("Введите интервал дифференцирования: ")
            val line = readLine() ?: throw IllegalArgumentException()
            val boarders = line.trim().replace(Regex(" +"), " ").split(" ").toList()
            if (boarders.size != 2) throw IllegalArgumentException()
            a = boarders[0].replace(",", ".").toDouble()
            b = boarders[1].replace(",", ".").toDouble()
            if (a > b) {
                val t = a
                a = b
                b = t
            }
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы\n")
        } catch (e: NumberFormatException) {
            print("Введенные данные некорректы\n")
        }
    }
    /* Шаг дифференцирования */
    check = false
    var h = 0.1
    while (!check) {
        try {
            print("Введите шаг дифференцирования (по умолчанию $h): ")
            val line = readLine().toString()
            if (line == "") break
            h = line.trim().replace(",", ".").toDouble()
            if (h > abs(b-a) || h <= 0) throw IllegalArgumentException()
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы. Введите положительное число, меньшее ${abs(b-a)}\n")
        } catch (e: NumberFormatException) {
            print("Введенные данные некорректы\n")
        }
    }
    /* Точность */
    check = false
    var estimate = 0.1
    while (!check) {
        try {
            print("Введите значение точности вычислений (по умолчанию $estimate): ")
            val line = readLine().toString()
            if (line == "") break
            estimate = line.trim().replace(",", ".").toDouble()
            if (estimate > 1 || estimate <= 0) throw IllegalArgumentException()
            check = true
        } catch (e: IllegalArgumentException) {
            print("Введенные данные некорректы. Введите положительное число, меньшее 1\n")
        } catch (e: NumberFormatException) {
            print("Введенные данные некорректы\n")
        }
    }
    if (chosenMethod == 2 && (abs(b - a) / h).toInt() <= 4) {
        println("Для решения через метод Адамса требуется как минимум 5 точек на интервале. Уменьшите шаг")
        return
    }
    val interval = Interval(a, b, h)
    val interval2 = Interval(a, b, h/2)
    val method: Method = when (chosenMethod) {
        1-> Euler(interval, y, function, estimate)
        else -> Adams(interval, y, function, estimate)
    }
    val method2: Method = when (chosenMethod) {
        1-> Euler(interval2, y, function, estimate)
        else -> Adams(interval2, y, function, estimate)
    }
    var list2 = method2.solve()
    var list = method.solve()
    var t = 0
    while (!method.checkEstimate(list, list2, method.p)) {
        list = list2.toList() as ArrayList<Point>
        method2.setIntervals(method2.interval.h / 2)
        list2 = method2.solve()
        t++
        if (t == 5) {
            println("Зацикливание")
            break
        }
    }
    method.printTable(list, list2)
    val graph = Graph(list)
    graph.drawMainFrame()
    val step = abs(b - a)/500
    val coeff = coeffitient.apply(list[0].x, list[0].y)
    list = ArrayList()
    var x = graph.leftX
    while (x < graph.rightX) {
        list.add(Point(x, realFunction.apply(x, coeff)))
        x += step
    }
    graph.list = list
    graph.drawDependency("Исходная функция")
    graph.showGraph()
}