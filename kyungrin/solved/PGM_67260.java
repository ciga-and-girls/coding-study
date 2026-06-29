import java.util.*;

public class PGM_67260 {

    static int N;
    static ArrayList<Integer>[] graph;
    static HashMap<Integer, Integer> needLst;
    static boolean[] visited;
    static Set<Integer> visitedNodes;

    public boolean solution(int n, int[][] path, int[][] order) {
        N = n;

        graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        needLst = new HashMap<>();
        for (int[] o : order) {
            if (o[1] == 0) return false;
            needLst.put(o[1], o[0]);
        }

        visited = new boolean[N];
        visitedNodes = new HashSet<>();

        for (int[] p : path) {
            int a = p[0];
            int b = p[1];

            graph[a].add(b);
            graph[b].add(a);
        }

        Queue<Integer> q = new ArrayDeque<>();
        int[] waiting = new int[N];

        q.offer(0);
        visited[0] = true;
        visitedNodes.add(0);

        while (!q.isEmpty()) {
            int now = q.poll();

            if (waiting[now] > 0) {
                q.offer(waiting[now]);
                visited[waiting[now]] = true;
                visitedNodes.add(waiting[now]);
            }

            for (int next : graph[now]) {
                if (visited[next]) continue;

                if (needLst.containsKey(next) && !visited[needLst.get(next)]) {
                    waiting[needLst.get(next)] = next;
                    continue;
                }

                q.offer(next);
                visited[next] = true;
                visitedNodes.add(next);
            }
        }

        return visitedNodes.size() == N;
    }
}
