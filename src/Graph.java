import java.util.*;



public class Graph {
    private final int n; // количество вершин (вершины нумеруются от 1 до n)

    private final List<int[]> edgeList; // список рёбер (каждое ребро - массив [u, v])

    public Graph(int n, List<int[]> edgeList) { // Конструктор графа, n - количество вершин, edgeList - список ребер
        this.n = n;
        this.edgeList = edgeList;
    }

    // 1. Матрица смежности
    public int[][] getAdjacencyMatrix() { // метод для формирования матрицы смежности
        int[][] adj = new int[n + 1][n + 1]; // создание квадратной матрицы размером (n+1) × (n+1), индексы от 1 до n
        for (int[] edge : edgeList) {
            int u = edge[0];
            int v = edge[1];
            adj[u][v] = 1;
            adj[v][u] = 1;
        }
        return adj;
    }

    // 2. Матрица инциденций
    // Рёбра нумеруются в том же порядке, что и в edgeList (от 0 до m-1)
    public int[][] getIncidenceMatrix() { // метод для формирования матрицы инциденций
        int m = edgeList.size(); // функция для определения длины списка, так определяем количество ребер
        int[][] inc = new int[n + 1][m]; // строки 1..n, столбцы 0..m-1
        for (int j = 0; j < m; j++) {
            int u = edgeList.get(j)[0];
            int v = edgeList.get(j)[1];
            inc[u][j] = 1;
            inc[v][j] = 1;
        }
        return inc;
    }

    // 3. Списки связей (смежности)
    public List<Integer>[] getAdjacencyLists() { // метод для формирования списка связей, метод возвращает массив списков (список номеров смежных вершин)
        List<Integer>[] adjLists = new List[n + 1]; //создание пустого массива размером n+1
        for (int i = 1; i <= n; i++) {
            adjLists[i] = new ArrayList<>(); // инициализируем каждый элемент массива пустым списком, для каждой вершины свой список
        }
        // создаем списки смежности
        for (int[] edge : edgeList) {
            int u = edge[0];
            int v = edge[1];
            adjLists[u].add(v);
            adjLists[v].add(u);
        }
        // Для удобства отсортируем каждый список по возрастанию
        for (int i = 1; i <= n; i++) {
            Collections.sort(adjLists[i]);
        }
        return adjLists;
    }
    // 4. Перечень рёбер (возвращаем исходный список)
    public List<int[]> getEdgeList() { // метод для возвращения исходного списка
        return edgeList;
    }
    // 5. Степени вершин
    public int[] getDegrees() { // Метод определения степеней вершин
        int[] deg = new int[n + 1]; //Создаем нулевой массив размером n + 1
        for (int[] edge : edgeList) { // Для каждого ребра увеличиваем счётчик степени для обеих вершин на 1
            deg[edge[0]]++;
            deg[edge[1]]++;
        }
        return deg;
    }
    // 6. Проверка на полноту
    public boolean isComplete() { // Метод проверки на полноту
        int totalEdges = edgeList.size(); // получение количества ребер
        // В полном графе число рёбер = n*(n-1)/2
        if (totalEdges != n * (n - 1) / 2) {
            return false;
        }
        // Можно также проверить, что нет изолированных вершин и степени равны n-1
        int[] deg = getDegrees(); // Проверяем, что степень каждой вершины равна n1. Если хоть одна не совпадает – граф не полный.
        for (int i = 1; i <= n; i++) {
            if (deg[i] != n - 1) {
                return false;
            }
        }
        return true; // все проверки пройдены, граф полный
    }
    // 7. Вершины с максимальной степенью
    public List<Integer> getVerticesWithMaxDegree() {
        int[] deg = getDegrees();
        int maxDeg = 0;
        for (int i = 1; i <= n; i++) {
            if (deg[i] > maxDeg) {
                maxDeg = deg[i];
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (deg[i] == maxDeg) {
                result.add(i);
            }
        }
        return result;
    }
    // 8. Вершины с минимальной степенью
    public List<Integer> getVerticesWithMinDegree() {
        int[] deg = getDegrees();
        int minDeg = Integer.MAX_VALUE;

        for (int j : deg) {
            if (j < minDeg) {
                minDeg = j;
            }
        }

        List<Integer> result = new ArrayList<>();

        // Collect all vertices with that minimum degree
        for (int i = 0; i < deg.length; i++) {
            if (deg[i] == minDeg) {
                result.add(i);
            }
        }

        return result;
    }

    // Вспомогательные методы для вывода
    private static void printMatrix(int[][] matrix, int n) { // Метод печати квадратной матрицы (матрица смежности)
        System.out.println("Матрица смежности" + ":");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.printf("%3d ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    private static void printIncidenceMatrix(int[][] inc, int n, int m)
    { // Метод печати матрицы инциденций
        System.out.println("Матрица инциденций" + ":");
        System.out.print(" ");
        for (int j = 0; j < m; j++) {
            System.out.printf("e%02d ", j + 1);
        }
        System.out.println();
        for (int i = 1; i <= n; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < m; j++) {
                System.out.printf("%3d ", inc[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    private static void printAdjacencyLists(List<Integer>[] lists, int n) { // метод печати списков смежности
        System.out.println("Списки связей (смежности):");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " -> ");
            for (int v : lists[i]) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    private static void printEdgeList(List<int[]> edges) { // метод печати перечня ребер (исходный список)
        System.out.println("Перечень рёбер:");
        for (int[] e : edges) {
            System.out.println("(" + e[0] + ", " + e[1] + ")");
        }
        System.out.println();
    }

    // Главный метод
    static void main() {
        //вариант 24
        //количество ребер - 10
        //количество вершин - 6

        List<int[]> edges = Arrays.asList(
                new int[]{1, 5},
                new int[]{1, 2},
                new int[]{1, 4},
                new int[]{2, 3},
                new int[]{5, 2},
                new int[]{4, 5},
                new int[]{6, 4},
                new int[]{3, 4},
                new int[]{6, 2},
                new int[]{5, 6}
        );

        int n = 6;
        Graph graph = new Graph(n, edges);

        // 1. Матрица смежности
        int[][] adj = graph.getAdjacencyMatrix();
        printMatrix(adj, n);

        // 2. Матрица инциденций
        int[][] inc = graph.getIncidenceMatrix();
        printIncidenceMatrix(inc, n, edges.size());

        // 3. Списки связей
        List<Integer>[] adjLists = graph.getAdjacencyLists();
        printAdjacencyLists(adjLists, n);

        // 4. Перечень рёбер
        printEdgeList(graph.getEdgeList());

        // 5. Степени вершин
        int[] degrees = graph.getDegrees();
        System.out.println("Степени вершин:");
        for (int i = 1; i <= n; i++) {
            System.out.println("deg(" + i + ") = " + degrees[i]);
        }
        System.out.println();

        // 6. Проверка на полноту
        if (graph.isComplete()) {
            System.out.println("Граф полный.");
        } else {
            System.out.println("Граф не полный.");
        }

        // 7. Вершины с максимальной степенью
        List<Integer> maxDegVertices = graph.getVerticesWithMaxDegree();
        System.out.print("Вершины с максимальной степенью: ");
        for (int v : maxDegVertices) {
            System.out.print(v + " ");
        }

        // 8. Вершины с минимальной степенью
        List<Integer> minDegVertices = graph.getVerticesWithMinDegree();
        System.out.print("Вершины с минимальной степенью: ");
        for (int v : minDegVertices) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
