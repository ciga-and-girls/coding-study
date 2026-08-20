/* 다익스트라 */

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Arrays;

class Solution_12978_배달_2 {
    
    static final int INF = 100_000_000;
    
    public int solution(int N, int[][] road, int K) {
        
        List<int[]>[] adjList = new List[N];
        for(int i = 0; i < N; i++) adjList[i] = new ArrayList<>();
        
        for(int i = 0; i < road.length; i++) {
            int from = road[i][0]-1;
            int to = road[i][1]-1;
            int time = road[i][2];
            
            adjList[from].add(new int[] {to, time});
            adjList[to].add(new int[] {from, time});
        }
        
        int[] minTime = new int[N];
        Arrays.fill(minTime, INF);
        minTime[0] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[] {0, minTime[0]});
        
        while(!pq.isEmpty()) {
            
            int[] cur = pq.poll();
            
            for(int[] n : adjList[cur[0]]) {
                int vertex = n[0];
                int time = n[1];
                
                if(minTime[vertex] > minTime[cur[0]] + time) {
                    minTime[vertex] = minTime[cur[0]] + time;
                    pq.add(new int[] {vertex, minTime[vertex]});
                }
            }
        }
        
        int answer = 0;
        for(int i = 0; i < N; i++) {
            if(minTime[i] > K) continue;
            answer++;
        }
        
        return answer;
    }
    
}