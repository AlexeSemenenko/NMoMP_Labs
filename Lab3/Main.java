package com.company;

public class Main {

    public static void main(String[] args) {
        double a = 0.0;
        double b = 1.0;
        double c = 0.0;
        double d = 2.0;

        double hx = 0.05;
        double hy = 0.1;

        double delta = (2.0 * hy * hy * Math.pow(Math.sin(Math.PI * hx / (2.0 * Math.abs(b - a))), 2.0)
                + 2.0 * hx * hx * Math.pow(Math.sin(Math.PI * hy / (2.0 * Math.abs(d - c))), 2.0)) / (hx * hx + hy * hy);

        double qOpt = 2.0 / (1.0 + Math.sqrt(delta * (2.0 - delta)));
        double q1 = 0.5;
        double q2 = 1.0;
        double q3 = 1.95;

        double eps = 0.0001;

        int N = (int) (Math.abs(b - a) / hx);
        int M = (int) (Math.abs(d - c) / hy);

        int n = N + 1;
        int m = M + 1;

        double[][] v0 = new double[n][m];

        for (int i = 1; i < n - 1; i++) {
            double xi = a + i * hx;
            v0[i][0] = psii0(xi);
            v0[i][M] = psiiM(xi);
        }

        for (int j = 0; j < m; j++) {
            double yj = c + j * hy;
            v0[0][j] = psi0j(yj);
            v0[N][j] = psiNj(yj);
        }

        solveRelax(v0, hx, hy, a, c, eps, q1);
        solveRelax(v0, hx, hy, a, c, eps, q2);
        solveRelax(v0, hx, hy, a, c, eps, q3);
        double[][] result = solveRelax(v0, hx, hy, a, c, eps, qOpt);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(String.format("%.5f", result[i][j]) + " ");

            }
            System.out.println();
        }
    }

    public static double[][] solveRelax(double[][] v0, double hx, double hy, double a, double c, double eps, double q) {
        double[][] v = v0;
        double[][] v_1;
        double nevyazka;
        int count_iter = 0;

        do {
            count_iter++;
            v_1 = iteration(v, hx, hy, a, c, q);
            nevyazka = nevyazka(v, v_1);
            v = v_1;
        } while (nevyazka > q * eps);

        System.out.println("q = " + q);
        System.out.println("Nevyazka = " + nevyazka);
        System.out.println("Iter = " + count_iter + "\n");

        return v_1;
    }

    public static double[][] iteration(double[][] v, double hx, double hy, double a, double c, double q) {
        double[][] v_1 = new double[v.length][v[0].length];

        for (int i = 1; i < v.length - 1; i++) {
            v_1[i][0] = v[i][0];
            v_1[i][v[0].length - 1] = v[i][v[0].length - 1];
        }

        for (int j = 0; j < v[0].length; j++) {
            v_1[0][j] = v[0][j];
            v_1[v.length - 1][j] = v[v.length - 1][j];
        }

        for (int i = 1; i < v.length - 1; i++) {
            for (int j = 1; j < v[i].length - 1; j++) {
                double xi = a + i * hx;
                double yj = c + j * hy;

                v_1[i][j] = (1.0 - q) * v[i][j] + q * ((1.0 / ((2.0 / (hx * hx)) + (2.0 / (hy * hy))))
                        * (((v_1[i - 1][j] + v[i + 1][j]) / (hx * hx)) + ((v_1[i][j - 1] + v[i][j + 1]) / (hy * hy))
                        + f(xi, yj)));
            }
        }

        return v_1;
    }

    public static double nevyazka(double[][] v, double[][] v_1) {
        double max = v_1[0][0] - v[0][0];

        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                double diff = Math.abs(v_1[i][j] - v[i][j]);

                if (diff > max) {
                    max = diff;
                }
            }
        }

        return max;
    }

    public static double f(double xi, double yj) {
        return -6 * (xi + yj);
    }

    public static double psi0j(double yj) {
        return Math.pow(yj, 3);
    }

    public static double psiNj(double yj) {
        return 1 + Math.pow(yj, 3);
    }

    public static double psii0(double xi) {
        return Math.pow(xi, 3);
    }

    public static double psiiM(double xi) {
        return 8 + Math.pow(xi, 3);
    }
}
