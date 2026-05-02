package kyungrin.unsolved;

/** 두 사람이 모두 귀가하는 데 소요되는 예상 최저 택시요금
 출발지 S
 도착지 A, B

 지점 번호 : 1부터 n
 가중치 : 택시요금
 무방향 그래프

 특이점
 - 아예 합승하지 않기, 합승하기, 합승하다가 중간에 째지기 전부 가능

 복잡도
 - 2 이상 n x (n-1) / 2 => 밀접 그래프 가능성

 그외 제한사항
 - 도착 지점들 : 1 이상 n 이하인 자연수이며, 각기 서로 다른 값
 - 요금 f: 1 이상 100,000 이하

 -----------------------------------------------------------------------------------------

 1. 합승 X -> 일반 다익스트라
 2. 합승 O -> 플루이드 워셜
  S -> A -> B, S -> B -> A
 3. 합승하다가 째질 경우
  ( S -> 모든 지점 위치 ) 만들어둔 거에서 들고오기 => (1)
  * 갈라설 지점 위치를 S로 하여 A, B에 대한 (1) 하기

 세 가지 경우를 비교한 최소 비용을 return

 !! 구현 전 방향성이 맞는지 AI를 통해 확인 !! : 맞음
  * **/
public class PGM_72413 {

  //
  static int[] distfromS;
  //
  static int minCost;
  public static void main(String[] args) {
  }

  public int solution(int n, int s, int a, int b, int[][] fares) {
    // -----------------------------

    /**
     * 다익스트라를 통해 S -> 모든 지점으로 가는 distfromS[]를 제작해 둔다.
     * **/
    distfromS = dijkstra(s);
    // (1)
    int value1 = 0;
    value1 = distfromS[a] + distfromS[b];

    // (2)
    int value2 = 0;
    int cost1 = floyd(a, s, b);
    int cost2 = floyd(b, s, a);
    value2 = Math.min(cost1, cost2);

    // (3)
    int value3 = 0;
    for(int i = 1; i < n+1; i++){

    }

    // -----------------------------

    int value = value1 + value2 + value3;
    return value;
  }

  // 1. 일반 다익스트라
  // 매개변수 : 출발지
  // 반환값 : dist[] 배열
  private static int[] dijkstra(int S){
    return new int[] {0};
  }

  // 2. 플루이드 워셜
  // 매개변수 : 경유지, 출발지, 도착지
  // 반환값 : dist[][] 배열
  // adjMatrix로 변환 후 dist[][] 초기화
  // 경유지가 K 인 경우, break하여 cost 반환
  private static int floyd(int K, int S, int E){
    return 0;
  }
}
