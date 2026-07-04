package kyungrin.unsolved;

import java.util.*;
import java.io.*;

public class CT_VirusExperiment {

  /**
   * n×n 격자 무늬의 배지
   * <p>
   * 각 칸에 5만큼의 양분 m개의 바이러스 -> 나이 존재
   * <p>
   * 한 칸에 바이러스 여러 개 존재 가능
   *
   * <cycle>
   * 1. 바이러스 양분 섭취 -> 양분을 섭취한 바이러스는 나이가 1 증가 - 본인의 나이만큼 양분을 섭취 - 여러 개의 바이러스가 있을 때 - 나이가 어린 바이러스부터
   * *만약 양분이 부족하여 본인의 나이만큼 양분을 섭취할 수 없다면 그 즉시 죽습니다.**
   * <p>
   * 2. 죽은 바이러스가 양분으로 변경 - 한 칸의 양분 += 나이를 2로 나눈 값 (편의를 위해 소숫점 아래는 버립니다.)
   * <p>
   * 3. 바이러스 번식 진행 - 5의 배수의 나이를 가진 바이러스에게만 진행 - 8개의 칸에 나이가 1인 바이러스
   * <p>
   * 4. 주어진 양분의 양에 따라 칸에 양분 추가
   * <p>
   * ------------------------------------------------------
   * <p>
   * 바이러스 자료구조 -> 하나의 위치에 여러 개의 바이러스가 존재할 수 있어서 흠... ArrayList에 int[좌표, 나이]를 관리할까. 그리고
   * Collections.sort로 좌표 -> 나이 오름차순으로 정렬.. O(m log m) 그냥 공간복잡도를 더 가져가는 게 좋다...? 매번 정렬할 바에야
   * ArrayList 100개 만들어서 공간복잡도 들고가기?
   * <p>
   * 양분 자료구조 -> MAP 하나 만들어서 관리
   * <p>
   * 1. 바이러스를 꺼내서, 좌표 확인 후, 좌표에서 양분이 충분한지 확인하고 없으면 -> 바이러스에서 제거하기 + 다음에 넘길 걸로 ArrayList에 저장 있음 ->
   * 양분 MAP에서 나이만큼 제거, 나이+1 2. ArrayList에서 꺼내서 나이 / 2 해서 양분 Map에 더함 3. 바이러스를 꺼내서, 5배수 바이러스 있는지 확인 ->
   * 8방 좌표로 바이러스 생성해서 넣기 4. MAP에 양분 더하기
   **/

  static int N;
  static int M;
  static int K;
  static int[][] ADD;
  //
  static int[] mr = {-1, 1, 0, 0, -1, 1, -1, 1};
  static int[] mc = {0, 0, -1, 1, 1, -1, -1, 1};
  static int[][] map;
  static ArrayList<Integer>[][] virus;
  //
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    ADD = new int[N][N];
    map = new int[N][N];
    virus = new ArrayList[N][N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        ADD[i][j] = Integer.parseInt(st.nextToken());
        map[i][j] = 5;
        virus[i][j] = new ArrayList<>();
      }
    }

    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());

      int r = Integer.parseInt(st.nextToken()) - 1;
      int c = Integer.parseInt(st.nextToken()) - 1;
      int age = Integer.parseInt(st.nextToken());

      virus[r][c].add(age);
    }
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        Collections.sort(virus[i][j]);
      }
    }

    for (int q = 0; q < K; q++) {
      // 1. 하...여기서 꼬임
      // 바이러스 위치를 저장해놓는 걸로 해당 작업을 줄일 수 있을 거 같음.
      ArrayList<int[]> delete = new ArrayList<>();
      ArrayList<int[]> five = new ArrayList<>();
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {

          int len = virus[i][j].size();
          for (int k = 0; k < len; k++) {
            int age = virus[i][j].get(k);
            if (age > map[i][j]) {
              delete.add(new int[]{i, j, age, k});
            }
          }

          for (int[] dieVirus : delete) {
            int r = dieVirus[0];
            int c = dieVirus[1];
            int idx = dieVirus[3];

            virus[r][c].remove(idx);
          }

          len = virus[i][j].size();
          for (int k = 0; k < len; k++) {
            int age = virus[i][j].get(k);
            virus[i][j].remove(k);
            virus[i][j].add(age + 1);
            map[i][j] -= age;
            if ((age + 1) % 5 == 0) {
              five.add(new int[]{i, j});
            }
          }
        }
      }

      // 2.
      // ArrayList에서 꺼내서 나이 / 2 해서 양분 Map에 더함
      for (int[] dieVirus : delete) {
        int r = dieVirus[0];
        int c = dieVirus[1];
        int age = dieVirus[2];

        map[r][c] += age / 2;
      }

      // 3.
      for (int[] breedingVirus : five) {
        int r = breedingVirus[0];
        int c = breedingVirus[1];

        for (int d = 0; d < 8; d++) {
          int nr = r + mr[d];
          int nc = c + mc[d];

          if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
            continue;
          }

          virus[nr][nc].add(0, 1);
        }
      }

      // 4.
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          map[i][j] += ADD[i][j];
        }
      }
    }

    int virusCnt = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int v : virus[i][j]) {
          virusCnt++;
        }
      }
    }

    System.out.println(virusCnt);
  }

}
