from math import e, sin
from progonka import solve


def F(x):
    return -e ** (-x) * sin(x)


if __name__ == "__main__":
    N = 10
    h = 1 / N

    a = [1 / (2 * h) - 1 / h ** 2] * (N - 1) + [1 / h]
    b = [- 1 / h] + [- 1 / (2 * h) - 1 / h ** 2] * (N - 1)
    c = [- 1 / h - h / 2] + [- 2 / h ** 2 - 1] * (N - 1) + [1 / h + 1 / e + h / 2 * (1 + 1 / e)]
    f = [F(i * h) for i in range(N)] + [h * sin(1) / (2 * e)]

    print(*solve(N, a, c, b, f), sep='\n')
