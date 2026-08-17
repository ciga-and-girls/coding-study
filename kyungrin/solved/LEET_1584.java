import java.util.*;

public class LEET_1584 {

    class Node implements Comparable<Node> {
        int a, b;
        int val;

        public Node(int a, int b, int val) {
            this.a = a;
            this.b = b;
            this.val = val;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.val, o.val);
        }
    }

    int[] parent;
    PriorityQueue<Node> pq;

    public int minCostConnectPoints(int[][] points) {

        // 1. 간선 생성
        pq = new PriorityQueue<>();
        int cnt = points.length;

        if (cnt <= 1) return 0;

        for (int i = 0; i < cnt; i++) {
            for (int j = i + 1; j < cnt; j++) {
                int val =
                        Math.abs(points[i][0] - points[j][0])
                        + Math.abs(points[i][1] - points[j][1]);

                pq.add(new Node(i, j, val));
            }
        }

        // 2. 간선 선정
        int n = 0;
        int result = 0;

        parent = new int[cnt];

        for (int i = 0; i < cnt; i++) {
            parent[i] = i;
        }

        while (true) {
            Node cur = pq.poll();

            int a = cur.a;
            int b = cur.b;
            int val = cur.val;

            if (find(a) == find(b)) continue;

            union(a, b);
            result += val;
            n++;

            if (n == cnt - 1) break;
        }

        // 3. 출력
        return result;
    }

    public int find(int x) {
        if (x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        parent[rootB] = rootA;
    }
}
