from equations import int_r, draw_point, draw_tangent
import matplotlib.pyplot as plt
import numpy as np


def print_header():
    print("\n                   Уточнение корня уравнения методом Ньютона")
    print("№ шага |    xₖ    |    f(xₖ)   |    f'(xₖ)   |     xₖ₊₁   |   |xₖ - xₖ₊₁|   ")


class Newton_Method:
    n = 0

    def __init__(self, eq):
        self.equation = eq

    def increment(self):
        self.n += 1

    def solve(self, left, right, estimate):
        if self.equation.get_value(left) * self.equation.get_value(right) > 0:
            print("На данном участке нет корней. ")
            return "На данном участке нет корней. "
        print_header()
        if self.equation.get_value(right) * self.equation.second_derivative(right) > 0:
            x0 = right
        else:
            x0 = left
        try:
            x = x0 - (self.equation.get_value(x0) / self.equation.first_derivative(x0))
        except ZeroDivisionError:
            print("Уточните входной интервал. Первая производная на промежутке равна нулю")
            return "Уточните входной интервал. Первая производная на промежутке равна нулю"
        self.print_line(self.n, x0, x)
        draw_point(x, 0, self.n, "b", "x")
        draw_tangent(x0, self.equation.get_value(x0), x)
        while True:
            self.increment()
            x0 = x
            x = x0 - (self.equation.get_value(x0) / self.equation.first_derivative(x0))
            draw_point(x, 0, self.n, "b", "x")
            draw_tangent(x0, self.equation.get_value(x0), x)
            self.print_line(self.n, x0, x)
            if abs(x - x0) <= estimate and abs(self.equation.get_value(x)) < estimate:
                draw_point(x, 0, self.n, "r", "x")
                break
        plt.title(self.equation.string_equation)
        print("\nРезультат выполнения: ")
        print("x = %6.5f f(x) = %4.5f Количество итераций: %2d" % (int_r(x), int_r(self.equation.get_value(x)), self.n + 1))
        return "Результат выполнения:\nx = %6.5f f(x) = %4.5f Количество итераций: %2d" % \
               (int_r(x), int_r(self.equation.get_value(x)), self.n + 1)

    def print_line(self, n, x0, x):
        print("-------|----------|-----------|-------------|-----------|----------------")
        print(" %-5d |%9.5f |%9.5f  |%11.5f  |%9.5f  |%9.5f  " % (n, int_r(x0), int_r(self.equation.get_value(x0)),
                                                                  int_r(self.equation.first_derivative(x0)), int_r(x),
                                                                  int_r(abs(x0 - x))))
