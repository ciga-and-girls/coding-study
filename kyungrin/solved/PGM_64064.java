package kyungrin.solved;

import java.util.HashSet;
import java.util.Set;

/**
 * 불량 사용자 : 당첨 처리 시 제외 -> 제재 아이디
 *
 * 일부 문자를 '*' 문자로 가려서 전달
 *  - 가리고자 하는 문자 하나에 '*' 문자 하나
 *  - 아이디 당 최소 하나 이상의 '*' 문자
 *
 * 하나의 불량 아이디에 대해서 하나의 제재 아이디가 생긴다.
 * -------------------------------
 *
 * 조합이 아닌 순열 문제.
 * 각 banned의 자리마다 요구하는 조건이 다르다.
 *
 * 조합은 인덱스를 기준으로 삼아, 이전 것은 보지 않고 앞으로만 전진하는 방식으로 구현되기 떄문에
 * 해당 문제를 조합으로 풀기에는 불편함이 있다.
 *
 * [AI 사용 아이디어]
 * 매칭 함수 + 순열 + SET
 *
 * idx번 째 banned_id에 대해서
 * N번 째 user_id를 선택한다.
 *
 * 매칭 함수를 통해 조건을 거른다.
 * **/
public class PGM_64064 {
  static int banned_cnt;
  static int user_cnt;
  static String[] userIds;
  static String[] bannedIds;
  //
  static boolean[] selected;
  static Set<String> set;

  public static void main(String[] args) {

  }
  //
  public int solution(String[] user_id, String[] banned_id) {
    int answer = 0;
    banned_cnt = banned_id.length;
    user_cnt = user_id.length;
    userIds = user_id;
    bannedIds = banned_id;

    selected = new boolean[user_cnt];
    set = new HashSet<>();

    // 순열 + SET으로 가능한 경우의 수를 저장
    permutation(0);

    // answer 내부 값 개수가 경우의 수
    answer = set.size();

    return answer;
  }

  // permutation
  // 매개변수 : 현재 banned_id의 idx
  // 현재 banned_id에 대해서 user_id를 선정하고, 기저에서 해당 결과를 배정한다.
  private static void permutation(int idx){
    if(idx == banned_cnt){
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < user_cnt; i++){
        if(selected[i]){
          sb.append(userIds[i]);
        }
      }

      set.add(sb.toString());

      return;
    }

    for(int i = 0; i < user_cnt; i++){
      if(selected[i]) continue;
      if(!isBanned(i, idx)) continue;

      selected[i] = true;
      permutation(idx + 1);
      selected[i] = false;
    }
  }

  // 해당 아이디가 banned 되었는지 확인한다.
  // 매개변수 : user_id, banned_id의 인덱스 번호
  // 반환값 : banned 여부
  private static boolean isBanned(int user_idx, int banned_idx){
    String user = userIds[user_idx];
    String bannedPattern = bannedIds[banned_idx];

    if(user.length() != bannedPattern.length()) return false;
    for(int i = 0; i < bannedPattern.length(); i++){
      if(bannedPattern.charAt(i) == '*') continue;
      if(bannedPattern.charAt(i) != user.charAt(i)) return false;
    }

    return true;
  }
}
