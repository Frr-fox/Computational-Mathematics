from equations import int_r, draw_point
import matplotlib.pyplot as plt
import numpy as np


def print_header():
    print("\n               Уточнение корня уравнения методом половинного деления")
    print("№ шага |     a     |     b     |     x     |     f(a)   |    f(b)    |    f(x)    |    |a-b|   ")


class Half_Division_Method:
    n = 0

    def __init__(self, eq):
        self.equation = eq

    def increment(self):
        self.n += 1

    def solve(self, left, right, estimate):
        if self.equation.get_value(left) * self.equation.get_value(right) > 0:
            print("На данном участке нет корней. ")
            return
        print_header()
        x = (left + right) / 2
        self.print_line(self.n, left, right, x)
        while True:
            self.increment()
            f_left = self.equation.get_value(left)
            fx = self.equation.get_value(x)
            draw_point(x, 0, self.n, "b", "x")
            if f_left * fx > 0:
                left = x
            else:
                right = x
            x = (left + right) / 2
            self.print_line(self.n, left, right, x)
            if abs(right - left) <= estimate:
                draw_point(x, 0, self.n, "r", "x")
                break
        plt.title(self.equation.string_equation)
        print("\nРезультат выполнения: ")
        print("x = %6.5f f(x) = %4.5f Количество итераций: %2d" % (int_r(x), int_r(self.equation.get_value(x)), self.n + 1))
        return "Результат выполнения: x = %6.5f f(x) = %4.5f Количество итераций: %2d" % \
               (int_r(x), int_r(self.equation.get_value(x)), self.n + 1)

    def print_line(self, n0, a, b, x0):
        print("-------|-----------|-----------|-----------|------------|------------|------------|-------------")
        print(" %-5d |%9.5f  |%9.5f  |%9.5f  |%10.5f  |%10.5f  |%10.5f  |%10.5f  " % (
            n0, int_r(a), int_r(b), int_r(x0), int_r(self.equation.get_value(a)),
            int_r(self.equation.get_value(b)), int_r(self.equation.get_value(x0)), int_r(abs(a - b))))
