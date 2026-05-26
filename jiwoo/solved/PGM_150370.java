import java.util.Arrays;

/*
 *   날짜 계산 + 문자열 처리 -> 구현 (54분)
 */
public class Solution_150370_개인정보수집유효기간 {

	public static void main(String[] args) {

		String[] today = {"2022.05.19", "2020.01.01"};
		String[][] terms = {
				{"A 6", "B 12", "C 3"},
				{"Z 3", "D 5"}
		};
		String[][] privacies = {
				{"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"},
				{"2019.01.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z"}
		};
		
		for(int tc = 0; tc < 2; tc++) {
			System.out.println(Arrays.toString(solution(today[tc], terms[tc], privacies[tc])));
		}
	}
	
	static int[] tdy;
	
	public static int[] solution(String today, String[] terms, String[] privacies) {
		
		tdy = dateFormat(today);
		
		int[] type = new int[127];
		for(int i = 0; i < terms.length; i++) {
			type[terms[i].charAt(0)] = Integer.parseInt(terms[i].substring(2));
		}
		
		int[] destroyed = new int[privacies.length];
		int idx = 0;
		for(int i = 0; i < privacies.length; i++) {
			int[] date = dateFormat(privacies[i].substring(0, 10));
			int term = type[privacies[i].charAt(11)];
			
			if(check(date, term)) destroyed[idx++] = i+1;
		}
		
		int[] answer = new int[idx];
		for(int i = 0; i < idx; i++) {
			answer[i] = destroyed[i];
		}
		return answer;
	}
	
	private static int[] dateFormat(String date) {
		
		int[] result = new int[3];
		result[0] = Integer.parseInt(date.substring(0, 4));  // year
		result[1] = Integer.parseInt(date.substring(5, 7));  // month
		result[2] = Integer.parseInt(date.substring(8, 10));  // day
		
		return result;
	}
	
	private static boolean check(int[] date, int period) {
		
		date[0] += period / 12;
		date[1] += period % 12;
		
		if(date[1] > 12) {
			date[0]++;
			date[1] %= 12;
		}
		
		for(int i = 0; i < 3; i++) {
			if(date[i] < tdy[i]) return true;
			else if(date[i] > tdy[i]) return false;
			
		}
		
		return true;
	}

}
