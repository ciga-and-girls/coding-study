package kyungrin.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
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

  static int[] mr = { 0, -1, 1, 0, 0 }; // [1:상, 2:하, 3:좌, 4:우]

  static int[] mc = { 0, 0, 0, -1, 1 };

  static boolean[][] visited;

  static int visitedCnt;

  static int mustVisitedCnt;

  static boolean isEnd;

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
    mustVisitedCnt = 0;
    map = new int[N+1][N+1];
    for (int i = 1; i < N+1; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 1; j < N+1; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
        if (map[i][j] == 0)
          mustVisitedCnt++;
      }
    }

    sb = new StringBuilder();
    visited = new boolean[N+1][N+1];
    visited[r][c] = true;
    visitedCnt = 1;
    sb.append(r).append(" ").append(c).append("\n");

    isEnd = false;

    while (!isEnd) {
      if (adjAdventure()) continue; // 1단계 성공 시 다시 1단계부터

      int[] target = getPosition();
      if (target == null) { // 더 이상 미방문 바다가 없다면 종료
        isEnd = true;
        break;
      }
      moveNearSea(target);
    }
    System.out.println(sb);
  }


  // 1. 인접 탐험
  // 현재 위치에서 상하좌우로 인접한 칸 && 아직 방문하지 않은 바다 칸으로 이동하며 sb에 기록한다.
  // return : 1단계 가능 여부 false of true
  // d [1:상, 2:하, 3:좌, 4:우]
  // * 1-1. 현재 바라보는 방향으로 직진
  // * 1-2. 좌회전(90도 반시계 방향 회전) 후 직진
  // * 1-3. 우회전(90도 시계 방향 회전) 후 직진
  // * 1-4. 180도 회전 후 직진
  private static boolean adjAdventure() {

    // 1. 현재 방향을 기준으로 우선순위 순서대로 시도할 방향
    int[] priorityDirs = getPriority(d);

    // 2. 우선 순위대로 진행
    for(int nextDir : priorityDirs) {
      int nr = r + mr[nextDir];
      int nc = c + mc[nextDir];

      if(nr >= 1 && nr < N+1 && nc >= 1 && nc < N+1
          && map[nr][nc] == 0
          && !visited[nr][nc]
      ) {
        r = nr;
        c = nc;
        d = nextDir;
        visited[nr][nc] = true;
        visitedCnt++;
        sb.append(nr).append(" ").append(nc).append("\n");;

        if (visitedCnt == mustVisitedCnt) isEnd = true;
        return true; // 한 칸 이동 성공 시 1 단계 종료
      }
    }

    return false;
  }

  private static int[] getPriority(int curd) {
    if(curd == 1) return new int[] {1, 3, 4, 2};
    if(curd == 2) return new int[] {2, 4, 3, 1};
    if(curd == 3) return new int[] {3, 2, 1, 4};
    if(curd == 4) return new int[] {4, 1, 2, 3};
    return new int[] {1, 2, 3, 4};
  }


  // 2. BFS : 이동할 칸을 선정한다.
  // 아직 방문하지 않은 바다 칸 중 현재 위치에서 가장 가까운 칸
  // 암초는 지나갈 수 없지만,(방해물 -> bfs) 이미 방문한 바다는 지나갈 수 있습니다.
  // return : 이동할 칸의 좌표
  private static int[] getPosition() {

    boolean[][] bfsVisited = new boolean[N+1][N+1];

    Queue<int[]> q = new ArrayDeque<>();
    q.offer(new int[] { r, c });
    bfsVisited[r][c] = true;

    int[] target;
    ArrayList<int[]> canMoves = new ArrayList<>();

    while (!q.isEmpty()) {
      int size = q.size();

      for(int i = 0; i < size; i++) {
        int[] cur = q.poll();

        for (int dir = 1; dir < 5; dir++) {
          int nr = cur[0] + mr[dir];
          int nc = cur[1] + mc[dir];

          if (nr < 1 || nr > N || nc < 1 || nc > N) continue;
          if (map[nr][nc] == 1) continue;
          if (bfsVisited[nr][nc]) continue;

          if (!visited[nr][nc]) canMoves.add(new int[] { nr, nc });

          bfsVisited[nr][nc] = true;
          q.offer(new int[] {nr, nc});
        }
      }
      if (!canMoves.isEmpty()) break;
    }


    if (canMoves.isEmpty()) return null;

    // 여러 개 일 시 : 행 번호가 작은 -> 열 번호가 작은.

    target = canMoves.get(0);
    for (int[] move : canMoves) {
      if (target[0] > move[0]) {
        target = move;
      } else if (target[0] == move[0]) {
        if (target[1] > move[1]) {
          target = move;
        }
      }
    }
    return target;
  }

  // 3. 가장 가까운 바다로 이동 : 인접한 칸의 좌하우상 순서대로 이동하며 sb에 기록한다.
  // 선택한 칸까지의 거리가 1 줄어드는 인접한 칸 중 하나
  // 암초는 지나갈 수 없지만,(방해물 -> bfs) 이미 방문한 바다는 지나갈 수 있다.
  // 매개변수 : 이동할 칸의 좌표
  // return void
  private static void moveNearSea(int[] target) {
    int[][] distMap = getDistMap(target);

    while(r != target[0] || c != target[1]) {
      int[] priority = {3, 2, 4, 1};

      for(int dir : priority) {
        int nr = r + mr[dir];
        int nc = c + mc[dir];

        if(nr < 1 || nr > N || nc < 1 || nc > N || map[nr][nc] == 1) continue;

        if(distMap[nr][nc] == distMap[r][c] - 1) {
          r = nr;
          c = nc;
          d = dir;


          if(!visited[r][c]) {
            visited[r][c] = true;
            visitedCnt++;
            sb.append(r).append(" ").append(c).append("\n");
            if(visitedCnt == mustVisitedCnt) isEnd = true;
          }
          break;
        }
      }
    }
  }


  private static int[][] getDistMap(int[] target) {
    int[][] distMap = new int[N+1][N+1];
    for(int i = 0; i <= N; i++) Arrays.fill(distMap[i], -1);

    Queue<int[]> q = new ArrayDeque<>();

    q.offer(new int[] {target[0], target[1]});
    distMap[target[0]][target[1]] = 0;
    while(!q.isEmpty()) {
      int[] cur = q.poll();

      for(int dir = 1; dir < 5; dir++) {
        int nr = cur[0] + mr[dir];
        int nc = cur[1] + mc[dir];

        if(nr < 1 || nr > N || nc < 1 || nc > N || map[nr][nc] == 1 || distMap[nr][nc] != -1) continue;

        distMap[nr][nc] = distMap[cur[0]][cur[1]] + 1;
        q.offer(new int[] {nr, nc});
      }
    }
    return distMap;
  }

}
