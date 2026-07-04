import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *   조합 + Map (60분)
 *   
 *   AI 활용: 문자열 정렬 시 문자 배열로 변환 후 정렬
 */
public class Solution_72411_메뉴리뉴얼 {

	public static void main(String[] args) {

		String[][] orders = {
				{"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"},
				{"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"},
				{"XYZ", "XWY", "WXA"}
		};
		int[][] course = {
				{2,3,4},
				{2,3,5},
				{2,3,4}
		};
		
		for(int tc = 0; tc < 3; tc++) {
			System.out.println(Arrays.toString(solution(orders[tc], course[tc])));
		}
	}
	
	static Map<String, Integer> menu;
	static boolean[] visited;
	static int[] maxCnt;
	
	public static String[] solution(String[] orders, int[] course) {
		
		char[][] ordersArr = new char[orders.length][];
		for(int i = 0; i < orders.length; i++) {
			ordersArr[i] = orders[i].toCharArray();  // AI 활용: 문자열 정렬 -> 문자 배열로 바꾼 뒤 정렬
			Arrays.sort(ordersArr[i]);
		}
		
		menu = new HashMap<>();
		maxCnt = new int[11];
		for(int i = 0; i < ordersArr.length; i++) {
			for(int j = 0; j < course.length; j++) {
				
				if(ordersArr[i].length < course[j]) continue;
				
				visited = new boolean[ordersArr[i].length];
				comb(0, course[j], 0, "", ordersArr[i]);
			}
		}
		
		List<String> answerList = new ArrayList<>();

		for(String key : menu.keySet()) {
		    int len = key.length();

		    if(menu.get(key) >= 2 && menu.get(key) == maxCnt[len]) {
		        answerList.add(key);
		    }
		}

		Collections.sort(answerList);

		String[] answer = new String[answerList.size()];
		for(int i = 0; i < answerList.size(); i++) {
		    answer[i] = answerList.get(i);
		}

		return answer;
    }
	
	private static void comb(int idx, int num, int start, String key, char[] orders) {
		if(idx == num) {
			
			if(menu.containsKey(key)) {
				menu.put(key, menu.get(key)+1);
				maxCnt[num] = Math.max(maxCnt[num], menu.get(key));
			} else menu.put(key, 1);
			
			return;
		}
		
		for(int i = start; i < orders.length; i++) {
			
			visited[i] = true;
			comb(idx+1, num, i+1, key+orders[i], orders);
			visited[i] = false;
			
		}
	}

}
