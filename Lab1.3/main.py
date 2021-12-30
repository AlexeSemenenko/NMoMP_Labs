from math import e, sin
from progonka import solve


if __name__ == "__main__":
    N = 10
    h = 1 / N

    def d(i):
        if i == N:
            return e ** (1 - h / 2)
        elif i == 0:
            return e ** (h / 2)
        return e ** (i * h - h / 2) * (1 + e ** h) / 2

    def phi(i):
        if i == N:
            return sin(1 - h / 2)
        elif i == 0:
            return sin(h / 2)
        return (sin((i - 1 / 2) * h) + sin((i + 1 / 2) * h)) / 2

    def A(i):
        return e ** (i * h - h / 2) * (1 - h ** 2 / 4)

    a = [- A(i) / h ** 2 for i in range(1, N)] + [- A(N) / h]
    b = [- A(1) / h] + [- A(i + 1) / h ** 2 for i in range(1, N)]
    c = [- A(1) / h - h / 2 * d(0)] + [-(A(i + 1) + A(i)) / h ** 2 - d(i) for i in range(1, N)] + [
        - A(N) / h - 1 - h / 2 * d(N)]
    f = [- h / 2 * phi(0)] + [-phi(i) for i in range(1, N)] + [- h / 2 * phi(N)]

    print(*solve(N, a, c, b, f), sep='\n')
