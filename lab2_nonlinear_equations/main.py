import re
import matplotlib.pyplot as plt
import equations
import halfDivision
import newton
import iteration

equation = equations.choose_equation()
check = False
while not check:
    try:
        print("Выберите тип ввода данных\n1. С консоли\n2. Из файла\nВаш выбор:", end=' ')
        type = int(input())
        if type < 0 or type > 2:
            raise ValueError
        check = True
    except ValueError:
        print("Введенные данные некорректны. Повторите попытку.")
estimate = 0.01
input_file_path = "resources/input.txt"
output_file_path = "resources/output.txt"
input_file = open(input_file_path)
output_file = open(output_file_path)
if type == 1:
    check = False
    while not check:
        try:
            print("Введите интервал (например, -2 -1):", end=' ')
            left, right = map(float, input().split(" "))
            if left > right:
                t = left
                left = right
                right = t
            check = True
        except ValueError:
            print("Введенные данные некорректны. Повторите попытку.")
    check = False
    while not check:
        try:
            print("Введите погрешность вычисления (по умолчанию 0.01):", end=' ')
            line = input()
            if line == "":
                break
            estimate = int(line)
            if estimate > 1 or estimate < 0:
                print("Погрешность должна быть в интервале от 0 до 1")
                raise ValueError
            check = True
        except ValueError:
            print("Введенные данные некорректны. Повторите попытку.")
else:
    check = False
    while not check:
        try:
            print("Введите путь к файлу (при нажатии на Enter будет использован файл по умолчанию):", end=' ')
            path = input()
            if path != "":
                input_file_path = path
            with open(input_file_path, "r") as input_file:
                left, right = map(float, (re.sub(" +", " ", input_file.readline())).strip().split(" "))
                if left > right:
                    t = left
                    left = right
                    right = t
                estimate = float(input_file.readline())
            check = True
        except FileNotFoundError:
            print("Файл не найден. Повторите попытку.")
        except ValueError:
            print("Данные в файле некорректны. Исправьте данные или введите адрес другого файла.")
methods = (halfDivision.Half_Division_Method(equation), newton.Newton_Method(equation),
           iteration.Iteration_Method(equation))
check = False
while not check:
    try:
        print("Метод решения нелинейного уравнения:\n1. Метод половинного деления\n2. Метод Ньютона\n"
              "3. Метод простой итерации")
        print("Выберите нужный метод:", end=' ')
        i = int(input())
        if i < 0 or i > 3:
            raise ValueError
        check = True
    except ValueError:
        print("Введенные данные некорректны. Повторите попытку.")
check = False
keeping = False
while not check:
    try:
        print("Сохранить результат в файл? (да/нет):", end=' ')
        retain = input().strip().lower()
        if retain == "да":
            keeping = True
            print("Введите путь к файлу (при нажатии на Enter будет использован файл по умолчанию):", end=' ')
            path = input()
            if path != "":
                output_file_path = path
        elif retain != "нет":
            raise ValueError
        check = True
    except ValueError:
        print("Введенные данные некорректны. Повторите попытку.")
method = methods[i-1]
result = method.solve(left, right, estimate)
if keeping:
    with open(output_file_path, "w") as output_file:
        output_file.write(result)
files = (input_file, output_file)
for file in files:
    if not file.closed:
        file.close()
k_left, k_right = 1.2, 1.2
if left > 0:
    k_left = 0.8
if right < 0:
    k_right = 0.8
equation.draw_graph(k_left * left, k_right * right)
plt.show()
