import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 *   Set (25분)
 */
public class Solution_12981_영어끝말잇기 {

	public static void main(String[] args) {

		int[] n = {3, 5, 2};
		String[][] words = {
				{"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"},
				{"hello", "observe", "effect", "take", "either", "recognize", "encourage", "ensure", "establish", "hang", "gather", "refer", "reference", "estimate", "executive"},
				{"hello", "one", "even", "never", "now", "world", "draw"}
		};
		
		for(int tc = 0; tc < 3; tc++) {
			System.out.println(Arrays.toString(solution(n[tc], words[tc])));
		}
	}
	
	public static int[] solution(int n, String[] words) {
		
		Set<String> prevWords = new HashSet<>();
		String prevWord = words[0];
		prevWords.add(prevWord);
		int turn = 0;
		int[] answer = {0, 0};
		while(true) {
			
			// 탈락자가 없는 경우
			if(++turn == words.length) break;
			
			String cur = words[turn];
			
			// 앞사람이 말한 단어의 마지막 문자로 시작하는 단어가 아닌 경우 -> 탈락
			if(cur.charAt(0) != prevWord.charAt(prevWord.length()-1)) {
				answer[0] = (turn+1) % n == 0 ? n : (turn+1) % n;
				answer[1] = 1+ turn / n;
				break;
			}
		
			// 이전에 등장한 단어를 말하는 경우 -> 탈락
			if(prevWords.contains(cur)) {
				answer[0] = (turn+1) % n == 0 ? n : (turn+1) % n;
				answer[1] = 1+ turn / n;
				break;
			}
			
			prevWords.add(cur);
			prevWord = cur;
		}
		
		return answer;
	}

}
