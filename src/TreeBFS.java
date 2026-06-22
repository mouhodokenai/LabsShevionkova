import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeBFS {
    // Дерево задано списками смежности
    static void main(String[] args) {
        int n = 6;
        List<Integer>[] children = new List[n + 1];
        for (int i = 1; i <= n; i++) children[i] = new ArrayList<>();
        children[1].add(2); children[1].add(3);
        children[2].add(4); children[2].add(5);
        children[3].add(6);

        // Очередь для BFS
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        List<Integer> order = new ArrayList<>();
        int start = 1;
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            order.add(v);
            for (int child : children[v]) {
                if (!visited[child]) {
                    visited[child] = true;
                    queue.add(child);
                }
            }
        }
        System.out.println("Порядок обхода дерева в ширину: " + order);
    }
}