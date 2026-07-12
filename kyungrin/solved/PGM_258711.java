package kyungrin.solved;
import java.util.*;

public class PGM_258711 {
/** 생성한 정점의 번호, 도넛 모양 그래프의 수, 막대 모양 그래프의 수, 8자 모양 그래프의 수

 도넛 모양 그래프
 n개의 정점과 n개의 간선
 사이클 있는 그래프
 n-1개의 정점들을 한 번씩 방문한 뒤 원래 출발했던 정점으로 돌아오게 됩니다.

 막대 모양 그래프
 n개의 정점과 n-1개의 간선
 나머지 n-1개의 정점을 한 번씩 방문하게 되는 정점이 단 하나 존재

 8자 모양 그래프는
 2n+1개의 정점과 2n+2개의 간선

 정점을 하나 생성한 뒤, 각 도넛 모양 그래프, 막대 모양 그래프, 8자 모양 그래프의 임의의 정점 하나로 향하는 간선들을 연결
 그 후 각 정점에 서로 다른 번호를 매겼습니다.


 생성한 정점의 번호
 정점을 생성하기 전 도넛 모양 그래프의 수, 막대 모양 그래프의 수, 8자 모양 그래프의 수

 ---------------

 인접 리스트 생성

 생성한 정점 : 진입 간선이 없는 정점 + 그래프 수의 합이 2 이상이므로 진출이 2개 이상인 정점
 도넛 모양 그래프 : 싸이클 확인 시...
 막대 모양 그래프 :  ...

 아이디어 AI한테 받아옴
 ;; 진출 차수로 판단하는 거라고 하네요.

 생성 정점을 기준으로
 도넛 모양 그래프 : 모든 정점의 진출차수가 1
 막대 모양 그래프 : 하나의 정점의 진출차수가 0
 8자 모양 그래프 : 하나의 정점의 진출차수가 2


 그럼 인접 리스트 만들고, 만드는 과정에서 진출 간선이랑 진입 간선 기록해두는 INT 배열 만들어 놓고, 그 중에 2 이상 / 0이 있으면 생성 정점으로 기록해두고, 생성 정점에서 진출되고 있는 정점들 싹 들고와서.
 해당 정점들 기준으로 순회하면서 out==2면 바로 8 out==0이면 바로 막대

 **/

  class Solution {
    static ArrayList<Integer>[] adjList;
    static int[] inDegree;
    static int[] outDegree;
    static int created;
    //
    static int donutCnt;
    static int stickCnt;
    static int eightCnt;
    public int[] solution(int[][] edges) {
      adjList = new ArrayList[1_000_001];
      inDegree = new int[1_000_001];
      outDegree = new int[1_000_001];
      int last = 0;

      created = 0;
      donutCnt = 0;
      stickCnt = 0;
      eightCnt = 0;
      //
      for(int[] edge : edges) {
        // a -> b
        int a = edge[0];
        int b = edge[1];
        if (adjList[a] == null) {
          adjList[a] = new ArrayList<>();
        }
        adjList[a].add(b);
        outDegree[a]++;
        inDegree[b]++;

        last = Math.max(last, Math.max(a, b));
      }

      for(int i = 1; i <= last; i++){
        if(inDegree[i] == 0 && outDegree[i] >= 2) {
          created = i;
          break;
        }
      }

      for(int start : adjList[created]){
        int current = start;
        boolean[] visited = new boolean[last + 1];

        while(true){
          visited[current] = true;

          if(outDegree[current] == 2) {
            eightCnt++;
            break;
          }

          if(outDegree[current] == 0) {
            stickCnt++;
            break;
          }

          current = adjList[current].get(0);
          if(visited[current]) {
            donutCnt++;
            break;
          }
        }
      }


      //
      return new int[] {created, donutCnt, stickCnt, eightCnt};
    }
  }
}
