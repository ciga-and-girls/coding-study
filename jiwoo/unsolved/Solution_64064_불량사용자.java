import java.util.Arrays;

/*
 *     순열 (67분)
 * 
 *     주의사항: 제재 아이디 중복 여부
 */
public class Solution_64064_불량사용자 {

    public static void main(String[] args) {
        
        String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};  // 응모자 아이디
        String[][] banned_id = {  // 불량 사용자 아이디
                {"fr*d*", "abc1**"},
                {"*rodo", "*rodo", "******"},
                {"fr*d*", "*rodo", "******", "******"}
        };
        
        for(int tc = 0; tc < 3; tc++) {
            System.out.println(solution(user_id, banned_id[tc]));
        }
    }
    
    static String[] userId;  // 응모자 아이디
    static String[] bannedId;  // 불량 사용자 아이디
    static boolean[] selected;  // 제재 아이디 여부
    static int[][] available;  // 불량 사용자 아이디에 매핑 가능한 응모자 아이디 저장
    static int answer;
    
    public static int solution(String[] user_id, String[] banned_id) {
        
        userId = user_id;
        bannedId = banned_id;
        
        int user = user_id.length;
        int ban = banned_id.length;
        
        selected = new boolean[user];
        available = new int[ban][user];
        for(int i = 0; i < ban; i++) Arrays.fill(available[i], -1);
        
        // 각 불량 사용자 아이디에 대해 매핑 가능한 응모자 아이디 확인
        for(int i = 0; i < ban; i++) {
            for(int j = 0, idx = 0; j < user; j++) {
                if(bannedId[i].length() != userId[j].length()) continue;  // id 길이가 다르면 pass
                if(check(i, j)) available[i][idx++] = j;
            }
        }
        
        answer = 0;
        permutation(0);
        
        Arrays.sort(bannedId);
        int denominator = 1;
        int cnt = 1;
        for(int i = 1; i < ban; i++) {
        	if(bannedId[i].equals(bannedId[i-1])) cnt++;
        	else {
        		denominator *= factorial(cnt);
        		cnt = 1;
        	}
        }
        denominator *= factorial(cnt);
        
        return answer/denominator;
    }
    
    private static boolean check(int bIdx, int uIdx) {
        
        String ban = bannedId[bIdx];
        String user = userId[uIdx];
        
        for(int i = 0; i < ban.length(); i++) {
            if(ban.charAt(i) == '*') continue;
            
            if(ban.charAt(i) != user.charAt(i)) return false;
        }
        
        return true;
    }
    
    private static void permutation(int idx) {
        
        if(idx == bannedId.length) {
            answer++;
            return;
        }
        
        for(int i = 0; i < available[idx].length; i++) {
            
            int id = available[idx][i];

            if(id == -1) break;
            if(selected[id]) continue;  // 이미 제재 아이디에 포함되면 pass
            
            selected[id] = true;
            permutation(idx+1);
            selected[id] = false;
        }
    }
    
    private static int factorial(int num) {
    	int total = 1;
    	for(int i = 2; i <= num; i++) {
    		total *= i;
    	}
    	return total;
    }

}