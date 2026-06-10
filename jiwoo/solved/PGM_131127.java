import java.util.HashMap;
import java.util.Map;

/*
 *   슬라이딩 윈도우 (14분)
 */
public class Solution_131127_할인행사 {

	public static void main(String[] args) {

		String[][] want = {
				{"banana", "apple", "rice", "pork", "pot"},
				{"apple"}
		};
		int[][] number = {{3, 2, 2, 2, 1}, {10}};
		String[][] discount = {
				{"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"},
				{"banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana"}
		};
		
		for(int tc = 0; tc < 2; tc++) {
			System.out.println(solution(want[tc], number[tc], discount[tc]));
		}
	}
	
	public static int solution(String[] want, int[] number, String[] discount) {
		
		int answer = 0;
		Map<String, Integer> map = new HashMap<>();
		for(int i = 0; i < 10; i++) {
			String key = discount[i];
			map.put(key, map.getOrDefault(key, 0) + 1);
		}
		if(check(want, number, map)) answer++;
		
		for(int i = 10; i < discount.length; i++) {
			String firstKey = discount[i-10];
			String lastKey = discount[i];
			
			map.put(firstKey, map.get(firstKey) - 1);
			map.put(lastKey, map.getOrDefault(lastKey, 0) + 1);
			
			if(check(want, number, map)) answer++;
		}
		
		return answer;
	}
	
	private static boolean check(String[] want, int[] number, Map<String, Integer>map) {
		
		for(int i = 0; i < want.length; i++) {
			String key = want[i];
			int num = number[i];
			
			if(map.getOrDefault(key, 0) < num) return false;
		}
		
		return true;
	}

}
