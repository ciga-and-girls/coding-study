package kyungrin.solved;

/** 행사 목적을 최대한으로 달성 이모티콘 플러스 서비스 가입 수, 이모티콘 매출액
 * 1. 이모티콘 플러스 서비스 가입자를 최대한 늘리는 것.
 * 2. 이모티콘 판매액을 최대한 늘리는 것.
 *
 * n명의 카카오톡 사용자들에게 이모티콘 m개를 할인하여 판매
 * 할인률 10%, 20%, 30%, 40% 중 하나
 *
 * n명의 사용자
 * 1. 이모티콘을 모두 구매
 * -> 구매 비용의 합이 일정 가격 이상이 된다면,
 * 1. 이모티콘 구매를 모두 취소하고 이모티콘 플러스 서비스에 가입
 *
 * 사용자 : idx, ratio(이상), cost
 * 이모티콘 : idx, cost
 *
 * ------------
 * 각 이모티콘에 대해서 각 사용자들에게 판매할 때는 가장 비싸되 안되면 플러스로 넘어가는 할인 비용을 찾아야 하는듯.
 *
 * 1. 각 이모티콘에 대해 할인률을 적용하기
 *   - 모든 경우의 수
 *   - 중복 할인 가능
 *   - 아... 조합이 아니라 순열이다... 1번 이모티콘에 적용하는 거랑 2번 이모티콘에 적용하는 거랑 완전 다르니까
 *   : 각 이모티콘의 할인률을 결정할 때마다 연쇄적으로 결정되어야 하는 것
 *    - 사용자가 해당 이모티콘을 구매하는가? (해당 할인률을 사용자가 수용하는가)
 *
 * **/
public class PGM_150368 {
  static int[][] USERS;
  static int[] EMOTICONS;
  //
  static int emtCnt;
  static int userCnt;
  static int[] emoticonsSale; // temp, idx: 이모티콘 번호, value : 이모티콘 할인률(1~4)
  //
  static int maxCnt;
  static int maxSale;
  public int[] solution(int[][] users, int[] emoticons) {
    USERS = users;
    EMOTICONS = emoticons;
    emtCnt = EMOTICONS.length;
    userCnt = USERS.length;

    emoticonsSale = new int[emtCnt];
    maxCnt = Integer.MIN_VALUE;
    maxSale = Integer.MIN_VALUE;
    dc(0);

    return new int[]{maxCnt, maxSale};
  }

  // 이모티콘에게 할인을 중복조합으로 배부
  // 매개변수
  // idx: 이모티콘 번호
  // start: 할인률의 시작 번호 (1~4)
  private static void dc(int idx) {
    if(idx == emtCnt){
      int totalPlus = 0;
      int totalCost = 0;

      // 1. 선택된 할인률이 사용자의 할인률 이상인지 확인
      // 10%, 20%, 30%, 40%
      // 1. 유저 별 구매 비용 확인
      for(int i = 0; i < userCnt; i++) {
        int[] info = USERS[i];
        int cost = 0;

        for(int e = 0; e < emtCnt; e++){
          int eRatio = emoticonsSale[e];
          int pRatio = info[0];
          if (eRatio >= pRatio) {
            cost += ((100 - eRatio) * EMOTICONS[e] / 100);
          }
        }
        // 1-1. 각 유저 중 구매 비용이 유저의 기준을 넘으면 -> 이모티콘 플러스 ++
        if (cost >= info[1]) totalPlus++;
        else totalCost += cost;
      }

      // 2. plus 갱신
      if (maxCnt < totalPlus) {
        maxCnt = totalPlus;
        maxSale = totalCost;
      }
      else if (maxCnt == totalPlus){
        // 2-2. plus가 같다면 totalCost 갱신
        maxSale = Math.max(maxSale, totalCost);
      }

      return;
    }

    //
    for(int i = 10; i <= 40; i += 10){
      // 0. emoticonsSale 에 선택된 할인률 부여
      emoticonsSale[idx] = i;
      dc(idx + 1);
    }
  }
}
