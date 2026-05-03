package kyungrin.unsolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/** 헤엄칠 수 있는 모든 바다를 방문, 아기 고래가 방문하는 바다 칸의 위치를 방문 순서대로 출력
 * N×N 크기의 격자
 * i행 j열의 칸을 (i,j)
 *
 * 각 칸은 헤엄칠 수 있는 바다(0)이거나, 지나갈 수 없는 암초(1)
 *
 * 아기 고래의 출발 : (r, c)
 * 아기 고래의 방향 : d [1:상, 2:하, 3:좌, 4:우]
 *
 * --------------------------------------------------------
 * 1단계: 인접 탐험
 * 현재 위치에서 상하좌우로 인접한 칸 && 아직 방문하지 않은 바다 칸
 * ->
 * 1-1. 현재 바라보는 방향으로 직진
 * 1-2. 좌회전(90도 반시계 방향 회전) 후 직진
 * 1-3. 우회전(90도 시계 방향 회전) 후 직진
 * 1-4. 180도 회전 후 직진
 *
 * 칸을 이동할 때 "그 칸을 본다고" 생각하고 회전해야 한다.
 * -------------------------------------------------------
 * 2단계: 가장 가까운 바다로 이동
 * !! 선제 조건 : 1단계가 불가능하다면 !!
 *
 * 아직 방문하지 않은 바다 칸 중 현재 위치에서 가장 가까운 칸
 *  <가장 가까운 것> : 최소 이동 횟수입니다. [BFS] 로 아직 방문하지 않은 칸 찾기
 *     암초는 지나갈 수 없지만,(방해물 -> bfs) 이미 방문한 바다는 지나갈 수 있습니다.
 *    - 여러 개 일 시 : 행 번호가 작은 -> 열 번호가 작은.
 *
 * 선택한 칸까지 최단 거리로 이동합니다.
 * 매 이동마다 선택한 칸까지의 거리가 1 줄어드는 인접한 칸 중 하나로 이동하며,
 * 그러한 칸이 여러 개라면 좌, 하, 우, 상 순서의 우선순위로 선택
 *
 * 특이사항 : 도착 후 바라보는 방향은 마지막 이동 방향으로 갱신
 * **/
public class CT_BabyWhaleFirstVoyage {
  static int N, r, c, d;
  static int[][] map;
  //
  static int[] mr = {-1, 1, 0, 0};
  static int[] mc = {0, 0, -1, 1};
  static boolean[][] visited;
  static boolean end;
  //
  static StringTokenizer st;
  static StringBuilder sb;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    r = Integer.parseInt(st.nextToken());
    c = Integer.parseInt(st.nextToken());
    d = Integer.parseInt(st.nextToken());

    map = new int[N][N];
    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());
      for(int j = 0; j < N; j++){
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    sb = new StringBuilder();
    visited = new boolean[N][N];
    end = true;

    while(end){
      // 1. 인접 탐험
      boolean adjAdventureIsPossible = adjAdventure();

      if(!adjAdventureIsPossible) {
        // 2.
        int[] target = getPosition();
        // 3.
        moveNearSea(target);
      }
    }

    System.out.println(sb);
  }

  // 1. 인접 탐험
  // 현재 위치에서 상하좌우로 인접한 칸 && 아직 방문하지 않은 바다 칸으로 이동하며 sb에 기록한다.
  // return : 1단계 가능 여부 false of true
  private static boolean adjAdventure(){
    return false;
  }


  // 2. BFS : 이동할 칸을 선정한다.
  // return : 이동할 칸의 좌표
  private static int[] getPosition(){
    return new int[] {};
  }

  // 3. 가장 가까운 바다로 이동 : 인접한 칸의 좌하우상 순서대로 이동하며 sb에 기록한다.
  // 암초는 지나갈 수 없지만,(방해물 -> bfs) 이미 방문한 바다는 지나갈 수 있다.
  // 매개변수 : 이동할 칸의 좌표
  // return void
  private static void moveNearSea(int[] target){
  }

}
