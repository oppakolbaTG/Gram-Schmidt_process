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

    static double[][] gram_schmidt(double[] v1, double[] v2){
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Векторы должны быть одинаковой длины");
        }

        double[] u1 = normalize(v1);
        double projCoeff = scal(v2, u1);
        double[] proj = mult_number(projCoeff, u1);
        double[] u2 = sub(v2, proj);
        u2 = normalize(u2);
        return new double[][]{u1, u2};
    }


    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите длину векторов:");
        int length = scanner.nextInt();
        double[] array1 = new double[length];
        System.out.println("Введите элементы первого массива:");
        for (int i = 0; i < length; i++) {
            array1[i] = scanner.nextDouble();
        }


        System.out.println("Введите элементы второго массива:");
        double[] array2 = new double[length];
        for (int i = 0; i < length; i++) {
            array2[i] = scanner.nextDouble();
        }
        try {
            double[][] result = gram_schmidt(array1, array2);
            System.out.println("Ортогональная компонента первого вектора (u1): " + Arrays.toString(result[0]));
            System.out.println("Ортогональная компонента второго вектора (u2): " + Arrays.toString(result[1]));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}