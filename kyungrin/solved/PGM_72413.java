package kyungrin.solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

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
  static ArrayList<Node>[] adjList;
  static class Node implements Comparable<Node>{
    int to, weight;
    public Node(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }
    @Override
    public int compareTo(Node o) {
      return this.weight - o.weight;
    }
  }
  //
  static int INF = 20_000_001;
  static class Vertex implements Comparable<Vertex>{
    int n, totalWeight;

    public Vertex(int n, int totalWeight) {
      this.n = n;
      this.totalWeight = totalWeight;
    }

    @Override
    public int compareTo(Vertex o) {
      return this.totalWeight - o.totalWeight;
    }
  }
  static int[] distfromS;
  //
  static int minCost;
  public static void main(String[] args) {
  }

  public int solution(int n, int s, int a, int b, int[][] fares) {

    adjList = new ArrayList[n+1];
    for(int i = 1; i < n+1; i++) adjList[i] = new ArrayList<>();

    for(int[] fare: fares){
      int c = fare[0];
      int d = fare[1];
      int f = fare[2];
      adjList[c].add(new Node(d, f));
      adjList[d].add(new Node(c, f));
    }

    // -----------------------------
    /**
     * 다익스트라를 통해 S -> 모든 지점으로 가는 distfromS[]를 제작해 둔다.
     * **/
    distfromS = dijkstra(s, n);
    // (1)
    int value1 = 0;
    value1 = distfromS[a] + distfromS[b];

    // (2)
    int value2 = INF;
    int cost1 = floyd(a, s, b, n, fares);
    int cost2 = floyd(b, s, a, n, fares);
    value2 = Math.min(cost1, cost2);

    // (3)
    int value3 = INF;
    for(int i = 1; i < n+1; i++){
      if(i == s || i == a || i == b) continue;
      int[] temp = dijkstra(i, n);
      value3 = Math.min(value3, distfromS[i] +  temp[a] + temp[b]);
    }

    // -----------------------------
    minCost = Math.min(value1, Math.min(value2, value3));
    return minCost;
  }

  // 1. 일반 다익스트라
  // 매개변수 : 출발지
  // 반환값 : dist[] 배열
  private static int[] dijkstra(int S, int n){
    // 1. dist 배열 생성 및 초기화
    int[] dist = new int[n+1];
    Arrays.fill(dist, INF);
    dist[S] = 0;

    // 2. 우선순위 큐 생성
    PriorityQueue<Vertex> pq = new PriorityQueue();
    pq.add(new Vertex(S, 0));


    // 3. 다익스트라
    while(!pq.isEmpty()){
      Vertex current = pq.poll();

      if(current.totalWeight > dist[current.n]) continue;

      for(Node next : adjList[current.n]){
        int newCost = current.totalWeight + next.weight;
        if(dist[next.to] > newCost){
          dist[next.to] = newCost;
          pq.add(new Vertex(next.to, newCost));
        }
      }
    }

    return dist;
  }

  // 2. 플루이드 워셜
  // 매개변수 : 경유지, 출발지, 도착지
  // 반환값 : dist[][] 배열
  // adjMatrix로 변환 후 dist[][] 초기화
  // 경유지가 K 인 경우, break하여 cost 반환
  private static int floyd(int K, int S, int E, int n, int[][] fares){
    int[][] dist = new int[n+1][n+1];
    for(int i = 0; i < n+1; i++) Arrays.fill(dist[i], INF);
    for(int i = 0; i < n+1; i++) dist[i][i] = 0;
    for(int[] fare : fares){
      int c = fare[0];
      int d = fare[1];
      int f = fare[2];

      dist[c][d] = f;
      dist[d][c] = f;
    }

    for(int k = 1; k < n+1; k++){
      for(int i = 1; i < n+1; i++){
        for(int j = 1; j < n+1; j++){
          if(dist[i][j] > dist[i][k] + dist[k][j]){
            dist[i][j] = dist[i][k] + dist[k][j];
            if(k == K && i == S && j == E) return dist[i][j];
          }
        }
      }
    }

    return INF; // 경유지를 지나쳐 출발지에서 도착지로 갈 수 없다면 MAX_VALUE
  }
}
