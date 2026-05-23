import java.util.Arrays;

/*
 *   (23분)
 */
public class Solution_68645_삼각달팽이 {

	public static void main(String[] args) {
		
		int[] n = {4, 5, 6};
		
		for(int tc = 0; tc < 3; tc++) {
			System.out.println(Arrays.toString(solution(n[tc])));
		}
	}
	
	public static int[] solution(int n) {
		
		int[][] map = new int[n][n];
		int[] answer = new int[n*(n+1)/2];
		
		// 하, 우, 대각(우하 -> 좌상)
		int[] dr = {1, 0, -1};
		int[] dc = {0, 1, -1};
		int dir = 0;
		
		int idx = 1;
		int r = 0;
		int c = 0;
		boolean[][] visited = new boolean[n][n];
		while(idx <= n*(n+1)/2) {
			
			map[r][c] = idx++;
			
			visited[r][c] = true;
			
			int nr = r + dr[dir];
			int nc = c + dc[dir];
			
			if(nr < 0 || nr >= n || nc < 0 || nc >= n || visited[nr][nc]) dir = (dir + 1) % 3;
			
			r += dr[dir];
			c += dc[dir];
		}
		
		int cnt = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j <= i; j++) {
				answer[cnt++] = map[i][j];
			}
		}
		
		return answer;
	}
}
