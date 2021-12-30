def solve(N, a, c, b, f):
    alpha = [b[0] / c[0]]
    beta = [f[0] / c[0]]

    for i in range(1, N):
        beta.append((f[i] + a[i - 1] * beta[i - 1]) / (c[i] - alpha[i - 1] * a[i - 1]))
        alpha.append(b[i] / (c[i] - alpha[i - 1] * a[i - 1]))
    beta.append((f[N] + a[N - 1] * beta[N - 1]) / (c[N] - alpha[N - 1] * a[N - 1]))

    y = [beta[N]]
    for i in range(N, 0, -1):
        y.insert(0, alpha[i - 1] * y[0] + beta[i - 1])

    return y
