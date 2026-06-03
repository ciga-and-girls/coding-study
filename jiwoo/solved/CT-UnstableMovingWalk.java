import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 	시뮬레이션 (76+@분)
 * 
 * 	칸: 고정된 위치
 * 	판: 초기의 칸의 번호, 회전할 때마다 이동
 * 
 *  AI 활용: 시간 초과 원인 파악 -> 회전해서 n번째 칸에 도착한 사람을 바로 내리지 않아서 queue에 남아있음
 */
public class Main_불안한무빙워크 {

	static int n;  // 무빙워크의 길이
	static int k;  // 실험 종료 기준이 되는 안정성이 0개인 판의 개수
	static int[] stability;  // 각 판의 안정성
	static int cnt;  // 안정성이 0인 판의 개수
	static int cur1;  // 현재 1번 칸에 위치한 판의 번호
	static Queue<Integer> queue;  // 사람이 있는 판 (가장 먼저 올라간 사람부터 이동 -> FIFO -> Queue)
	static int people;  // 현재 무빙워크에 있는 사람 수
	static boolean[] used;  // 각 판에 사람이 있는지 (사람이 올라갈 수 있는 칸은 1~n번 칸)
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		stability = new int[2*n];
		cnt = 0;
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 2 * n; i++) {
			stability[i] = Integer.parseInt(st.nextToken());
			if(stability[i] == 0) cnt++;
		}
		
		/* 입력 종료 */
		
		cur1 = 0;
		int result = 0;  // 현재 진행 중인 실험이 몇 번째 실험인지
		queue = new ArrayDeque<>();
		people = 0;
		used = new boolean[2*n];
		
		while (cnt < k) {
			
			result++;
			
			// 1. 무빙워크 한 칸 회전
			cur1 = (2*n + cur1-1) % (2*n);
			
			// 2. 사람 이동
			if(!queue.isEmpty()) move(people);
			
			// 3. 1번 칸에 사람 올리기
			if(!used[cur1] && stability[cur1] != 0) {
				queue.add(cur1);
				used[cur1] = true;
				if(--stability[cur1] == 0) cnt++;
				people++;
			}
		}
		
		System.out.println(result);
		
	}
	
	private static void move(int p) {
		
		while(p-- > 0) {  // 무빙워크에 있는 사람 수만큼 반복
			
			int cur = queue.poll();  // 사람이 있는 판의 번호
			
			// 회전 직후 내려야 하는 사람
			if(cur == (cur1 + n-1) % (2*n)) {
				used[cur] = false;
				people--;
				continue;
			}
			
			int next = (cur + 1) % (2 * n);  // 앞선 칸의 판의 번호
			
			// 이동 불가능: 앞선 칸에 사람이 이미 있거나 앞선 칸의 안정성이 0인 경우
			if(used[next] || stability[next] == 0) {
				queue.add(cur);
				continue;
			}
			
			// 이동 가능
			used[cur] = false;
			if(--stability[next] == 0) cnt++;
			if(next == (cur1 + n-1) % (2*n)) people--;
			else {
				queue.add(next);
				used[next] = true;
			}
			
		}
	}

}
