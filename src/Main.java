//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //Вариант 24
    int N = 5, M = 6, k = 4, b = -3, A = -10, B = 10;

    System.out.println("=== Лабораторная работа №1 (Вариант " + "24" + ") ===\n");

    System.out.println("\n=== Задание 1 ===");
    task1(N);

    System.out.println("\n=== Задание 2 ===");
    task2(N, k, b);

    System.out.println("\n=== Задание 3 ===");
    task3(N, M, A, B);

    System.out.println("\n\n=== Задание 4 ===");
    task4(N);

}



// 1. Вывести на экран таблицу умножения размером N × N, где на пересечении
// строки i и столбца j находится число i × j
void task1(int N) {
    for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= N; j++) {
            System.out.printf("%4d", i * j);
        }
        System.out.println();
    }
}

/*
2. Создать одномерный массив целых чисел длины N. Заполнить его значениями
по формуле:
a[i] = i * k + b, где k и b – константы из варианта.
Найти и вывести:
• сумму всех элементов массива,
• среднее арифметическое (вещественное число),
• количество элементов, больших среднего.
 */
void task2(int N, int k, int b) {
    int[] arr = new int[N];
    int sum = 0;
    int big = 0;

    for (int i = 0; i < N; i++) {
        arr[i] = i * k + b;
        System.out.printf("%3d", arr[i]);
        sum += arr[i];
    }

    System.out.println();

    for (int i = 0; i < N; i++) {
        if (arr[i] > sum/N) big++;
    }

    System.out.println("Сумма всех элементов " + sum);
    System.out.println("Среднее арифметическое " + sum/N);
    System.out.println("Количество элементов, больших среднего " + big);
}

/*
3. Создать матрицу размером N × M (строки × столбцы). Заполнить её
случайными целыми числами от A до B (включительно).
Вывести матрицу в виде таблицы, а затем найти и вывести:
• максимальный элемент и его индексы (строку и столбец),
• минимальный элемент и его индексы.
 */
void task3(int N, int M, int A, int B) {
    Random rand = new Random();
    int[][] matrix = new int[N][M];
    int min = B;
    int max = A;
    int imax = 0, imin = 0, jmax = 0, jmin = 0;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            matrix[i][j] = rand.nextInt(B - A + 1) + A;
            if (matrix[i][j] < min) {
                min = matrix[i][j];
                imin = i;
                jmin = j;
            }
            if (matrix[i][j] > max) {
                max = matrix[i][j];
                imax = i;
                jmax = j;
            }
            System.out.printf("%3d", matrix[i][j]);
        }
        System.out.println();
    }
    System.out.printf("Минимальный элемент: %d, индекс (%d, %d) %n", min, imin, jmin);
    System.out.printf("Максимальный элемент: %d, индекс (%d, %d)", max, imax, jmax);
}

/*
4. Создать квадратную матрицу размером N × N и заполнить её числами от 1 до
N² по спирали (по часовой стрелке, начиная с левого верхнего угла). Вывести
матрицу на экран.
 */
void task4(int N) {
    int[][] matrix = new int[N][N];
    int value = 1;
    int top = 0;
    int bottom = N - 1;
    int left = 0;
    int right = N - 1;

    while (value <= N * N) {

        for (int i = left; i <= right && value <= N * N; i++) {
            matrix[top][i] = value++;
        }
        top++;

        for (int i = top; i <= bottom && value <= N * N; i++) {
            matrix[i][right] = value++;
        }
        right--;

        for (int i = right; i >= left && value <= N * N; i--) {
            matrix[bottom][i] = value++;
        }
        bottom--;

        for (int i = bottom; i >= top && value <= N * N; i--) {
            matrix[i][left] = value++;
        }
        left++;
    }


    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            System.out.printf("%3d", matrix[i][j]);
        }
        System.out.println();
    }
}