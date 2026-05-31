package kyungrin.unsolved;

/** 몇 번째 실험?
 * 무빙워크 :
 *  - 길이 n
 *  - 구성요소 : 2n개의 판
 *  - 시계 방향으로 회전
 *
 * 안전성 테스트 :
 *  1번 칸에 올라서서 -> n번 칸으로 이동
 *
 *  판의 안전성 :
 *  사람이 올라간 판은 안정성 -1
 *  시간에 지남에 따라 다시 상승하지 않습니다.
 *
 * 루틴 :
 * 1. 무빙워크 회전 (판)
 * 2. 사람들 시계 방향 한 칸 이동 (칸)
 *  - 먼저 올라탄 사람 먼저
 * 3. 1번 칸에 사람올리기
 *  - 1번 칸 : 사람없음, 안전성 0아님
 *  - n번 칸의 사람 : 그 즉시 내림
 * 종료 조건 : 판의 안전성이 0인 칸이 K이상일 시 종료
 *
 * --------------
 * 내구도 있는 판과 n번 칸은 별도이다.
 * 무빙워크의 회전은 판에 연관있고
 * 사람의 이동은 칸에 연관있다.
 * 
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 완전 시뮬레이션
 * */
public class CT_UnstableMovingWalk {
  static int N, K;
  //
  static int[] movingWorkBoards; // 무빙워크의 판 위치 | idx : 위치, value : 판 번호
  static int[] boardSafety; // 판의 안전성 | idx : 판의 번호, value : 판의 안전성, 1-BASED
  // 사람의 이동 가능과 불가능 여부를 빠르게 따지기 위한 배열
  static boolean[] isPersonHere; // 사람이 서있는 위치 | idx : 판의 번호, value : 서있기의 유무, 1-BASED

  static int lastPersonNum;
  // 사람의 정보 : 사람의 번호 & 그 사람이 서 있는 판의 번호
  static class Person implements Comparable<Person> {
    int personNum;
    int boardNum;

    public Person(int personNum, int boardNum) {
      this.personNum = personNum;
      this.boardNum = boardNum;
    }

    @Override
    public int compareTo(Person o) {
      return this.personNum - o.personNum;
    }
  }
  // 먼저 처리해야할 사람을 찾기 위한 정보를 담는 pq
  static PriorityQueue<Person> pq;
  static int stopBoxIdx;
  static int safeZeroCnt;
  //
  static int rotationCnt;
  //
  static StringTokenizer st;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    stopBoxIdx = N-1; // 무빙워크의 칸 위치는 0-BASED

    movingWorkBoards = new int[N*2];
    boardSafety = new int[N*2+1];
    st = new StringTokenizer(br.readLine());
    for(int i = 1; i <= N; i++) {
      boardSafety[i] = Integer.parseInt(st.nextToken());
    }

    isPersonHere = new boolean[N*2+1];
    lastPersonNum = 0;
    pq = new PriorityQueue<>();
    safeZeroCnt = 0;
    rotationCnt = 0;

    while(true){
      // 1. 무빙워크 한 칸 회전
      movingWork();

      // 2. 사람들의 이동
      movingPerson();

      // 3. 1번 칸에 사람 올리기
      if(boardSafety[1] != 0 && !isPersonHere[1]) {
        boardSafety[1]--;
        isPersonHere[1] = true;
        pq.add(new Person(lastPersonNum + 1, 1));
      }

      // 4. 안전성이 0이 된 칸이 있는지 확인
      int tempCnt = 0;
      for(int i = 1; i <= N; i++) {
        if(boardSafety[i] == 0) tempCnt++;
      }
      safeZeroCnt = tempCnt;
      rotationCnt++;

      if (safeZeroCnt == K) break;
    }

    System.out.println(rotationCnt);

  }

  // 무빙워크 : 시계방향으로 Shifting 하는 기능을 가진다.
  // movingWorkBoards 요소를 오른쪽으로 이동
  private static void movingWork() {
    int temp = movingWorkBoards[N-1];
    for(int i = N-2; i >= 1; i--){
      movingWorkBoards[i] = movingWorkBoards[i-1];
    }
    movingWorkBoards[0] = temp;
  }

  // 사람의 이동 : 옆 판으로 이동한다.
  private static void movingPerson() {

    PriorityQueue<Person> tempPQ = new PriorityQueue<>();

    while(!pq.isEmpty()) {
      Person person = pq.poll();
      int nextBoardNum = (person.boardNum + 1) % N;

      if (boardSafety[nextBoardNum] != 0 && !isPersonHere[nextBoardNum]) {
        if(isStopBox(nextBoardNum)) continue;

        boardSafety[nextBoardNum]--;
        isPersonHere[person.boardNum] = false;
        isPersonHere[nextBoardNum] = true;
        tempPQ.add(new Person(person.personNum, nextBoardNum));
      } else {
        tempPQ.add(new Person(person.personNum, person.boardNum));
      }
    }

    pq = tempPQ;
  }

  private static boolean isStopBox(int nextBoardNum) {
    for(int i = 0; i < N*2; i++){
      if(movingWorkBoards[i] == nextBoardNum) {
        if (i == stopBoxIdx) return true;
      }
    }
    return false;
  }


}
