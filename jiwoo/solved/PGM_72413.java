import java.util.Arrays;

/*
 *  플루이드 워샬 (42분)
 *  1. 출발지에서 모든 정점까지의 요금 구하기
 *  2. 해당 정점에서 A와 B의 도착지점까지의 요금 구하기
 *  3. 최소 요금 갱신
 */
public class Solution_합승택시요금 {
	
	public static void main(String[] args) {
		
		int[] n = {6, 7, 6};  // 지점 개수
		int[] s = {4, 3, 4};  // 출발지점
		int[] a = {6, 4, 5};  // A의 도착지점
		int[] b = {2, 1, 6};  // B의 도착지점
		int[][][] fares = {
				{{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22}, {1, 6, 25}},
				{{5, 7, 9}, {4, 6, 4}, {3, 6, 1}, {3, 2, 3}, {2, 1, 6}},
				{{2,6,6}, {6,3,7}, {4,6,7}, {6,5,11}, {2,5,12}, {5,3,20}, {2,4,8}, {4,3,9}}
		};
		
		for(int tc = 0; tc < 3; tc++) {
			System.out.println(solution(n[tc], s[tc], a[tc], b[tc], fares[tc]));
		}
	}

	public static int solution(int n, int s, int a, int b, int[][] fares) {
		
		final int INF = 1_000_000;
		
		int[][] adjMatrix = new int[n+1][n+1];
		for(int i = 0; i <= n; i++) Arrays.fill(adjMatrix[i], INF);
		
		for(int[] info : fares) {
			int from = info[0];
			int to = info[1];
			int fare = info[2];
			
			adjMatrix[from][to] = fare;
			adjMatrix[to][from] = fare;
		}
		
		for(int k = 1; k <= n; k++) {  // 경유지
			for(int i = 1; i <= n; i++) {  // 출발지
				for(int j = 1; j <= n; j++) {  // 도착지
					if(i == j) adjMatrix[i][j] = 0;
					else adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
				}
			}
		}
		
        int answer = adjMatrix[s][a] + adjMatrix[s][b];
        for(int i = 1; i <= n; i++) {
        	if(i == s) continue;
        	answer = Math.min(answer, adjMatrix[s][i] + adjMatrix[i][a] + adjMatrix[i][b]);
        }
       
        return answer;
    }

}
