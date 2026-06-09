package kyungrin.unsolved;

import java.util.ArrayList;

/** 각 대기실별로 거리두기를 지키고 있으면 1을, 한 명이라도 지키지 않고 있으면 0을 배열
 - 대기실 : 5개(5x5)
 - 응시자 : 맨해튼 거리가 3이상으로만
 - 파티션으로 막혀 있을 경우에는 2이하 허용

 ------------------------------------------
 응시자가 앉아있는 자리(P)
 빈 테이블(O)
 파티션(X)
 ------------------------------------------

 BFS 하나의 경로에 대해 장애물 여부를 확인해야해서 X

 DFS + 맨해튼 거리 이하 인지 체크하여 확인하기.
 A(기준) -> X
 1. 파티션이 있다면 이동 불가능
 2. 도달한다고 하여도 3 이상이면 갱신 없음
 -> 도달했고, 2이하면 갱신

 **/
public class PGM_81302 {

  static int[] mr = {-1, 1, 0, 0};
  static int[] mc = {0, 0, -1, 1};
  static boolean[][] visited;

  public int[] solution(String[][] places) {
    int[] answer = new int[5];

    for(int i = 0; i < 5; i++) {
      boolean isAlright = true;


      // 1. 행렬변환
      String[][] map = new String[5][5];
      ArrayList<int[]> people = new ArrayList<>();
      int idx = 0;
      for(String place : places[i]){
        for(int j = 0; j < 5; j++){
          map[idx][j] = String.valueOf(place.charAt(j));
          if(map[idx][j].equals("P")) people.add(new int[] {idx, j});
        }
        idx++;
      }


      // 2. 기준 응시자로 해당 대기실이 거리를 지키고 있는지 확인
      for(int j = 0; j < people.size(); j++){
        int[] standard = people.get(j);

        for(int k = 0; k < people.size(); k++){
          if(j == k) continue; // 자기자신은 pass
          int[] target = people.get(k);

          visited = new boolean[5][5];
          visited[target[0]][target[1]] = true;
          isAlright = dfs(standard, map, target, 0);
          if(!isAlright){
            break;
          }
        }
      }
      // 3. 기록하기
      answer[i] = isAlright ? 1 : 0;
    }

    return answer;
  }


  private static boolean dfs(int[] now, String[][] map, int[] target, int moveCnt){
    if(moveCnt >= 3) return true; // 가치치기

    //
    if(now[0] == target[0] && now[1] == target[1]){
      if(moveCnt <= 2) {
        return false;
      } else {
        return true;
      }
    }

    //
    for(int d = 0; d < 4; d++){
      int nr = now[0] + mr[d];
      int nc = now[1] + mc[d];

      if(nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;
      if(map[nr][nc].equals("X")) continue;

      visited[nr][nc] = true;

      if( ! dfs(new int[] {nr, nc} , map, target, moveCnt + 1 ) ) return false;

      visited[nr][nc] = false;
    }

    return true;
  }
}
