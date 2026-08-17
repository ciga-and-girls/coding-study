import java.util.*;

public class PGM_12978 {

    class Node implements Comparable<Node> {
        public int target;
        public int weight;

        public Node(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    private ArrayList<Node>[] graph;

    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        graph = new ArrayList[N + 1];

        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < road.length; i++) {
            int[] data = road[i];
            int a = data[0];
            int b = data[1];
            int c = data[2];

            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));
        dist[1] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int t = cur.target;
            int w = cur.weight;

            if (dist[t] < w) continue;

            for (Node next : graph[t]) {
                int newCost = dist[t] + next.weight;

                if (newCost < dist[next.target]) {
                    dist[next.target] = newCost;
                    pq.add(new Node(next.target, newCost));
                }
            }
        }

        for (int i = 0; i <= N; i++) {
            int v = dist[i];

            if (v <= K) {
                answer++;
            }
        }

        return answer;
    }
}
