package kyungrin.solved;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * 각 대기실별로 거리두기를 지키고 있으면 1을, 한 명이라도 지키지 않고 있으면 0을 배열 - 대기실 : 5개(5x5) - 응시자 : 맨해튼 거리가 3이상으로만 - 파티션으로
 * 막혀 있을 경우에는 2이하 허용
 * <p>
 * ------------------------------------------ 응시자가 앉아있는 자리(P) 빈 테이블(O) 파티션(X)
 * ------------------------------------------
 * <p>
 * BFS 하나의 경로에 대해 장애물 여부를 확인해야해서 X
 * <p>
 * DFS + 맨해튼 거리 이하 인지 체크하여 확인하기. A(기준) -> X 1. 파티션이 있다면 이동 불가능 2. 도달한다고 하여도 3 이상이면 갱신 없음 -> 도달했고,
 * 2이하면 갱신
 **/
public class PGM_81302 {

  String[][] map;
  static int[] mr = {-1, 1, 0, 0};
  static int[] mc = {0, 0, -1, 1};

  public int[] solution(String[][] places) {
    int[] answer = new int[5];

    for (int i = 0; i < 5; i++) {
      boolean isAlright = true;

      // 1. 행렬변환
      map = new String[5][5];
      ArrayList<int[]> people = new ArrayList<>();
      int idx = 0;
      for (String place : places[i]) {
        for (int j = 0; j < 5; j++) {
          map[idx][j] = String.valueOf(place.charAt(j));
          if (map[idx][j].equals("P")) {
            people.add(new int[]{idx, j});
          }
        }
        idx++;
      }

      // 2. 기준 응시자로 해당 대기실이 거리를 지키고 있는지 확인
      for (int j = 0; j < people.size(); j++) {
        int[] standard = people.get(j);

        isAlright = bfs(standard);
        if (!isAlright) {
          break;
        }
      }
      // 3. 기록하기
      answer[i] = isAlright ? 1 : 0;
    }
    return answer;
  }

  private boolean bfs(int[] standard) {
    Queue<int[]> q = new ArrayDeque<>();
    boolean[][] visited = new boolean[5][5];

    q.add(new int[] {standard[0], standard[1], 0});
    visited[standard[0]][standard[1]] = true;

    while(!q.isEmpty()){
        int[] now = q.poll();

        for (int d = 0; d < 4; d++) {
          int nr = now[0] + mr[d];
          int nc = now[1] + mc[d];
          int ndist = now[2] + 1;

          if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5)
            continue;
          if (visited[nr][nc])
            continue;
          if (map[nr][nc].equals("X"))
            continue;
          if (map[nr][nc].equals("P") && ndist <= 2)
            return false;

          q.offer(new int[]{nr, nc, ndist});
          visited[nr][nc] = true;
        }
    }
    return true;
  }
}
