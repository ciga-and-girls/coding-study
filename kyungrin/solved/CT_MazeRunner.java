package kyungrin.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/** K초 or K초 전에 모든 참가자가 탈출에 성공 -> 모든 참가자들의 이동 거리 합과 출구 좌표를 출력
 * 미로
 *  - N×N 크기의 격자
 *  - 좌상단은 (1,1)
 *
 *   > 빈 칸
 *   이동 가능한 칸
 *   > 벽
 *   이동할 수 없는 칸
 *   1이상 9이하의 내구도
 *   (회전)할 때, 내구도가 1씩 깎입니다.
 *    0이 되면, 빈 칸으로 변
 *   > 출구
 *   칸에 도달하면, 즉시 탈출
 *
 *  참가자
 *  - 1초마다 모든 참가자는 한 칸씩
 *    * 모든 참가자는 동시에 움직
 *    - 움직일 수 없는 상황이라면, 움직이지 않습니다.
 *  - 최단거리 : ∣x1−x2∣+∣y1−y2∣
 *    - 움직인 칸은 현재 머물러 있던 칸보다 출구까지의 최단 거리가 가까워야 합니다
 *    - 같은 최단거리 있을 시 상하로 움직이는 것을 우선시
 *  - 상하좌우, 벽이 없는 곳
 *  - 한 칸에 2명 이상의 참가자가 있을 수 있습니다.
 *
 * 미로의 회전
 *  - 한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형
 *      => exit을 중앙에 두면 가장 작은 정사각형이 되기 힘듦.
 *      -> 2*2.. 부터해서 좌상 우상 좌하 좌하 순으로 가면될듯.
 *  - 같은 크기 : r 좌표가 작은 것이 우선되고, 그래도 같으면 c 좌표가 작은 것이 우선
 *  - 시계방향으로 90도 회전
 *  - 회전된 벽은 내구도가 1씩 깎입니다.
 *
 *  참가자 이동 -> 미로 회전
 * **/
public class CT_MazeRunner {
  static int N, M, K;
  static int[][] miro;
  static int[][] people; // 사람 수, (좌표)
  //
  static int[] mr = {-1, 1, 0, 0};
  static int[] mc = {0, 0, -1, 1};
  //
  static int totalMoveCnt;
  static int[] exit;
  //
  static StringTokenizer st;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    miro = new int[N][N];

    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());
      for(int j = 0; j < N; j++) {
        miro[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    people = new int[M][2];
    for(int i = 0; i < M; i++){
      st = new StringTokenizer(br.readLine());
      int r = Integer.parseInt(st.nextToken()) -1;
      int c = Integer.parseInt(st.nextToken()) -1;
      people[i] = new int[] {r, c};
    }
    st = new StringTokenizer(br.readLine());
    int r = Integer.parseInt(st.nextToken()) -1;
    int c = Integer.parseInt(st.nextToken()) -1;
    exit = new int[]{r, c};

    totalMoveCnt = 0;

    while(K > 0) {
      boolean allEscaped = true;
      for(int i = 0; i < M; i++){
        if(people[i][0] != -1) {
          allEscaped = false;
          break;
        }
      }
      if(allEscaped) break;

      K--;
      peopleMoving();
      miroRotation();
    }

    StringBuilder sb = new StringBuilder();
    sb.append(totalMoveCnt).append("\n");
    sb.append(exit[0] + 1).append(" ").append(exit[1] + 1);
    System.out.println(sb);
  }

  // 1. 참가자들이 이동하는 로직
  // 참가자의 사방에 대하여 매해튼 거리 계산.
  // 가장 짧은 거리로 이동.
  // ** 사방에 벽이 존재하면 이동하지 않는다. **
  private static void peopleMoving() {
    for(int i = 0; i < M; i++) {
      int[] person = people[i];
      if (person[0] == -1) continue;

      int fNextr = person[0];
      int fNextc = person[1];
      // AI 사용 : 현재 위치에서 가까워지는지 확인하기 위해
      int fMoveCnt = Math.abs(exit[0] - person[0]) + Math.abs(exit[1] - person[1]);
      boolean isMoved = false;

      for(int d = 0; d < 4; d++){
        int nr = person[0] + mr[d];
        int nc = person[1] + mc[d];

        if(nr < 0 || nr >= N || nc < 0 || nc >= N ) continue;
        if(miro[nr][nc] > 0) continue;
        int moveCnt = Math.abs(exit[0] - nr) + Math.abs(exit[1] - nc);

        if(moveCnt < fMoveCnt){
          fNextr = nr;
          fNextc = nc;
          fMoveCnt = moveCnt;
          isMoved = true;
        }
      }

      if(isMoved){
        totalMoveCnt++;
        if(fNextr == exit[0] && fNextc == exit[1]) {
          people[i][0] = -1;
        } else {
          people[i] = new int[]{fNextr, fNextc};
        }
      }
    }
  }

  // 2. 미로의 회전을 담당하는 로직
  private static void miroRotation() {
    int[] range = getRange();
    int sqr = range[0];
    int sqc = range[1];
    int size = range[2];

    rotation(sqr, sqc, size);
  }

  /**
   * 로직 아이디어 AI 사용
   * (처음생각)인덱싱 -> (처음부터 끝까지 찾기 )실제 범위 찾기
   * **/
  // 3. 정사각형 범위를 잡는 로직
  // return : 좌상단 r, c, 정사각형 크기
  private static int[] getRange(){
    int sqr = 0;
    int sqc = 0;
    int size = 0;

    Search:
    for(int s = 2; s <= N; s++){
      for(int r = 0; r <= N-s; r++){
        for(int c = 0; c <= N-s; c++){
          // 출구 유무
          if(
              !(exit[0] >= r && exit[0] < r + s &&
                  exit[1] >= c && exit[1] < c + s)
          ) continue;

          boolean hasPerson = false;
          for(int i = 0; i < M; i++){
            if(people[i][0] == -1) continue;

            if(
                people[i][0] >= r && people[i][0] < r + s &&
                    people[i][1] >= c && people[i][1] < c + s
            ){
              hasPerson = true;
              break;
            }
          }

          if(hasPerson) {
            sqr = r;
            sqc = c;
            size = s;
            break Search;
          }
        }
      }
    }

    return new int[] {sqr, sqc, size};
  }


  // 4. 회전
  /**
   * 로직 아이디어 AI 사용
   * 좌상단 (0, 0)일 때 r->c, c-> s-1-r 규칙 사용하기
   * **/
  private static void rotation(int sqr, int sqc, int size){
    // 1. 미로 회전
    int[][] temp = new int[size][size];

    for(int r = 0; r < size; r++){
      for(int c = 0; c < size; c++) {
        temp[c][size-1-r] = miro[r + sqr][c + sqc];
      }
    }

    for(int r = 0; r < size; r++){
      for(int c = 0; c < size; c++) {
        int val = temp[r][c];
        if(val > 0) val--;
        miro[sqr + r][sqc + c] = val;
      }
    }

    // 2. 참가자 좌표 회전
    for(int i = 0; i < M; i++) {
      int pr = people[i][0];
      int pc = people[i][1];

      if (pr == -1) continue;

      if(pr >= sqr && pr < sqr + size && pc >= sqc && pc < sqc + size) {
        int localR = pr - sqr;
        int localC = pc - sqc;

        int nlr = localC;
        int nlc = size -1 - localR;

        people[i][0] = sqr + nlr;
        people[i][1] = sqc + nlc;
      }
    }

    // 3. 출구 좌표 회전
    int er = exit[0];
    int ec = exit[1];

    if(er >= sqr && er < sqr + size && ec >= sqc && ec < sqc + size) {
      int localR = er - sqr;
      int localC = ec - sqc;

      int nlr = localC;
      int nlc = size -1 - localR;

      exit[0] = sqr + nlr;
      exit[1] = sqc + nlc;
    }
  }
}
