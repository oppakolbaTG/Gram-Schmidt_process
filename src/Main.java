import Misc.LinearSpace;
import Misc.VectorRn;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static Misc.LinearSpace.randGenInSpan;

public class Main {




    public static void main(String[] args) {
        boolean otvet = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите метод который вы хотите использовать\n" +
                "1.Ортогонализация Методом Грама-Шмидта\n" +
                "2.Проверка принадлежности вектора лин. оболочке\n" +
                "3.Генерация вектора лин. оболочки\n" +
                "4.Проверить является ли вектор ЛНЗ\n" +
                "5.Вывести базис лин. оболочки");

        while(!otvet) {
            switch (scanner.nextInt()){

                /// ///
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

                double[][] orthoBasis = LinearSpace.gramSchmidt(vectors);

                System.out.println("\nОртогонализированный базис:");
                for (int i = 0; i < orthoBasis.length; i++) {
                    System.out.println("u" + (i + 1) + ": " + Arrays.toString(orthoBasis[i]));
                }
                otvet = true;
                break;


                /// ///
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

                    if(LinearSpace.isInSpan(vectors1, tVector)){

                        System.out.println("Вектор принадлежит лин. оболочке");
                        break;
                    }else{
                        System.out.println("Вектор не принадлежит лин. оболочке");
                        break;
                    }

                /// ///
                case 3:
                    System.out.println("Введите размерность векторов:");
                    int dim2 = scanner.nextInt();

                    System.out.println("Введите количество векторов:");
                    int n2 = scanner.nextInt();

                    double[][] basis = new double[n2][dim2];
                    for (int i = 0; i < n2; i++) {
                        System.out.println("Введите вектор №" + (i + 1) + ":");
                        for (int j = 0; j < dim2; j++) {
                            basis[i][j] = scanner.nextDouble();
                        }
                    }
                    System.out.println("Вы хотите сгенерировать случайные коэффициенты?\n1.Да\n2.Нет");
                    int ans = scanner.nextInt();
                    boolean otvet1 = false;
                    while(!otvet1) {
                        if (ans == 1) {
                            double[] randomVector = LinearSpace.randGenInSpan(basis);
                            System.out.println("Сгенерированный вектор: " + Arrays.toString(randomVector));
                            boolean isInSpan = LinearSpace.isInSpan(basis, randomVector);
                            System.out.println("Принадлежит оболочке: " + isInSpan);
                            otvet1 = true;
                            break;
                        } else if (ans == 2) {
                            double[] coefficients = new double[n2];
                            System.out.println("Введите коэффициенты для линейной комбинации:");

                            for (int i = 0; i < n2; i++) {
                                System.out.print("Коэффициент для вектора " + (i + 1) + ": ");
                                coefficients[i] = scanner.nextDouble();
                            }
                            double[] randomVector = LinearSpace.genInSpan(basis, coefficients);

                            System.out.println("Сгенерированный вектор: " + Arrays.toString(randomVector));
                            boolean isInSpan = LinearSpace.isInSpan(basis, randomVector);
                            System.out.println("Принадлежит оболочке: " + isInSpan);
                            otvet1 = true;
                            break;

                        } else {
                            System.out.println("Неверный выбор. Введите 1 или 2.");
                            return;
                        }
                    }
                    break;


                /// ///
                case 4:
                    System.out.println("Введите размерность векторов:");
                    int dim3 = scanner.nextInt();

                    System.out.println("Введите количество векторов:");
                    int n3 = scanner.nextInt();

                    VectorRn[] vectors3 = new VectorRn[n3];

                    for (int i = 0; i < n3; i++) {
                        System.out.println("Введите вектор №" + (i + 1) + ":");
                        double[] components = new double[dim3];
                        for (int j = 0; j < dim3; j++) {
                            components[j] = scanner.nextDouble();
                        }
                        vectors3[i] = new VectorRn(components);
                    }

                    if (LinearSpace.linearInd(vectors3)) {
                        System.out.println("Векторы линейно независимы");
                        break;
                    } else {
                        System.out.println("Векторы линейно зависимы");
                        break;
                    }
                /// ///
                case 5:
                    System.out.println("Введите размерность векторов:");
                    int dim4 = scanner.nextInt();
                    System.out.println("Введите количество векторов:");
                    int n4 = scanner.nextInt();
                    VectorRn[] vectors4 = new VectorRn[n4];
                    for (int i = 0; i < n4; i++) {
                        System.out.println("Введите вектор №" + (i + 1) + ":");
                        double[] components = new double[dim4];
                        for (int j = 0; j < dim4; j++) {
                            components[j] = scanner.nextDouble();
                        }
                        vectors4[i] = new VectorRn(components);
                    }
                    LinearSpace space = new LinearSpace(vectors4);
                    space.printBasis();
                    System.out.println(space);

                    break;

                    default:
                System.out.println("Выберите существующий вариант ответа!");
                break;
            }
        }
    }
}
