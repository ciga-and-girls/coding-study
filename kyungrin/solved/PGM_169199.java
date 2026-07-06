package kyungrin.solved;

import java.util.*;

public class PGM_169199 {
  public int solution(String[] board) {
    int N = board.length; // 행
    int M = board[0].length();
    char[][] map = new char[N][M];
    int[] G = new int[1];
    int[] R = new int[2];
    int r = 0;
    for(String line : board){
      for(int c = 0; c < M; c++){
        map[r][c] = line.charAt(c);
        if(map[r][c] == 'G') G = new int[] {r, c};
        if(map[r][c] == 'R') R = new int[] {r, c};
      }
      r++;
    }

    Queue<int[]> q = new ArrayDeque<>();
    boolean[][] visited = new boolean[N][M];
    q.offer(new int[] {R[0], R[1], 0});
    visited[R[0]][R[1]] = true;

    int[] mr = {-1, 1, 0, 0};
    int[] mc = {0, 0, -1, 1};
    while(!q.isEmpty()){
      int[] now = q.poll();

      for(int d = 0; d < 4; d++){
        int[] last = new int[] {now[0], now[1], now[2]};

        while(true){
          int nextr = last[0] + mr[d];
          int nextc = last[1] + mc[d];

          if(nextr < 0 || nextr >= N || nextc < 0 || nextc >= M) break;
          if(map[nextr][nextc] == 'D') break;

          last = new int[] {nextr, nextc, last[2]};
        }

        if(visited[last[0]][last[1]]) continue;

        if(last[0] == G[0] && last[1] == G[1]) return last[2] + 1;

        q.offer(new int[] {last[0], last[1], last[2] + 1});
        visited[last[0]][last[1]] = true;
      }

    }
    return -1;
  }
}
