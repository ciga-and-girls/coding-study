import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 *   시뮬레이션 (40분)
 */
public class Main_메이즈러너 {
	
	static int N, M, K;  // N: 미로 크기, M: 참가자 수, K: K초 반복
	static int[][] map;  // 미로: 0 -> 빈 칸, 1~9 -> 벽(내구도)
	static int[][] people;  // 참가자 좌표
	static int[] exit;  // 출구 좌표
	static int moves;  // 참가자들의 이동 거리 합
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		people = new int[M][2];
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			people[i][0] = Integer.parseInt(st.nextToken());
			people[i][1] = Integer.parseInt(st.nextToken());
		}
		
		exit = new int[2];
		st = new StringTokenizer(br.readLine());
		exit[0] = Integer.parseInt(st.nextToken());
		exit[1] = Integer.parseInt(st.nextToken());
		
		/* 입력 종료 */
		
		moves = 0;
		while(K > 0) {
			
			// 1. 참가자 이동
			move();
			
			// 2. 미로 회전
			rotate();
		}
		
		sb.append(moves).append("\n").append(exit[0]).append(" ").append(exit[1]);
		System.out.println(sb);
	}
	
	private static void move() {
		
		for(int i = 0; i < M; i++) {
			
			if(people[i][0] < 0) continue;  // 이미 탈출한 경우
			
			int r = people[i][0];
			int c = people[i][1];
			boolean moved = false;
			
			// 상하 이동
			if(r != exit[0]) {
				if(r > exit[0]) {  // 위로 한 칸 이동
					if(map[r-1][c] == 0) {
						people[i][0]--;
						moves++;
						moved = true;
					}
				} else {  // 아래로 한 칸 이동
					if(map[r+1][c] == 0) {
						people[i][0]++;
						moves++;
						moved = true;
					}
				}
			}
			
			// 좌우 이동
			if(!moved && people[i][1] != exit[1]) {
				if(c > exit[1]) {  // 왼쪽으로 한 칸 이동
					if(map[r][c-1] == 0) {
						people[i][1]--;
						moves++;
						moved = true;
					}
				} else {  // 오른쪽으로 한 칸 이동
					if(map[r][c+1] == 0) {
						people[i][1]++;
						moves++;
						moved = true;
					}
				}
			}
			
			// 탈출 여부 확인
			if(people[i][0] == exit[0] && people[i][1] == exit[1]) {
				people[i][0] = -1;
				people[i][1] = -1;
			}
			
		}
	}

}
