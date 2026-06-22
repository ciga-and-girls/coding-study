import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 *  시뮬레이션 + BFS (84분)
 */
public class Main_전투로봇 {
	
	static int n;
	static int[][] map;
	static int[] robot = {-1, -1, 2};  // 전투로봇의 r, c, level
	static List<Monster> monsters;  // 몬스터들의 r, c, level 리스트
	static int totalTime;  // 일을 끝내기까지 걸린 시간
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static class Monster {
		int r;
		int c;
		int level;
		
		public Monster(int r, int c, int level) {
			this.r = r;
			this.c = c;
			this.level = level;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		n = Integer.parseInt(br.readLine());
		
		map = new int[n][n];
		monsters = new ArrayList<>();
		for(int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < n; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				
				if(map[r][c] == 9) {  // 전투로봇인 경우
					robot[0] = r;
					robot[1] = c;
					map[r][c] = 0;
				} else if(map[r][c] >= 1 && map[r][c] <= 6) {  // 몬스터인 경우
					monsters.add(new Monster(r, c, map[r][c]));
				}
				
			}
		}
		
		monsters.sort((a,b) -> {
			if(a.r == b.r) return a.c - b.c;
			else return a.r - b.r;
		});  // 몬스터의 행 -> 열 기준 오름차순 정렬
		
		totalTime = 0;
		int removed = 0;  // 없앤 몬스터 수
		while(monsters.size() > 0) {
			
			int time = findTarget();
			
			if(time == -1) break;
			
			totalTime += time;
			if(++removed == robot[2]) {  // 레벨 수만큼 몬스터를 없애면 레벨 상승
				robot[2]++;
				removed = 0;
			}
		}
		
		System.out.println(totalTime);
	}
	
	private static int findTarget() {
		
		int targetIndex = -1;
		int minTime = Integer.MAX_VALUE;
		
		for(int i = 0; i < monsters.size(); i++) {
			if(monsters.get(i).level >= robot[2]) continue;
			
			int time = getTime(i);
			
			if(time == -1) continue;
			
			if(time < minTime) {
				targetIndex = i;
				minTime = time;
			}
			
		}
		
		if(targetIndex != -1) {
			int r = monsters.get(targetIndex).r;
			int c = monsters.get(targetIndex).c;
			
			// 몬스터 제거
			map[r][c] = 0;
			monsters.remove(targetIndex);
			
			// 로봇 위치 갱신
			robot[0] = r;
			robot[1] = c;
		}
		
		return minTime == Integer.MAX_VALUE ? -1 : minTime;
	}
	
	// 전투로봇이 없애려는 몬스터까지 이동하는 데 걸리는 시간 반환 -> BFS
	private static int getTime(int i) {
		
		boolean[][] visited = new boolean[n][n];
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {robot[0], robot[1], 0});  // r, c, time
		visited[robot[0]][robot[1]] = true;
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			
			if(cur[0] == monsters.get(i).r && cur[1] == monsters.get(i).c) {
				return cur[2];
			}
			
			for(int dir = 0; dir < 4; dir++) {
				int nr = cur[0] + dr[dir];
				int nc = cur[1] + dc[dir];
				
				if(nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
				if(visited[nr][nc]) continue;
				if(map[nr][nc] > robot[2]) continue;
				
				visited[nr][nc] = true;
				queue.add(new int[] {nr, nc, cur[2]+1});
			}
		}
		
		return -1;
	}

}
