import java.util.HashMap;
import java.util.Map;

/*
 *   Map (34분)
 */
public class Solution_42578_의상 {

	public static void main(String[] args) {
		String[][][] clothes = {
				{{"yellow_hat", "headgear"},
				 {"blue_sunglasses", "eyewear"},
				 {"green_turban", "headgear"}},
				{{"crow_mask", "face"},
				 {"blue_sunglasses", "face"},
				 {"smoky_makeup", "face"}}
		};
		
		for(int tc = 0; tc < 2; tc++ ) {
			System.out.println(solution(clothes[tc]));
		}
	}
	
	public static int solution(String[][] clothes) {
		
		Map<String, Integer>map = new HashMap<>();
		for(int i = 0; i < clothes.length; i++) {
			String type = clothes[i][1];
			map.put(type, map.getOrDefault(type, 0) + 1);
		}
		
		int answer = 1;
		for(int cnt : map.values()) {
			answer *= (cnt + 1);
		}
		
		return --answer;  // 아무것도 선택하지 않는 경우 제외
	}
}
