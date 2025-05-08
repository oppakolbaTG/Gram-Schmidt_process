import java.util.Arrays;
import java.util.Scanner;

public class Main {
static final double epsilon = 10e-10;
    static boolean isInSpan(double[][] vectors, double[] target){

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

    static double[][] gramSchmidt(double[][] vectors) {
        int n = vectors.length;
        double[][] orthoBasis = new double[n][vectors[0].length];

        for (int i = 0; i < n; i++) {
            orthoBasis[i] = vectors[i].clone();
            for (int j = 0; j < i; j++) {
                double projectionCoeff = Oper.scal(vectors[i], orthoBasis[j]) / Oper.scal(orthoBasis[j], orthoBasis[j]);
                double[] projection = Oper.mult_number(projectionCoeff, orthoBasis[j]);
                orthoBasis[i] = Oper.sub(orthoBasis[i], projection);
            }
            // orthoBasis[i] = normalize(orthoBasis[i]);
        }
        return orthoBasis;
    }

    public static void main(String[] args) {
        boolean otvet = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите метод который вы хотите использовать\n1.Ортогонализация Методом Грама-Шмидта\n2.Проверка принадлежности вектора лин. оболочке");

        while(!otvet) {
            switch (scanner.nextInt()){

                case 1:
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
                otvet = true;
                break;

                case 2:
                    System.out.println("Введите размерность векторов:");
                    int dim1 = scanner.nextInt();

                    System.out.println("Введите количество векторов:");
                    int n1 = scanner.nextInt();

                    double[][] vectors1 = new double[n1][dim1];
                    for (int i = 0; i < n1; i++) {
                        System.out.println("Введите вектор №" + (i + 1) + ":");
                        for (int j = 0; j < dim1; j++) {
                            vectors1[i][j] = scanner.nextDouble();
                        }
                    }

                    double[] tVector = new double[dim1];
                    System.out.println("Введите вектор который нужно проверить: ");
                    for (int j = 0; j < dim1; j++) {
                        tVector[j] = scanner.nextDouble();
                    }

                    if(isInSpan(vectors1, tVector)){

                        System.out.println("Вектор принадлежит лин. оболочке");
                        break;
                    }else{
                        System.out.println("Вектор не принадлежит лин. оболочке");
                        break;
                    }


                default:
                System.out.println("Выберите существующий вариант ответа!");
                break;
            }
        }
    }
}
