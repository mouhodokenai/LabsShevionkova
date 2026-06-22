import java.util.*;

public class DistanceInChain {
    static void main(String[] args) {
        int[] arr = {1, 0, 1, 0, 0, 1};
        int start = 0;

        // Собираем все позиции вершин для вывода
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) vertices.add(i);
        }

        // BFS по всем индексам массива (через нули тоже)
        Map<Integer, Integer> dist = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        dist.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int left = cur - 1;
            int right = cur + 1;

            if (left >= 0 && !dist.containsKey(left)) {
                dist.put(left, dist.get(cur) + 1);
                queue.add(left);
            }
            if (right < arr.length && !dist.containsKey(right)) {
                dist.put(right, dist.get(cur) + 1);
                queue.add(right);
            }
        }

        // Вывод расстояний только до вершин (где arr[i] == 1)
        System.out.println("Расстояния от вершины на позиции " + start + ":");
        for (int v : vertices) {
            System.out.println("до позиции " + v + " = " + dist.get(v));
        }
    }
}