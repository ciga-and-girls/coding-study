import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/*
 *   시뮬레이션 (71분)
 *   
 *   AI 활용: 시간 초과 원인 분석 + 해결 방법
 *   	- 기존의 virus 리스트를 매 사이클마다 정렬하는 방식은 바이러스 수가 커지면 비용이 크게 증가
 *   	- 각 위치마다 바이러스 목록을 따로 관리 virus[][] (나이가 적은 바이러스부터 순차적으로 진행 Queue/Deque)
 *   	- 번식 시 나이가 1인 바이러스를 추가하기 위해 Deque 선택
 *   	- 처음에는 List로 바이러스 정보를 입력 받고 정렬한 후 Deuque 사용
 */
public class Main_바이러스실험 {
	
	static int n, m, k;
	static int[][] medium, nutrient;
	static List<Integer>[][] temp;
	static Deque<Integer>[][] virus;
	static List<int[]> position;
	
	// 상 우상 우 우하 하 좌하 좌 좌상
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());  // 배지의 크기
		m = Integer.parseInt(st.nextToken());  // 바이러스의 개수
		k = Integer.parseInt(st.nextToken());  // 총 사이클의 수
		
		medium = new int[n][n];  // 배지
		for(int r = 0; r < n; r++) {
			for(int c = 0; c < n; c++) {
				medium[r][c] = 5;
			}
		}
		
		nutrient = new int[n][n];  // 추가되는 양분
		for(int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < n; c++) {
				nutrient[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		temp = new List[n][n];  // 초기 정렬을 위한 List
		for(int r = 0; r < n; r++) {
			for(int c = 0; c < n; c++) {
				temp[r][c] = new ArrayList<>();
			}
		}
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int age = Integer.parseInt(st.nextToken());
			
			temp[r][c].add(age);
		}
		
		virus = new Deque[n][n];  // (r, c)에 위치한 바이러스 Deque
		for(int r = 0; r < n; r++) {
			for(int c = 0; c < n; c++) {
				virus[r][c] = new ArrayDeque<>();
			}
		}
		for(int r = 0; r < n; r++) {
			for(int c = 0; c < n; c++) {
				if(temp[r][c].isEmpty()) continue;
				
				temp[r][c].sort((a, b) -> a - b);  // 나이 기준 오름차순 정렬
				
				for(int age : temp[r][c]) {
					virus[r][c].add(age);
				}
			}
		}
		
		// k번 실험 진행
		while(k-- > 0) {
			
			position = new ArrayList<>();  // 번식할 바이러스 위치 저장 객체
			
			// 1. 나이가 어린 바이러스부터 양분 섭취 (섭취 불가능 시 양분화)
			consume();
			
			// 2. 바이러스 번식
			multiply();
			
			// 3. 양분 추가
			for(int r = 0; r < n; r++) {
				for(int c = 0; c < n; c++) {
					medium[r][c] += nutrient[r][c];
				}
			}
			
		}  // while문 종료
		
		System.out.println(m);
	}
	
	private static void consume() {
		
		for(int r = 0; r < n; r++) {
			for(int c = 0; c < n; c++) {
				
				if(virus[r][c].isEmpty()) continue;
				
				int size = virus[r][c].size();
				boolean canConsume = true;
				
				for(int i = 0; i < size; i++) {
					int age = virus[r][c].poll();
					
					if(canConsume && age <= medium[r][c]) {  // 양분 섭취 가능
						medium[r][c] -= age;
						if(++age % 5 == 0) position.add(new int[] {r, c});
						virus[r][c].add(age);
					} else {  // 양분 섭취 불가능
						if(canConsume) canConsume = false;
						medium[r][c] += age/2;
						m--;  // 바이러스 수 -1
					}
					
				}  // for문 종료 (i)
				
			}
		}  // 2중 for문 종료 (r, c)
		
	}
	
	private static void multiply() {
		
		for(int[] p : position) {
			
			int r = p[0];
			int c = p[1];
			
			for(int dir = 0; dir < 8; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				if(nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
				
				virus[nr][nc].addFirst(1);
				m++;
			}
			
		}
	}

}
