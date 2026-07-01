import java.util.ArrayList;

public class PGM_76503 {

    // (1) 총 합이 0이어야 한다.
    // (2) 자식에서 부모 노드로의 가중치 이동 규칙에 따라 전원 0이 될 수 있어야 한다.
    // (3) 리프부터 루트로 타고 올라가서, 루트가 0이면 가능, 아니면 불가능

    static long[] A;
    static long answer;

    // 인접 리스트
    static ArrayList<Integer>[] adjList;

    public long solution(int[] a, int[][] edges) {

        answer = 0;

        A = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            A[i] = a[i];
        }

        adjList = new ArrayList[a.length];
        for (int i = 0; i < a.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] nodes : edges) {
            int b = nodes[0];
            int c = nodes[1];
            adjList[b].add(c);
            adjList[c].add(b);
        }

        // (1) 전체 합은 반드시 0이어야 한다.
        long sum = 0;
        for (long v : A) {
            sum += v;
        }

        if (sum != 0) {
            return -1;
        }

        // (2) 리프부터 부모로 가중치 이동
        dfs(0, 0);

        // (3) 루트도 0이 되어야 성공
        if (A[0] != 0) {
            return -1;
        }

        return answer;
    }

    // now : 현재 노드
    // parent : 부모 노드
    private static void dfs(int now, int parent) {

        for (int next : adjList[now]) {
            if (next == parent) {
                continue;
            }
            dfs(next, now);
        }

        answer += Math.abs(A[now]);
        A[parent] += A[now];
    }
}
