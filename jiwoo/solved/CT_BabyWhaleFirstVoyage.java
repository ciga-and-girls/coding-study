import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 *   BFS (114분 + @)
 * 
 *   AI 활용: 시간 초과 해결하기 위해 2단계(가까운 바다로 이동) 구현 부분 수정(좌표 마다 매번 거리 구하기 -> 방문하지 않은 칸에 대한 거리 한 번에 구하기)
 */
public class Main_C_아기고래의첫항해 {
	
	static int N;
	static int[][] map;
	static boolean[][] visited;
	static int[][] result;
	static int idx;
	
	// 1: 상, 2: 하, 3: 좌, 4: 우
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, -1, 1};
	static int[][] order = {
			{3, 2, 4, 1},  // 2단계
			{1, 3, 4, 2},  // 1단계
			{2, 4, 3, 1},
			{3, 2, 1, 4},
			{4, 1, 2, 3}
	};
	

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());

		int canMove = 0;
		map = new int[N+1][N+1];
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 0) canMove++;
			}
		}
		
		visited = new boolean[N+1][N+1];
		result = new int[canMove][3];
		idx = 0;
		move(new int[] {r, c, d});
		
		while(idx < canMove) {
			int[] pos = nextPos(result[idx-1]);
			move(pos);
		}
		
		for(int i = 0; i < idx; i++) {
			sb.append(result[i][0]).append(" ").append(result[i][1]).append("\n");
		}
		
		System.out.println(sb);
	}
	
	private static void move(int[] start) {
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(start);
		visited[start[0]][start[1]] = true;
		result[idx][0] = start[0];
		result[idx][1] = start[1];
		result[idx++][2] = start[2];
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			
			for(int dir = 0; dir < 4; dir++) {
				int nr = cur[0] + dr[order[cur[2]][dir]];
				int nc = cur[1] + dc[order[cur[2]][dir]];
				
				if(nr <= 0 || nr > N || nc <= 0 || nc > N) continue;
				if(map[nr][nc] == 1) continue;
				if(visited[nr][nc]) continue;
				
				visited[nr][nc] = true;
				result[idx][0] = nr;
				result[idx][1] = nc;
				result[idx++][2] = order[cur[2]][dir];
				queue.add(result[idx-1]);
				break;
			}
		}
	}
	
	private static int[] nextPos(int[] start) {
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {start[0], start[1], start[2], 0});
		
		int[][] dist = new int[N+1][N+1];
		for(int i = 1; i <= N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
		
		boolean[][] moved = new boolean[N+1][N+1];
		moved[start[0]][start[1]] = true;
		
		int minDist = Integer.MAX_VALUE;
		int[] pos = new int[3];
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			
			for(int dir = 0; dir < 4; dir++) {
				int nr = cur[0] + dr[order[0][dir]];
				int nc = cur[1] + dc[order[0][dir]];
				
				if(nr <= 0 || nr > N || nc <= 0 || nc > N) continue;
				if(map[nr][nc] == 1) continue;
				if(moved[nr][nc]) continue;
				
				moved[nr][nc] = true;
				queue.add(new int[] {nr, nc, order[0][dir], cur[3] + 1});
				
				if(visited[nr][nc]) continue;
				
				dist[nr][nc] = cur[3] + 1;
				if(dist[nr][nc] > minDist) continue;
				else if(dist[nr][nc] == minDist && pos[0] < nr) continue;
				else if(dist[nr][nc] == minDist && pos[0] == nr && pos[1] < nc) continue;
				
				minDist = dist[nr][nc];
				pos[0] = nr;
				pos[1] = nc;
				pos[2] = order[0][dir];
			}
		}
		
		return pos;
	}

}
