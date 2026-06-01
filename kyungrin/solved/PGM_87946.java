package kyungrin.solved;

/** 유저가 탐험할 수 있는 최대 던전 수
 *
 * 피로도 시스템(0 이상의 정수로 표현합니다)
 * 탐험 시작 -> 필요한 "최소 필요 피로도"
 * 탐험을 마쳤을 때 ->  "소모 피로도"
 *
 * 던전 1일 1회, 최대한 많이
 *
 * -------------------------------------
 * Greedy하게 되는 안될듯.
 * - 1 <= k <=5000
 * - dungeons 1이상 8이하
 *  [최소, 소모]
 * - 최소 >= 소모 => 1 이상 1000이하
 *
 * => 순열
 *  순서에 따라서 갈 수 있는 던전의 개수가 달라진다.
 *  1 ~ dgs.legnth 만큼 선택의 기준을 잡아서, 순열로 해당 경우의 수가 가능한지 확인한다.
 *
 * **/
public class PGM_87946 {
  static int K;
  static int[][] dgs;
  static int dgsLength;
  //
  static boolean[] visited;
  //
  static int max;
  public int solution(int k, int[][] dungeons) {
    K = k;
    dgs = dungeons;
    dgsLength = dungeons.length;
    visited = new boolean[dgsLength];

    max = Integer.MIN_VALUE;

    for(int i = 1; i <= dgsLength; i++){
      permutation(0, K, 0, i);
    }

    return max;
  }

  // 던전을 선택하는 기능을 하는 메서드
  // 매개변수 : 선택의 자리 idx, 현재 피로도, 현재까지 선택된 개수
  private static void permutation(int idx, int tired, int selectedCnt, int end){
    if(idx == end){
      max = Math.max(max, selectedCnt);
      return;
    }

    for(int i = 0; i < dgsLength; i++){
      if(visited[i]) continue;
      if(tired >= dgs[i][0]){
        visited[i] = true;
        permutation(idx + 1, tired-dgs[i][1], selectedCnt  + 1, end);
        visited[i] = false;
      }
    }
  }

}
