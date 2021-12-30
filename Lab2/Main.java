package com.company;

public class Main {
	static final double h = 0.2;
	static final double tau = 0.2;

	static final double a = -1;
	static final double b = 1;
	static final double c = 0;
	static final double d = 2;

	static final int N = (int) ((b - a) / h);
	static final int M = (int) ((d - c) / tau);

    public static void main(String[] args) {
	    double[][] y = new double[N][M];

	    for (int j = 0; j < M; j++) {
	        y[N - 1][j] = psi(a + h * j);
        }

		for (int i = N - 2, k = 0; i >= 0; i--, k++) {
			for (int j = 1 + k; j < M - 1 - k; j++) {
				y[i][j] = Math.abs(y_i_jP1(y[i + 1][j - 1], y[i + 1][j], y[i + 1][j + 1]));
			}
		}

	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < M; j ++) {
	            System.out.print(String.format("%.2f", y[i][j]) + " ");
            }
	        System.out.println();
        }

    }

    public static double psi(double x) {
        return Math.pow(x, 2);
    }

    public static double y_i_jP1(double y_iM1_j, double y_i_j, double y_iP1_j) {
    	return tau * ((1 / (2 * h) + tau / (2 * Math.pow(h, 2))) * y_iM1_j + (1/ tau - tau / Math.pow(h, 2)) * y_i_j
				+ (tau / (2 * Math.pow(h, 2)) - 1 / (2 * h)) * y_iP1_j);
	}
}
