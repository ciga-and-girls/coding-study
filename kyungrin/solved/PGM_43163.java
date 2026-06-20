package kyungrin.solved;

public class PGM_43163 {
  static String BEGIN;
  static String TARGET;
  static String[] WORDS;
  static int WORDSCNT;
  static int WORDLENGTH;
  //
  static boolean[] visited;
  static int minCnt;
  public int solution(String begin, String target, String[] words) {
    BEGIN = begin;
    TARGET = target;
    WORDS = words;
    WORDSCNT = WORDS.length;
    WORDLENGTH = words[0].length();
    visited = new boolean[WORDSCNT];
    minCnt = Integer.MAX_VALUE;
    // 순열
    // 한글자만 다르면 선택해서 재귀
    // 기저에서 최소 개수 카운트
    permutation(0, 0, BEGIN);

    return minCnt == Integer.MAX_VALUE ? 0 : minCnt;
  }

  private static void permutation(int idx, int cnt, String now){
    if(now.equals(TARGET)) {
      minCnt = Math.min(minCnt, cnt);
      return;
    }
    if(WORDSCNT == idx ) return;


    for(int i = 0; i < WORDSCNT; i++){
      if(visited[i]) continue;

      String candidate = WORDS[i];
      int isSameCnt = 0;
      for(int j = 0; j < WORDLENGTH; j++){
        if(now.charAt(j) == candidate.charAt(j)) isSameCnt++;
      }
      if(isSameCnt != WORDLENGTH - 1) continue;
      visited[i] = true;
      permutation(idx + 1, cnt + 1, candidate);
      visited[i] = false;
    }
  }
}
