package kyungrin.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * n×n 격자판
 * m개의 몬스터 & 하나의 전투로봇
 * 모두 자연수인 레벨
 *
 *
 * 몬스터 (1~6 => 레벨)
 *  - 한 칸에는 몬스터가 최대 하나
 *
 *  전투로봇 (9)
 *   - 레벨
 *      - 초기 레벨 2
 *      - 본인의 레벨과 같은 수의 몬스터를 없앨 때마다 레벨 상승
 *   - 움직이기
 *      - 1초 마다 상하좌우로 인접한 한 칸씩 이동
 *      - 레벨보다 큰 몬스터 칸 X
 *      - 낮은 몬스터만 없앨 수 있습니다
 *   - 몬스터 죽이기
 *      - 죽일 수 있어야 함
 *      - 여러 마리 => 거리가 가장 가까운 몬스터
 *        - 거리가 가까울 시 => 상 -> 좌가 우선
 *
 *
 *   없앨 수 있는 몬스터가 없다면 일을 끝냅니다.
 *
 *   --------------------------------
 *   [접근법]
 *    반복 [ 1. 가까운 몬스터 찾기
 *      => BFS
 *      가장 가까운 곳에서 몬스터를 찾으면 ArrayList에 저장
 *      행 좌표 min 을 찾기. 행 equal이면 열 min을 찾기
 *    2. 맵에서 지우기
 *      => counting 하기
 *      counting == 로봇의 level => 레벨 업 + counting 초기화
 *    3. 죽인 몬스터 수 == 실제 몬스터 수면 반복 종료
 *    ]
 * **/
public class CT_FightingRobot { // 시간 출력하기
  static int N;
  static int[][] MAP;
  //
  static int[] robotPosition;
  static int robotLevel;
  static int killMonsterCnt;
  //
  //
  static int totalTime;
  static StringTokenizer st;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    MAP = new int[N][N];
    robotLevel = 2;

    for(int i = 0 ; i < N; i++){
      st = new StringTokenizer(br.readLine());
      for(int j = 0 ; j < N; j++){
        MAP[i][j] = Integer.parseInt(st.nextToken());
        if(MAP[i][j] == 9) {
          MAP[i][j] = 0;
          robotPosition = new int[]{i, j};
        }
      }
    }

    totalTime = 0;

    KillTask : while(true){

      // 1. BFS + 죽일 몬스터 선정
      int[] next = selectKillTarget();

      // - 못찾았으면 끝
      if(next[2] == -1) break KillTask;

      // 2. 후처리 (이동하기 + 맵에 몬스터 지우기 + 카운팅 하기 + 레벨업 확인하기(레벨업시 킬 변수 초기화))
      robotPosition = new int[] {next[0], next[1]};
      MAP[robotPosition[0]][robotPosition[1]] = 0;
      killMonsterCnt++;
      totalTime += next[2];
      if(killMonsterCnt == robotLevel) {
        robotLevel++;
        killMonsterCnt = 0;
      }
    }

    System.out.println(totalTime);
  }

  static int[] mr = {-1, 1, 0, 0};
  static int[] mc = {0, 0, -1, 1};
  private static int[] selectKillTarget() {
    ArrayList<int[]> candidate = new ArrayList<>();

    Queue<int[]> q = new ArrayDeque<>(); // 현재 레벨에 도달할 수 있는 위치
    boolean[][] visited = new boolean[N][N];
    q.offer(new int[] {robotPosition[0], robotPosition[1], 0});
    visited[robotPosition[0]][robotPosition[1]] = true;

    while(!q.isEmpty()) {
      int size = q.size();

      // AI 사용 : QUEUE 안의 모든 기준을 살펴보도록 수정
      for(int i = 0; i < size; i++) {
        int[] now = q.poll();

        for (int d = 0; d < 4; d++) {
          int nr = now[0] + mr[d];
          int nc = now[1] + mc[d];

          if (nr < 0 || nr >= N || nc < 0 || nc >= N)
            continue;
          if (visited[nr][nc])
            continue;
          if (MAP[nr][nc] > robotLevel)
            continue;
          if (MAP[nr][nc] < robotLevel && MAP[nr][nc] != 0) {
            candidate.add(new int[]{nr, nc, now[2] + 1});
          }

          visited[nr][nc] = true;
          q.offer(new int[]{nr, nc, now[2] + 1});
        }
      }

      if(!candidate.isEmpty()) break;
    }

    // AI 사용 : Collections.sort 사용하는 법 확인
    Collections.sort(candidate, (a, b) -> {
      if(a[0] != b[0]) return a[0] - b[0]; // 행 오름차순
      return a[1] - b[1]; // 열 오름차순
    });

    return candidate.isEmpty() ? new int[] {-1, -1, -1} : candidate.get(0);
  }
}
