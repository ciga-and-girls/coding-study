import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Solution_42861_섬연결하기 {
    
    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int cost;
        
        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.cost, other.cost);
        }
    }
    
    static int[] parents;
    
    static int find(int x) {
        if(parents[x] == x) return x;
        
        return parents[x] = find(parents[x]);
    }
    
    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        
        if(a < b) {
            parents[b] = a;
        } else {
            parents[a] = b;
        }
    }
    
    public int solution(int n, int[][] costs) {
        
        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < costs.length; i++) {
            edges.add(new Edge(costs[i][0], costs[i][1], costs[i][2]));
        }
        
        Collections.sort(edges);
        
        parents = new int[n];
        for(int i = 0; i < n; i++) parents[i] = i;
        
        int totalCost = 0;
        int cnt = 0;
        
        for(Edge edge : edges) {
            
            if(find(edge.from) != find(edge.to)) {
                
                union(edge.from, edge.to);
                
                totalCost += edge.cost;
                cnt++;
                
                if(cnt == n-1) break;
            }
        }
        
        
        return totalCost;
    }
}