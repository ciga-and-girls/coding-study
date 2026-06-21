import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  Map + 부분집합 + 이분 탐색 (102분)
 * 
 *  AI 활용:
 *		1. 지원자를 미리 조건별로 분류해두기 -> Map
 *		2. 조건에 해당하는 지원자 수 구할 때 이분 탐색 사용
 */
public class Solution_72412_순위검색 {

	public static void main(String[] args) {

		String[] info = {
				"java backend junior pizza 150",
				"python frontend senior chicken 210",
				"python frontend senior chicken 150",
				"cpp backend senior pizza 260",
				"java backend junior chicken 80",
				"python backend senior chicken 50"
		};
		String[] query = {
				"java and backend and junior and pizza 100",
				"python and frontend and senior and chicken 200",
				"cpp and - and senior and pizza 250",
				"- and backend and senior and - 150",
				"- and - and - and chicken 100",
				"- and - and - and - 150"
		};
		
		for(int tc = 0; tc < 1; tc++) {
			System.out.println(Arrays.toString(solution(info, query)));
		}
	}
	
	static Map<String, List<Integer>> map;
	
	public static int[] solution(String[] info, String[] query) {
		
		map = new HashMap<>();
		
		for(int i = 0; i < info.length; i++) {
			String[] applicant = info[i].split(" ");
			int score = Integer.parseInt(applicant[4]);
			
			makeKey(0, "", applicant, score);
		}
		
		for(List<Integer> list : map.values()) {
			list.sort(Collections.reverseOrder());
		}
		
		int[] answer = new int[query.length];
		
		for(int i = 0; i < query.length; i++) {
			
			String[] condition = query[i].replace(" and ", " ").split(" ");
			String key = condition[0] + condition[1] + condition[2] + condition[3];
			int minScore = Integer.parseInt(condition[4]);
			
			answer[i] = getNum(map.getOrDefault(key, new ArrayList<>()), minScore);
		}
		
		return answer;
	}
	
	private static void makeKey(int idx, String key, String[] applicant, int score) {
		
		if(idx == 4) {
			List<Integer> list = map.getOrDefault(key, new ArrayList<>());
			list.add(score);
			map.put(key, list);
			return;
		}
		
		makeKey(idx+1, key+applicant[idx], applicant, score);
		
		makeKey(idx+1, key+"-", applicant, score);
	}
	
	private static int getNum(List<Integer> list, int minScore) {
		
		int left = 0;
		int right = list.size();
		
		while(left < right) {
			
			int mid = (left + right) / 2;
			
			if(list.get(mid) >= minScore) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		
		return left;
	}

}
