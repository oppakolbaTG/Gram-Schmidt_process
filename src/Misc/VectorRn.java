package Misc;

public class VectorRn {
    private double[] components;
    private int dimension;

    public VectorRn(double[] components) {
        if(components == null || components.length == 0) throw new IllegalArgumentException("Компоненты не могут быть пустыми");
        this.dimension = components.length;
        this.components = components.clone();
    }

    public VectorRn(int n){
        if(n <= 0) throw new IllegalArgumentException("Размерность должна быть положительной");  // Добавлена проверка
        this.dimension = n;
        this.components = new double[n];
    }

    public double[] getComponents() {
        return components.clone();
    }

    public int getDimension() {
        return dimension;
    }

    static double scal(double[] a, double[] b) {
        if(a.length != b.length) throw new IllegalArgumentException("Векторы должны быть одной размерности");  // Добавлена проверка
        double dot = 0;
        int size = a.length;
        for(int i = 0; i < size; i++) {
            dot += a[i] * b[i];
        }
        return dot;
    }

    static VectorRn mult_number(double num, double[] vec) {
        int size = vec.length;
        VectorRn mult = new VectorRn(size);
        for(int i = 0; i < vec.length; i++) {
            mult.components[i] = num * vec[i];
        }
        return mult;
    }

    static double[] sub(double[] v1, double[] v2) {
        int size1 = v1.length;
        int size2 = v2.length;
        if(size1 != size2) throw new IllegalArgumentException("Нельзя вычитать векторы разной размерности");
        double[] sub = new double[size1];
        for(int i = 0; i < v1.length; i++) {
            sub[i] += v1[i] - v2[i];
        }
        return sub;
    }

    static double[] sum(double[] v1, double[] v2) {
        int size1 = v1.length;
        int size2 = v2.length;
        if(size1 != size2) throw new IllegalArgumentException("Нельзя сложить векторы разной размерности");
        double[] add = new double[size1];
        for(int i = 0; i < v1.length; i++) {
            add[i] += v1[i] + v2[i];
        }
        return add;
    }

    static double[] normalize(double[] v) {
        double norm = Math.sqrt(scal(v, v));
        if(norm == 0) throw new IllegalArgumentException("Нельзя нормализовать нулевой вектор");
        double[] normalized = new double[v.length];
        for(int i = 0; i < v.length; i++) {
            normalized[i] += v[i] / norm;
        }
        return normalized;
    }

    static double norm(double[] vector) {
        return Math.sqrt(scal(vector, vector));
    }

    public static int Rank(double[][] matrix, int rows, int cols) {
        int rank = 0;
        for(int col = 0; col < cols && rank < rows; col++) {
            int pivot = rank;
            while(pivot < rows && Math.abs(matrix[pivot][col]) < 1e-10) {
                pivot++;
            }
            if(pivot == rows) {
                continue;
            }
            if(pivot != rank) {
                double[] temp = matrix[pivot];
                matrix[pivot] = matrix[rank];
                matrix[rank] = temp;
            }
            for(int i = rank + 1; i < rows; i++) {
                double factor = matrix[i][col] / matrix[rank][col];
                for(int j = col; j < cols; j++) {
                    matrix[i][j] -= factor * matrix[rank][j];
                }
            }
            rank++;
        }
        return rank;
    }


}