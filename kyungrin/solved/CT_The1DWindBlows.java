package kyungrin.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 0 이상 9이하의 숫자로만 이루어진 N×M 행렬
 * 총 Q번의 바람
 *
 * 바람
 *  -> 특정 행의 모든 원소들을 왼쪽 혹은 오른쪽으로 전부 한 칸씩 밀어 shift 하는 효과
 *
 * 전파
 *  - 단 하나라도 같은 열에 같은 숫자가 적혀있는 경우
 *    -> 현재 행이 밀렸던 방향과 반대 방향으로 작용
 *
 * **/
public class CT_The1DWindBlows {
  static int N, M, Q;
  static int[][] map;
  static int[][] winds; // (r-1, d) -> L:1 R:2
  //
  static StringTokenizer st;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    Q = Integer.parseInt(st.nextToken());
    map = new int[N][M];
    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());
      for(int j = 0; j < M; j++){
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    winds = new int[Q][2];

    // AI 사용 : [0] 으로 전부 받고 있었다.
    for(int i = 0; i < Q; i++){
      st = new StringTokenizer(br.readLine());
      winds[i][0] = Integer.parseInt(st.nextToken()) - 1;
      if(st.nextToken().equals("L")) winds[i][1] = 1;
      else winds[i][1] = 2;
    }
    //

    for(int i = 0; i < Q; i++){
      int row = winds[i][0];
      int dir = winds[i][1];
      recursion(-1, row, dir);
    }

    //
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < N; i++){
      for(int j = 0; j < M; j++){
        sb.append(map[i][j]).append(" ");
      }
      sb.append("\n");
    }
    System.out.println(sb);
  }

  private static void recursion(int preRow, int row, int dir){

    // 1. 대상 행의 움직임
    moveData(row, dir);

    // 2. 위, 아래 전파 여부 확인
    // AI 사용 : 행을 열(M)과 비교
    int nextRow1 = row + 1;
    int nextRow2 = row - 1;
    if(nextRow1 < N && nextRow1 != preRow) {
      if(isSpread(row, nextRow1)) {
        recursion(row, nextRow1, dir == 1 ? 2 : 1);
      }
    }
    if(nextRow2 >= 0 && nextRow2 != preRow) {
      if(isSpread(row, nextRow2)) {
        recursion(row, nextRow2, dir == 1 ? 2 : 1);
      }
    }

  }

  // 기능 : 한 행에 대해 데이터 이동을 실시한다.
  // 매개변수 : 행 번호, L:1 R:2
  private static void moveData(int row, int dir){
    // 1. R
    if(dir == 2){
      int temp = map[row][0];
      for(int i = 1; i < M; i++){
        map[row][i-1] = map[row][i];
      }
      map[row][M-1] = temp;
    }
    // 2. L
    else {
      int temp = map[row][M-1];
      // AI 사용 : 시작 위치를 M-2에서 시작
      for(int i = M-1; i > 0; i--){
        map[row][i] = map[row][i-1];
      }
      map[row][0] = temp;
    }
  }

  // 기능 : 각 열의 값이 동일한지 확인한다.
  // 매개변수 : 비교 행 1, 비교 행 2
  private static boolean isSpread(int num1, int num2){
    for(int i = 0; i < M; i++){
      if(map[num1][i] == map[num2][i]) return true;
    }
    return false;
  }
}
