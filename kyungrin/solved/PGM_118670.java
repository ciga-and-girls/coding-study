package kyungrin.solved;

import java.util.*;
public class PGM_118670 {
/**
 ShiftRow
 i번째 행은 i+1번째 행이 됩니다. (마지막 행은 1번째 행이 됩니다.)

 Rotate
 바깥쪽에 있는 원소들을 시계 방향으로 한 칸 회전

 ㅇ0ㅇ 당연히 시뮬로 풀 수는 있을듯?
 근데 효율성도 따진다는 거 보니까

 시뮬레이션 :
 최대 25*10000*10000
 ShiftRow가 100_000 * 50_000 -> 5 * 1_000_000_000 => 벌써부터 XX
 Rotate 도 50_000 * 50_000 - 4 ... 아무튼 많음.

 Deque? :
 left, middle, right deque 생성

 ShiftRow ->
 left => 가장 마지막 값을 가져와서 첫 번째에 붙인다.
 middle => "" (배열인 거만 다름)
 right => ""

 = 총 6회

 Rotate ->
 middle => 첫 번째 값들고와서. left의 첫번째 값을. addFirst로 넣는다.
 right => middle의 첫 번째 값의 마지막 값을. addFirst로 넣는다.
 left => middle의 마지막 값의 첫번째 값을. addLast로 넣는다.

 = 총 6회

 **/
  class Solution {
    public int[][] solution(int[][] rc, String[] operations) {
      int R = rc.length;
      int C = rc[0].length;
      int[][] answer = new int[R][C];
      Deque<Integer> left = new ArrayDeque<>();
      Deque<Deque<Integer>> middle = new ArrayDeque<>();
      Deque<Integer> right = new ArrayDeque<>();

      for(int r = 0; r < R; r++){
        left.addLast(rc[r][0]);
        right.addLast(rc[r][C-1]);
      }
      for(int r = 0; r < R; r++){
        Deque<Integer> d = new ArrayDeque<>();
        for(int c = 1; c <= C-2; c++){
          d.addLast(rc[r][c]);
        }
        middle.addLast(d);
      }

      //

      for(String operation : operations){
        if(operation.equals("ShiftRow")){
          left.addFirst(left.pollLast());
          middle.addFirst(middle.pollLast());
          right.addFirst(right.pollLast());

        } else {
          middle.peekFirst().addFirst(left.pollFirst());
          right.addFirst(middle.peekFirst().pollLast());
          middle.peekLast().addLast(right.pollLast());
          left.addLast(middle.peekLast().pollFirst());
        }
      }

      //
      for(int r = 0; r < R; r++){
        Deque<Integer> row = middle.pollFirst();
        answer[r][0] = left.pollFirst();
        for(int c = 1; c <= C-2; c++){
          answer[r][c] = row.pollFirst();
        }
        answer[r][C-1] = right.pollFirst();
      }
      return answer;
    }
  }
}
