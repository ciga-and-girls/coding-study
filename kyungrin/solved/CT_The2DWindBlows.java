package kyungrin.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 0이상 9이하의 숫자로만 이루어진 N×M 행렬 모양
 *  Q번의 바람
 *
 *  1. 영역의 경계 : 시계 방향 회전
 *  	- (r1, c1) ~ (r1, c2)
 *  	- (r1+1, c2) ~ (r2-1, c2)
 *  	- (r2, c2) ~ (r2, c1)
 *  	- (r2-1, c1) ~ (r1+1, c1)
 *  	-> 가져와서 1차원 만들고 Shift R
 *  		배열의 크기  : (c2-c1+1)*2 + (r2-c1+1-2)*2
 *  		배열의 인덱스는 int cnt로 관리
 *  	=> 다시 map에 저 순서대로 붙이기
 *  		(c2-c1+1)번,
 *  		(r2-c1+1-2)번,
 *  		이하 동일
 *
 *  2. 각 칸 : 현재 칸에 적혀있는 숫자 + 인접한 곳에 적혀있는 숫자들의 평균치 (*소숫점 버림)
 *  	- (r1, c1) ~ (r2, c2)까지 배열을 생성
 *  	- 원본 기준으로 들고와서 평균 값 기입
 *  	- 원본 <- 복사본
 *
 * **/
public class CT_The2DWindBlows {
  static int N, M, Q;
  static int[][] map;
  //
  static int[][] windAreas;
  static int[] mr = {-1, 1, 0, 0};
  static int[] mc = {0, 0, -1, 1};
  //
  static StringTokenizer st;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    Q = Integer.parseInt(st.nextToken());

    map = new int[N][M];

    for(int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for(int j = 0; j < M; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    windAreas = new int[Q][4];
    for(int i = 0; i < Q; i++) {
      st = new StringTokenizer(br.readLine());
      windAreas[i][0] = Integer.parseInt(st.nextToken())-1;
      windAreas[i][1] = Integer.parseInt(st.nextToken())-1;
      windAreas[i][2] = Integer.parseInt(st.nextToken())-1;
      windAreas[i][3] = Integer.parseInt(st.nextToken())-1;
    }

    for(int t = 0; t < Q; t++) {
      int[] positions = windAreas[t];
      int r1 = positions[0];
      int c1 = positions[1];
      int r2 = positions[2];
      int c2 = positions[3];

      // 1. 회전 시키기 : shift R
      int[] arr = getArr(r1, c1, r2, c2);
      int temp = arr[arr.length-1];
      for(int i = arr.length-1; i >= 1; i--) {
        arr[i] = arr[i-1];
      }
      arr[0] = temp;
      setArr(r1, c1, r2, c2, arr);

      // 2. 평균 구하기
      setAvg(r1, c1, r2, c2);
    }

    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < M; j++) {
        sb.append(map[i][j]).append(" ");
      }
      sb.append("\n");
    }
    System.out.println(sb);
  }


  private static int[] getArr(int r1, int c1, int r2, int c2) {
    int[] arr = new int[(c2-c1+1)*2 + (r2-r1+1-2)*2];

    int idx = 0;

    // (r1, c1) ~ (r1, c2)
    for(int j = c1; j <= c2; j++) {
      arr[idx++] = map[r1][j];
    }

    // (r1+1, c2) ~ (r2-1, c2)
    for(int i = r1+1; i <= r2-1; i++) {

      arr[idx++] = map[i][c2];
    }

    // (r2, c2) ~ (r2, c1)
    for(int j = c2; j >= c1; j--) {
      arr[idx++] = map[r2][j];
    }

    // (r2-1, c1) ~ (r1+1, c1)
    for(int i = r2-1; i >= r1+1; i--) {
      arr[idx++] = map[i][c1];
    }

    return arr;
  }

  private static void setArr(int r1, int c1, int r2, int c2, int[] arr) {

    int idx = 0;

    // (r1, c1) ~ (r1, c2)
    for(int j = c1; j <= c2; j++) {
      map[r1][j] = arr[idx++];
    }

    // (r1+1, c2) ~ (r2-1, c2)
    for(int i = r1+1; i <= r2-1; i++) {
      map[i][c2] = arr[idx++];
    }

    // (r2, c2) ~ (r2, c1)
    for(int j = c2; j >= c1; j--) {
      map[r2][j] = arr[idx++];
    }

    // (r2-1, c1) ~ (r1+1, c1)
    for(int i = r2-1; i >= r1+1; i--) {
      map[i][c1] = arr[idx++];
    }

  }

  private static void setAvg(int r1, int c1, int r2, int c2) {
    // AI 사용 : newMap의 생성과 인덱스 접근의 기준 일치
    int[][] newMap = new int[N][M];

    for(int i = r1; i < r2+1; i++) {
      for(int j = c1; j < c2+1; j++) {

        int sum = map[i][j];
        int cnt = 1;
        for(int d = 0; d < 4; d++) {
          int nr = i + mr[d];
          int nc = j + mc[d];

          if(nr < 0 ||nr >= N||nc < 0||nc >= M) continue;

          cnt++;
          sum += map[nr][nc];
        }

        // AI 사용 : 5 -> 실제 더한 개수
        newMap[i][j] = sum / cnt;
      }
    }

    for(int i = r1; i < r2+1; i++) {
      for(int j = c1; j < c2+1; j++) {
        map[i][j] = newMap[i][j];
      }
    }
  }
}
