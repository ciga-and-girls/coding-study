package kyungrin.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/** 모든 섬을 연결하는 다리 길이의 최솟값
 * N×M 크기
 * 땅이거나 바다
 *
 * 섬은 연결된 땅이 상하좌우로 붙어있는 덩어리
 *
 * 다리 : 연결해서 모든 섬을 연결
 * - 바다에만 건설
 * - 길이는 다리가 격자에서 차지하는 칸의 수
 *     - 길이는 2 이상
 * - 다리의 양 끝은 섬과 인접한 바다 위
 * - 한 다리의 방향이 중간에 바뀌면 안된다.
 * ---------------------------------------
 * [접근법] Flood Fill + MST
 *
 * 1. Flood Fill : 상하좌우로 이어진 섬에 번호를 부여한다.
 * 2. 간선 만들기  :
 *     - "땅"의 좌표에서
 *         - 상, 좌로 뻗는다.
 *             - 범위를 벗어났으면 다리가 될 수 없다.
 *             - 땅을 만났다면 다리가 될 수 있다.
 *                 - edgeList <- (출발 땅의 번호, 도착 땅의 번호, 다리의 길이)
 * 3. 크루스칼 : 모든 섬이 연결되어 있는지 확인한다.
 *     - 간선 정렬
 *     - 각 간선 마다 합집합 시도
 *     - 합집합이 가능했고, 됐다면 -> 해당 가중치를 결과에 더하기
 *     - 선택한 간선의 개수가 섬의 개수 - 1이면 STOP
 * **/
public class BOJ_17472 {
  static int N, M;
  static int[][] map;
  //
  static int[] mr = {-1, 0, 1, 0};
  static int[] mc = {0, -1, 0, 1};
  static int islandNum = 0;

  static class Edge implements Comparable<Edge>{
    int start, end;
    int len;
    public Edge(int start, int end, int len) {
      super();
      this.start = start;
      this.end = end;
      this.len = len;
    }
    @Override
    public int compareTo(Edge o) {
      return this.len - o.len;
    }
  }
  static ArrayList<Edge> edgeList;

  static int[] parents; // 1-based
  //
  static int minLen;
  //
  static StringTokenizer st;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    map = new int[N][M];
    for(int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for(int j = 0; j < M; j++) {
        map[i][j] = Integer.parseInt(st.nextToken()) == 1 ? -1 : 0;
      }
    }

    // 1. 섬 라벨링하기
    //
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < M; j++) {
        if(map[i][j] != -1) continue;

        islandNum++;
        labeling(i, j);
      }
    }

    // 2. 간선 만들기
    edgeList = new ArrayList<>();
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < M; j++) {
        if(map[i][j] == 0) continue;

        getEdge(i, j);
      }
    }

    // 3. 크루스칼
    minLen = getLen();
    System.out.println(minLen);
  }

  private static void labeling(int r, int c) {
    map[r][c] = islandNum;

    for(int d = 0; d < 4; d++) {
      int nr = r + mr[d];
      int nc = c + mc[d];

      if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
      // 바다이거나, 다른 섬이거나를 거르기 위해서는 아직 번호가 선정되지 않은 부분만
      if(map[nr][nc] != -1) continue;

      labeling(nr, nc);
    }
  }

  private static void getEdge(int sr, int sc) {
    for(int d = 0; d < 2; d++) {
      int er = sr;
      int ec = sc;

      int len = 0;
      while(true) {
        er += mr[d];
        ec += mc[d];

        if(er < 0 || ec < 0) break;
        if(map[er][ec] == 0) len++;
        else if(map[er][ec] > 0) {
          if(len >= 2) {
            edgeList.add(new Edge(map[sr][sc], map[er][ec], len));
          }
          break;
        }
      }
    }

  }

  private static int getLen() {
    parents = new int[islandNum + 1];
    for(int i = 1; i < islandNum + 1; i++) {
      parents[i] = i;
    }

    Collections.sort(edgeList);

    int totalLen = 0;
    int mst = 0;
    for(Edge edge : edgeList) {
      if(!union(edge.start, edge.end)) continue;
      totalLen += edge.len;
      mst++;
      if(mst == islandNum - 1) {
        return totalLen;
      }
    }

    return -1;
  }

  private static int find(int x) {
    if(parents[x] == x) return x;
    return parents[x] = find(parents[x]);
  }

  private static boolean union(int a, int b) {
    int ap = find(a);
    int bp = find(b);
    if(ap == bp) return false;
    parents[bp] = ap;
    return true;
  }
}

