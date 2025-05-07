import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static double scal(double[] a, double[] b) {
        double dot = 0;
        int size = a.length;
        for (int i = 0; i < size; i++) {
            dot += a[i] * b[i];
        }
        return dot;
    }

    static double[] mult_number(double num, double[] vec) {
        int size = vec.length;
        double[] mult = new double[size];
        for (int i = 0; i < vec.length; i++) {
            mult[i] = num * vec[i];
        }
        return mult;
    }

    static double[] sub(double[] v1, double[] v2) {
        int size1 = v1.length;
        int size2 = v2.length;
        double[] sub = new double[size1];
        if (size2 == size1) {
            for (int i = 0; i < v1.length; i++) {
                sub[i] += v1[i] - v2[i];
            }
        }
        return sub;
    }

    static double[] normalize(double[] v) {
        double norm = Math.sqrt(scal(v, v));
        if (norm == 0) throw new IllegalArgumentException("Нельзя нормализовать нулевой вектор");
        double[] normalized = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            normalized[i] = v[i] / norm;
        }
        return normalized;
    }

    static double[][] gramSchmidt(double[][] vectors) {
        int n = vectors.length;
        double[][] orthoBasis = new double[n][vectors[0].length];

        for (int i = 0; i < n; i++) {
            orthoBasis[i] = vectors[i].clone();
            for (int j = 0; j < i; j++) {
                double projectionCoeff = scal(vectors[i], orthoBasis[j]) / scal(orthoBasis[j], orthoBasis[j]);
                double[] projection = mult_number(projectionCoeff, orthoBasis[j]);
                orthoBasis[i] = sub(orthoBasis[i], projection);
            }
            // orthoBasis[i] = normalize(orthoBasis[i]);
        }
        return orthoBasis;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите размерность векторов:");
        int dim = scanner.nextInt();

        System.out.println("Введите количество векторов:");
        int n = scanner.nextInt();

        double[][] vectors = new double[n][dim];
        for (int i = 0; i < n; i++) {
            System.out.println("Введите вектор №" + (i + 1) + ":");
            for (int j = 0; j < dim; j++) {
                vectors[i][j] = scanner.nextDouble();
            }
        }

        double[][] orthoBasis = gramSchmidt(vectors);

        System.out.println("\nОртогонализированный базис:");
        for (int i = 0; i < orthoBasis.length; i++) {
            System.out.println("u" + (i + 1) + ": " + Arrays.toString(orthoBasis[i]));
        }
    }
}
