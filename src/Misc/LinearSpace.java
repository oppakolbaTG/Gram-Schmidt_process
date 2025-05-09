package Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinearSpace {
    static final double epsilon = 10e-10;
    private static List<VectorRn> basis;
    private int dimension;

    public LinearSpace(VectorRn... vector){
        this.basis = new ArrayList<>();
        if (vector == null || vector.length == 0) {
            return;
        }
        double[][] matrix = new double[vector[0].getDimension()][vector.length];
        for (int i = 0; i < vector.length; i++) {
            double[] components = vector[i].getComponents();
            for (int j = 0; j < components.length; j++) {
                matrix[j][i] = components[j];
            }
        }
        int rank = VectorRn.Rank(matrix, vector[0].getDimension(), vector.length);
        for (int i = 0; i < rank; i++) {
            double[] basisVector = new double[vector[0].getDimension()];
            for (int j = 0; j < basisVector.length; j++) {
                basisVector[j] = matrix[j][i];
            }
            basis.add(new VectorRn(basisVector));
        }
    }

    public LinearSpace(int dimension){
        this.dimension = dimension;
        this.basis = new ArrayList<>();
    }

    public static boolean linearInd(VectorRn[] vector) {
        int n = vector[0].getDimension();
        int k = vector.length;
        for(VectorRn v : vector) {
            if(v.getDimension() != n) {
                throw new IllegalArgumentException("Все векторы должны иметь одинаковую размерность");
            }
        }
        if(k > n) {
            return false;
        }
        double[][] matrix = new double[n][k];
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < n; j++) {
                matrix[j][i] = vector[i].getComponents()[j];
            }
        }
        int rank = VectorRn.Rank(matrix, n, k);
        return rank == k;
    }

    public static double[][] gramSchmidt(double[][] vectors) {
        int n = vectors.length;
        double[][] orthoBasis = new double[n][vectors[0].length];

        for (int i = 0; i < n; i++) {
            orthoBasis[i] = vectors[i].clone();
            for (int j = 0; j < i; j++) {
                double projectionCoeff = VectorRn.scal(vectors[i], orthoBasis[j]) / VectorRn.scal(orthoBasis[j], orthoBasis[j]);
                double[] projection = VectorRn.mult_number(projectionCoeff, orthoBasis[j]).getComponents();
                orthoBasis[i] = VectorRn.sub(orthoBasis[i], projection);
            }
            // orthoBasis[i] = normalize(orthoBasis[i]);
        }
        return orthoBasis;
    }

    public static boolean isInSpan(double[][] vectors, double[] target){

        if(vectors == null || vectors.length == 0 || target == null) {
            return false;
        }

        int size = vectors[0].length;
//        for(double[] matr : vectors){
//            if(matr.length != size) {
//                return false;
//            }
//        }

        double[][] matrix = new double[size][vectors.length + 1];
        for(int i = 0; i < size; i++){
            for (int j = 0; j < vectors.length; j++){
                matrix[i][j] = vectors[j][i];
            }
            matrix[i][vectors.length ] = target[i];
        }

        int rows = size;
        int cols = vectors.length + 1;
        int lead = 0;

        for (int r = 0; r < rows; r++) {
            if (lead >= cols) {
                break;
            }
            int i = r;
            while (i < rows && Math.abs(matrix[i][lead]) < epsilon) {
                i++;
                if (i == rows) {
                    i = r;
                    lead++;
                    if (lead == cols) {
                        break;
                    }
                }
            }
            if (lead >= cols) {
                break;
            }
            double[] temp = matrix[r];
            matrix[r] = matrix[i];
            matrix[i] = temp;
            double lv = matrix[r][lead];
            for (int j = 0; j < cols; j++) {
                matrix[r][j] /= lv;
            }
            for (int k = 0; k < rows; k++) {
                if (k != r) {
                    double factor = matrix[k][lead];
                    for (int j = 0; j < cols; j++) {
                        matrix[k][j] -= factor * matrix[r][j];
                    }
                }
            }
            lead++;
        }

        for(int i = 0; i < rows; i++) {
            boolean zeros = true;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix[i][j]) > epsilon) {
                    zeros = false;
                    break;
                }
            }
            if(zeros && Math.abs(matrix[i][cols - 1]) > epsilon){
                return false;
            }
        }
        return true;
    }


    public static double[] genInSpan(double[][] vectors){
        int size = vectors[0].length;

        Random rand = new Random();

        double[] result = new double[size];
        for(int i = 0; i < vectors.length; i++) {
            double coef = rand.nextDouble() * 2 - 1;
            for(int j = 0; j < size; j++) {
                result[j] += coef * vectors[i][j];
            }
        }

        return result;
    }

    public void printBasis() {
        if (basis.isEmpty()) {
            System.out.println("Базис пуст");
            return;
        }

        System.out.println("Базис линейной оболочки:");
        for (int i = 0; i < basis.size(); i++){
            VectorRn v = basis.get(i);
            System.out.println("Вектор " + (i + 1) + ":" +  arrayToString(v.getComponents()));
        }
    }

    private static String arrayToString(double[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}