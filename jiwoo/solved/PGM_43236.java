import java.util.Arrays;

public class Solution_43236_징검다리 {

	public static void main(String[] args) {

		System.out.println(solution(25, new int[] {2, 14, 11, 21, 17}, 2));  // 4

	}
	
	public static int solution(int distance, int[] rocks, int n) {
		
		Arrays.sort(rocks);
		int left = 1;
		int right = distance;
		int answer = 0;
		
		while(left <= right) {
			int mid = (left + right) / 2;
			
			if(check(distance, rocks, n, mid)) {
				answer = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return answer;
	}
	
	private static boolean check(int distance, int[] rocks, int n, int limit) {
		int removed = 0;
		int prev = 0;
		
		for(int rock : rocks) {
			 if(rock - prev < limit) removed++;
			 else prev = rock;
		}
		
		if(distance - prev < limit) removed++;
		
		return removed <= n;
	}

}
