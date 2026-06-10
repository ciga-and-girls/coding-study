import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 *   다익스트라 (19분)
 */
public class Solution_12978_배달 {

	public static void main(String[] args) {

		int[] N = {5, 6};
		int[][][] road = {
				{{1,2,1}, {2,3,3}, {5,2,2}, {1,4,2},{5,3,1}, {5,4,2}},
				{{1,2,1}, {1,3,2}, {2,3,2}, {3,4,3}, {3,5,2}, {3,5,3}, {5,6,1}}
		};
		int[] K = {3, 4};
		
		for(int tc = 0; tc < 2; tc++) {
			System.out.println(solution(N[tc], road[tc], K[tc]));
		}
	}
	
	static class Node {
		int vertex;
		int time;
		
		public Node(int vertex, int weight) {
			this.vertex = vertex;
			this.time = weight;
		}
	}
	
	static List<Node>[] adjList;
	static int[] minTime;
	static final int INF = 1_000_000_000;
	
	public static int solution(int N, int[][] road, int K) {
		
		adjList = new List[N+1];
		for(int i = 0; i <= N; i++) adjList[i] = new ArrayList<>();
		
		for(int i = 0; i < road.length; i++) {
			int from = road[i][0];
			int to = road[i][1];
			int time = road[i][2];
			
			adjList[from].add(new Node(to, time));
			adjList[to].add(new Node(from, time));
		}
		
		minTime = new int[N+1];
		Arrays.fill(minTime, INF);
		minTime[1] = 0;
		dijkstra();
		
		int answer = 0;
		for(int i = 1; i <= N; i++) {
			if(minTime[i] <= K) answer++;
		}
		
		return answer;
	}
	
	private static void dijkstra() {
		
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
		pq.add(new Node(1, 0));
		
		while(!pq.isEmpty()) {
			
			Node cur = pq.poll();
			
			for(Node n : adjList[cur.vertex]) {
				if(minTime[n.vertex] < cur.time + n.time) continue;
				
				minTime[n.vertex] = cur.time + n.time;
				pq.add(new Node(n.vertex, minTime[n.vertex]));
			}
		}
	}

}
