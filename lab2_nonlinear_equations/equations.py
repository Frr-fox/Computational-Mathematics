import math

import matplotlib.pyplot as plt
import numpy as np


def print_all_equation():
    print("1. 1.62x³ - 8.15x² + 4.39x + 4.29 = 0")
    print("2. x³ - x + 4 = 0")
    print("3. exp(x) - 5 = 0")
    print("4. sin(2*x) + π/4 = 0")


def choose_equation():
    global selected
    check = False
    print("Численное решение нелинейный уравнений")
    while not check:
        print_all_equation()
        print("Выберите нужное уравнение: ", end=' ')
        try:
            selected = int(input())
            if selected <= 0 or selected > 4:
                raise ValueError
            check = True
        except ValueError:
            print("Введенные данные некорректы. Повторите попытку")
    return Equation(selected)


def int_r(num):
    num = int(num * 100000 + (0.5 if num * 100000 > 0 else -0.5)) / 100000
    return num


def draw_point(x, y, n, color, function):
    plt.plot(x, y, "o", lw=2, c=color, alpha=0.8)
    plt.text(x, y + 0.1, function + "[%d]" % n, c=color)


def draw_tangent(x0, fx, x):
    plt.plot([x0, x], [fx, 0], lw=2, c="b", alpha=0.8, ls='-')


class Equation:
    def __init__(self, number):
        if number == 1:
            self.equation = lambda x: 1.62 * x * x * x - 8.15 * x * x + 4.39 * x + 4.29
            self.string_equation = "1.62x³ - 8.15x² + 4.39x + 4.29 = 0"
        if number == 2:
            self.equation = lambda x: x * x * x - x + 4
            self.string_equation = "x³ - x + 4 = 0"
        if number == 3:
            self.equation = lambda x: np.exp(x) - 5
            self.string_equation = "exp(x) - 5 = 0"
        if number == 4:
            self.equation = lambda x: np.sin(2*x) + np.pi/4
            self.string_equation = "sin(2*x) + π/4 = 0"

    def get_value(self, x):
        return self.equation(x)

    def first_derivative(self, x):
        h = 0.00001
        return (self.get_value(x + h) - self.get_value(x)) / h

    def second_derivative(self, x):
        h = 0.00001
        return (self.get_value(x + h) - 2 * self.get_value(x) + self.get_value(x - h)) / (
                h * h)

    def draw_graph(self, left, right):
        x = np.arange(left, right, 0.01)
        y = self.equation(x)
        plt.plot(x, y, c="r")
        if min(x) <= 0 and max(x) >= 0:
            plt.plot([0, 0], [max(y), min(y)], "^", lw=2, c="k", markevery=[0], alpha=0.5, ls='-')
        if max(y) >= 0 and min(y) <= 0:
            plt.plot([max(x), min(x)], [0, 0], ">", lw=2, c="k", markevery=[0], alpha=0.5, ls='-')
        plt.grid(True)
