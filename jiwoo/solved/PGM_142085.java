import java.util.Arrays;

/*
 *   이분 탐색 (34분)
 * 
 *   AI 활용: 반례 생성
 *   (입력)
 *   n = 10
 *   k = 1
 *   enemy = {9, 2, 2};
 *   (출력)
 *   3
 */


public class Solution_142085_디펜스게임 {

	public static void main(String[] args) {

		int[] n = {7, 2, 10};
		int[] k = {3, 4, 1};
		int[][] enemy = {
				{4, 2, 4, 5, 3, 3, 1},
				{3, 3, 3, 3},
				{9, 2, 2}  // AI로 생성한 반례
		};
		
		for(int tc = 0; tc < 3; tc++) {
			System.out.println(solution(n[tc], k[tc], enemy[tc]));  // 5, 4, 3
		}
		
	}
	
	public static int solution(int n, int k, int[] enemy) {
		
		int left = 1;
		int right = enemy.length;
		int answer = 0;
		
		while(left <= right) {
			int mid = (left + right) / 2;
			
			if(check(n, k, enemy, mid)) {
				answer = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		
		return answer;
	}
	
	private static boolean check(int n, int k, int[] enemy, int limit) {
		
		int[] temp = new int[limit];
		for(int i = 0; i < limit; i++) {
			temp[i] = enemy[i];
		}
		
		Arrays.sort(temp);
		
		int used = 0;
		for(int e : temp) {
			if(n - e >= 0) n-= e;
			else used++;
		}
		
		return used <= k;
	}

}
