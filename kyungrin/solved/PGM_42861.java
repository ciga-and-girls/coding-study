import java.util.*;

public class PGM_42861 {

    public int[] parent;

    public int solution(int n, int[][] costs) {
        int answer = 0;

        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        Arrays.sort(costs, (a, b) ->
                Integer.compare(a[2], b[2])
        );

        int connectCnt = 0;

        for (int[] cost : costs) {
            int a = cost[0];
            int b = cost[1];
            int c = cost[2];

            if (!(find(a) == find(b))) {
                union(a, b);
                answer += c;
                connectCnt++;
            }

            if (connectCnt == n - 1) break;
        }

        return answer;
    }

    private int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        parent[rootB] = rootA;
    }
}
