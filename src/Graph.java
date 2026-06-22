import java.util.*;



public class Graph {
    private final int n;                      // количество вершин (1..n)
    private final List<int[]> edgeList;       // список рёбер
    private final int[][] adjMatrix;          // матрица смежности (добавлено поле)

    // Конструктор – теперь сразу строит матрицу смежности
    public Graph(int n, List<int[]> edgeList) {
        this.n = n;
        this.edgeList = edgeList;
        this.adjMatrix = new int[n + 1][n + 1];
        for (int[] edge : edgeList) {
            int u = edge[0];
            int v = edge[1];
            adjMatrix[u][v] = 1;
            adjMatrix[v][u] = 1;
        }
    }

    // 1. Матрица смежности – возвращает готовую матрицу (поле)
    public int[][] getAdjacencyMatrix() {
        return adjMatrix;
    }

    // 2. Матрица инциденций
    public int[][] getIncidenceMatrix() {
        int m = edgeList.size();
        int[][] inc = new int[n + 1][m];
        for (int j = 0; j < m; j++) {
            int u = edgeList.get(j)[0];
            int v = edgeList.get(j)[1];
            inc[u][j] = 1;
            inc[v][j] = 1;
        }
        return inc;
    }

    // 3. Списки связей (смежности)
    public List<Integer>[] getAdjacencyLists() {
        List<Integer>[] adjLists = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            adjLists[i] = new ArrayList<>();
        }
        for (int[] edge : edgeList) {
            int u = edge[0];
            int v = edge[1];
            adjLists[u].add(v);
            adjLists[v].add(u);
        }
        for (int i = 1; i <= n; i++) {
            Collections.sort(adjLists[i]);
        }
        return adjLists;
    }

    // 4. Перечень рёбер
    public List<int[]> getEdgeList() {
        return edgeList;
    }

    // 5. Степени вершин
    public int[] getDegrees() {
        int[] deg = new int[n + 1];
        for (int[] edge : edgeList) {
            deg[edge[0]]++;
            deg[edge[1]]++;
        }
        return deg;
    }

    // 6. Проверка на полноту
    public boolean isComplete() {
        int totalEdges = edgeList.size();
        if (totalEdges != n * (n - 1) / 2) return false;
        int[] deg = getDegrees();
        for (int i = 1; i <= n; i++) {
            if (deg[i] != n - 1) return false;
        }
        return true;
    }

    // 7. Вершины с максимальной степенью
    public List<Integer> getVerticesWithMaxDegree() {
        int[] deg = getDegrees();
        int maxDeg = 0;
        for (int i = 1; i <= n; i++) {
            if (deg[i] > maxDeg) maxDeg = deg[i];
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (deg[i] == maxDeg) result.add(i);
        }
        return result;
    }

    // 8. Вершины с минимальной степенью
    public List<Integer> getVerticesWithMinDegree() {
        int[] deg = getDegrees();
        int minDeg = Integer.MAX_VALUE;
        for (int j : deg) {
            if (j < minDeg) minDeg = j;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < deg.length; i++) {
            if (deg[i] == minDeg) result.add(i);
        }
        return result;
    }

    // МЕТОДЫ ДЛЯ BFS

    /**
     * Поиск в ширину (BFS) от заданной вершины.
     * @param start     стартовая вершина (1..n)
     * @param distances массив размером n+1, заполняется расстояниями (для недостижимых останется -1)
     * @param order     список для записи порядка обхода (порядок добавления в очередь)
     */
    public void bfs(int start, int[] distances, List<Integer> order) {
        if (start < 1 || start > n) {
            throw new IllegalArgumentException("Стартовая вершина вне диапазона 1.." + n);
        }

        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        distances[start] = 0;
        queue.offer(start);
        order.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u = 1; u <= n; u++) {
                if (adjMatrix[v][u] == 1 && !visited[u]) {
                    visited[u] = true;
                    distances[u] = distances[v] + 1;
                    queue.offer(u);
                    order.add(u);
                }
            }
        }
    }

    /**
     * Обход всего графа в ширину (от всех компонент связности).
     * Выводит порядок обхода, расстояния от стартовой вершины каждой компоненты
     * и общее количество компонент.
     */
    public void bfsAllComponents() {
        boolean[] visited = new boolean[n + 1];
        int[] distances = new int[n + 1];
        Arrays.fill(distances, -1); // -1 означает "недостижимо"

        int components = 0;
        List<Integer> globalOrder = new ArrayList<>();

        for (int v = 1; v <= n; v++) {
            if (!visited[v]) {
                components++;
                List<Integer> componentOrder = new ArrayList<>();
                bfsWithVisited(v, visited, distances, componentOrder);
                System.out.println("Компонента " + components + " (старт = " + v + "):");
                System.out.println("  Порядок обхода: " + componentOrder);
                System.out.println("  Расстояния от " + v + ":");
                for (int u : componentOrder) {
                    System.out.println("    до " + u + " = " + distances[u]);
                }
                globalOrder.addAll(componentOrder);
            }
        }

        System.out.println("\nОбщий порядок обхода (по компонентам): " + globalOrder);
        System.out.println("Количество компонент связности: " + components);
        if (components == 1) {
            System.out.println("Граф связный.");
        } else {
            System.out.println("Граф несвязный.");
        }
    }

    /**
     * Вспомогательный BFS, использующий общий массив visited.
     * Заполняет distances и order для одной компоненты.
     */
    private void bfsWithVisited(int start, boolean[] visited, int[] distances, List<Integer> order) {
        Queue<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        distances[start] = 0;
        queue.offer(start);
        order.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u = 1; u <= n; u++) {
                if (adjMatrix[v][u] == 1 && !visited[u]) {
                    visited[u] = true;
                    distances[u] = distances[v] + 1;
                    queue.offer(u);
                    order.add(u);
                }
            }
        }
    }


    // МЕТОДЫ ДЛЯ DFS

    /**
     * Рекурсивный обход в глубину.
     * @param v          текущая вершина
     * @param visited    массив отметок (true – не посещена, false – посещена)
     * @param order      список для записи порядка обхода
     * @param treeEdges  список для записи древесных рёбер (в формате "(u,v)")
     */
    public void dfsRecursive(int v, boolean[] visited,
                             List<Integer> order, List<String> treeEdges) {
        visited[v] = false;          // помечаем как посещённую
        order.add(v);
        for (int u = 1; u <= n; u++) {
            if (adjMatrix[v][u] == 1 && visited[u]) {
                treeEdges.add("(" + v + "," + u + ")");
                dfsRecursive(u, visited, order, treeEdges);
            }
        }
    }

    /**
     * Нерекурсивный обход в глубину с явным стеком (массив + указатель).
     * @param start      стартовая вершина
     * @param visited    массив отметок (true – не посещена, false – посещена)
     * @param order      список для записи порядка обхода
     * @param treeEdges  список для записи древесных рёбер
     */
    public void dfsIterative(int start, boolean[] visited,
                             List<Integer> order, List<String> treeEdges) {
        int[] stack = new int[n + 1];
        int top = 0;

        // Помещаем стартовую вершину
        top++;
        stack[top] = start;
        visited[start] = false;
        order.add(start);

        while (top != 0) {
            int t = stack[top];          // текущая вершина на вершине стека
            int j = 1;
            boolean found = false;

            // Ищем первого непосещённого соседа
            while (!found && j <= n) {
                if (adjMatrix[t][j] == 1 && visited[j]) {
                    found = true;
                } else {
                    j++;
                }
            }

            if (found) {
                // Нашли нового соседа – идём вглубь
                top++;
                stack[top] = j;
                visited[j] = false;
                order.add(j);
                treeEdges.add("(" + t + "," + j + ")");
            } else {
                // Нет непосещённых соседей – возвращаемся
                top--;
            }
        }
    }

    /**
     * Подсчёт количества компонент связности (использует рекурсивный DFS).
     * @return количество компонент
     */
    public int countComponents() {
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(visited, true);
        int components = 0;
        for (int v = 1; v <= n; v++) {
            if (visited[v]) {
                components++;
                List<Integer> order = new ArrayList<>();
                List<String> edges = new ArrayList<>();
                dfsRecursive(v, visited, order, edges);
                System.out.println("  Компонента " + components + ": " + order);
            }
        }
        return components;
    }

    private static void printMatrix(int[][] matrix, int n) {
        System.out.println("Матрица смежности:");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.printf("%3d ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printIncidenceMatrix(int[][] inc, int n, int m) {
        System.out.println("Матрица инциденций:");
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

    private static void printAdjacencyLists(List<Integer>[] lists, int n) {
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

    private static void printEdgeList(List<int[]> edges) {
        System.out.println("Перечень рёбер:");
        for (int[] e : edges) {
            System.out.println("(" + e[0] + ", " + e[1] + ")");
        }
        System.out.println();
    }

    // Старые вспомогательные методы
    static void printNumbers1(int n) {
        if (n == 0) return;
        System.out.print(n + " ");
        printNumbers1(n - 1);
    }

    static void printNumbers2(int n) {
        if (n == 0) return;
        printNumbers2(n - 1);
        System.out.print(n + " ");
    }

    static int sum(int[] arr, int n) {
        if (n == 0) return 0;
        return arr[n - 1] + sum(arr, n - 1);
    }

    static void printUnvisited(boolean[] visited) {
        System.out.print("Непосещённые вершины: ");
        boolean hasUnvisited = false;
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i]) {
                System.out.print(i + " ");
                hasUnvisited = true;
            }
        }
        if (!hasUnvisited) {
            System.out.print("все вершины посещены");
        }
        System.out.println();
    }

    static int findFirstNeighbor(int v, boolean[] visited, int[][] A) {
        for (int u = 1; u < A.length; u++) {
            if (A[v][u] == 1 && !visited[u]) {
                return u;
            }
        }
        return -1;
    }

    //Главный метод
    static void main() {



        /*
        int[][] A = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 1, 1, 1, 0}
        };

        boolean[] visited = new boolean[6];

        System.out.println(findFirstNeighbor(1, visited, A));


        visited[2] = true;
        visited[4] = true;

        printUnvisited(visited);
        */

        //System.out.println(sum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 8));

        //printNumbers1(5);
        //printNumbers2(5);

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

        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        List<Integer> order = new ArrayList<>();
        graph.bfs(1, dist, order);
        System.out.println("BFS от 1: " + order);
        System.out.println("Расстояния:");
        for (int i = 1; i <= n; i++) {
            System.out.println("  до " + i + " = " + dist[i]);
        }

        System.out.println("\nОбход всех компонент");
        graph.bfsAllComponents();

        /*
        System.out.println("Рекурсивный DFS (старт = 1) ");
        boolean[] visitedRec = new boolean[n + 1];
        Arrays.fill(visitedRec, true);
        List<Integer> orderRec = new ArrayList<>();
        List<String> edgesRec = new ArrayList<>();
        graph.dfsRecursive(1, visitedRec, orderRec, edgesRec);
        System.out.println("Порядок обхода: " + orderRec);
        System.out.println("Древесные рёбра: " + edgesRec);

        System.out.println("\nНерекурсивный DFS (старт = 1) ");
        boolean[] visitedIter = new boolean[n + 1];
        Arrays.fill(visitedIter, true);
        List<Integer> orderIter = new ArrayList<>();
        List<String> edgesIter = new ArrayList<>();
        graph.dfsIterative(1, visitedIter, orderIter, edgesIter);
        System.out.println("Порядок обхода: " + orderIter);
        System.out.println("Древесные рёбра: " + edgesIter);

        // Сравнение
        System.out.println("\n Сравнение ");
        if (orderRec.equals(orderIter) && edgesRec.equals(edgesIter)) {
            System.out.println("Результаты совпадают");
        } else {
            System.out.println("Результаты различаются");
        }

        // Компоненты связности
        System.out.println("\nКомпоненты связности");
        int compCount = graph.countComponents();
        System.out.println("Количество компонент: " + compCount);
        if (compCount == 1) {
            System.out.println("Граф связный");
        } else {
            System.out.println("Граф несвязный");
        }



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


 */
    }
}
