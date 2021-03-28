from equations import int_r, draw_point
import matplotlib.pyplot as plt
import numpy as np


def print_header():
    print("\n         Уточнение корня уравнения методом простой итерации")
    print("№ шага |    xₖ    |    f(xₖ)   |     xₖ₊₁    |    φ(xₖ)   |   |xₖ - xₖ₊₁|   ")


class Iteration_Method:
    n = 0

    def __init__(self, eq):
        self.equation = eq

    def increment(self):
        self.n += 1

    def solve(self, left, right, estimate):
        if self.equation.get_value(left) * self.equation.get_value(right) > 0:
            print("На данном участке нет корней. ")
            return
        print("\nВыбор начального приближения")
        self.print_info(left)
        self.print_info(right)
        parameter_lambda = - 1 / max(self.equation.first_derivative(left), self.equation.first_derivative(right))
        if abs(self.new_function_first_derivative(left, parameter_lambda)) >= 1 or \
                abs(self.new_function_first_derivative(right, parameter_lambda)) >= 1:
            print("φ(a) = %9.5f" % self.new_function_first_derivative(left, parameter_lambda))
            print("φ(b) = %9.5f" % self.new_function_first_derivative(right, parameter_lambda))
            print("Метод не сходится. Нарушено достаточное условие сходимости метода. "
                  "Уменьшите рассматриваемый промежуток")
            return
        if self.equation.get_value(right) * self.equation.second_derivative(right) > 0:
            x0 = right
        else:
            x0 = left
        res = x0
        print("φ(x) = %9.5f" % self.new_function_first_derivative(x0, parameter_lambda))
        print_header()
        self.draw_new_function(left, right, parameter_lambda)
        while True:
            x = self.new_function(x0, parameter_lambda)
            draw_point(x0, self.new_function(x0, parameter_lambda), self.n, "g", "φ")
            draw_point(x0, self.equation.get_value(x0), self.n, "b", "x")
            self.print_line(self.n, x0, x, parameter_lambda)
            if abs(x - x0) <= estimate:
                self.increment()
                draw_point(x, self.equation.get_value(x), self.n, "r", "x")
                break
            if x0 - x > res:
                print("Уменьшите рассматриваемый промежуток. Метод сходится только в малой окрестности корня")
                return
            res = abs(x0 - x)
            x0 = x
            self.increment()
        plt.title(self.equation.string_equation)
        print("\nРезультат выполнения: ")
        print("x = %6.5f f(x) = %4.5f Количество итераций: %2d" % (
            int_r(x), int_r(self.equation.get_value(x)), self.n))
        return "Результат выполнения: x = %6.5f f(x) = %4.5f Количество итераций: %2d" % (
            int_r(x), int_r(self.equation.get_value(x)), self.n)

    def new_function(self, x, parameter_lambda):
        return x + parameter_lambda * self.equation.get_value(x)

    def new_function_first_derivative(self, x, parameter_lambda):
        return 1 + parameter_lambda * self.equation.first_derivative(x)

    def draw_new_function(self, left, right, parameter_lambda):
        k_left, k_right = 1.2, 1.2
        if left > 0:
            k_left = 0.8
        if right < 0:
            k_right = 0.8
        x = np.arange(left * k_left, right * k_right, 0.01)
        y = self.new_function(x, parameter_lambda)
        plt.plot(x, y, c="g")

    def print_info(self, value):
        print("x = %-3.2f" % value)
        print("f(x) = %9.5f" % int_r(self.equation.get_value(value)))
        print("f''(x) = %9.5f" % int_r(self.equation.second_derivative(value)))
        print("f(x) * f''(x) > 0" if self.equation.get_value(value) * self.equation.second_derivative(value) > 0
              else "f(x) * f''(x) <= 0")
        # l = - 1 / self.equation.first_derivative(value)
        # fi = self.new_function_first_derivative(value, l)
        # print("λ = %9.5f" % int_r(l))
        # print("φ'(x) = %9.5f" % int_r(fi))
        # print("|φ'(x)| < 1" if abs(fi) < 1 else "|φ'(x)| >= 1")
        print("")

    def print_line(self, n, x0, x, parameter_lambda):
        print("-------|----------|-----------|-------------|-----------|----------------")
        print(" %-5d |%9.5f |%9.5f  |%11.5f  |%9.5f  |%9.5f  " %
              (n, int_r(x0), int_r(self.equation.get_value(x0)), int_r(x),
               int_r(self.new_function(x0, parameter_lambda)), int_r(abs(x0 - x))))
