package kyungrin.unsolved;

/** 라이언이 가장 큰 점수 차이로 우승하기 위해 n발의 화살을 어떤 과녁 점수에 맞혀야 하는지 정수 배열에 담아 return
 * 전 대회 우승자인 라이언에게 불리
 *
 * N발 씩 : 어 > 라
 *
 * K점에 대하여
 * - A > B 면 어
 * - B > A 면 라
 * - A == B면 어
 * - A = B = 0이면 X
 *
 * 우승자
 *  최종 점수가 높은 선수
 *  같다면 어파치
 *
 * 라이언은 어피치를 가장 큰 점수 차이로 이기기 위해서
 * n발의 화살을 어떤 과녁 점수에 맞혀야 하는지를 구하려고 합니다.
 *
 * 라이언이 우승할 수 없는 경우(무조건 지거나 비기는 경우)는 [-1]을 return
 * ---
 * 입력값 주의점
 *  각 배열의 idx 의미 : 10-idx 점수
 *  ---
 *  라이언이 어파치보다 1발이라도 더 맞췄으면 들고갈 수 있는거니까
 *  평범하게 생각하자면 우선
 *  그리디하게 생각했을 때
 *  반례가 있네 어파치가 10점을 가져가게 두더라도 더 큰 점수 차이로 이길 수 있는 방법이...
 *
 *
 *  10~0 점수에 대한 get 여부를 정할 때
 *  1. 남은 화살이 었어야 한다.
 *  2. 어파치 info와 비교하여 그 이상 쏠 수 있어야 한다.
 *  3. get 여부를 전부 정했다면, 기저에서 각 점수를 계산한 후 비교하여 차이가 크다면 변경
 *
 * **/
public class PGM_92342 {
  static int N;
  static int[] apachInfo;
  //
  static int[] tempLionInfo;
  static boolean[] lionGet;
  //
  static int diff;
  static int[] lionInfo;

  public int[] solution(int n, int[] info) {
    N = n;
    apachInfo = info;

    tempLionInfo = new int[11];
    lionGet = new boolean[11];

    diff = Integer.MIN_VALUE;
    lionInfo = new int[11];

    // 점수 차이를 구하는 부분집합
    subset(10, N);

    return lionInfo;
  }

  // 역할 : 라이온의 점수-화살 배열을 갱신한다.
  // 매개변수 : 현재 점수 판의 점수
  private static void subset(int score, int remainArrowCnt){
    if(score < 0) {
      // 1. lionGet 기준으로 어파치와 라이온의 점수 계산
      int apachScore = 0;
      int lionScore = 0;
      for(int i = 0; i <= 10; i++){
        if(lionGet[i]) lionScore += (10-i);
        else apachScore += (10-i);
      }

      if(apachScore > lionScore) return;

      // 2. diff가 이전 까지의 diff 보다 크다면 갱신
      int tempDiff = lionScore - apachScore;
      if(tempDiff > diff) {
        diff = tempDiff;
        lionInfo = tempLionInfo;
      }
      // 3. 같다면 lionInfo가 tempLionInfo 점수 idx를 뒤부터 비교하면서 값이 더 큰 쪽으로 변경
      else if(tempDiff == diff){
        for(int i = 10; i >= 0; i--){
          if(tempLionInfo[i] > lionInfo[i]){
            lionInfo = tempLionInfo;
            break;
          }
        }
      }
      // 남은 화살이 있다면 0번 점수판에 넣는다.
      if(remainArrowCnt != 0) {
        lionInfo[10] = remainArrowCnt;
      }
      return;
    }

    // 2. 어파치info의 값보다 화살이 더 많다면
    if(apachInfo[score] > remainArrowCnt) {
      // -> 1. tempLionInfo[score] 에 화살 수 배치
      tempLionInfo[score] = apachInfo[score] + 1;
      // -> 2. lionGet[score] -> 체크
      lionGet[score] = true;
      // 재귀
      subset(score-1, remainArrowCnt - (apachInfo[score] + 1));
    }


    // 재귀 (선택하지 않음)
    tempLionInfo[score] = 0;
    lionGet[score] = false;
    subset(score-1, remainArrowCnt);
  }
}
