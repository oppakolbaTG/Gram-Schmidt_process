public class Oper {
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

}
