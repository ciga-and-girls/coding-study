package kyungrin.solved;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/** 차량 번호가 작은 자동차부터 청구할 주차 요금을 차례대로 정수 배열
 *
 * 1. 누적 주차 시간이 기본 시간이하 -> 기본 요금을 청구
 * 2. 누적 주차 시간이 기본 시간을 초과 -> 기본 요금 + 단위 요금
 *  - 단위 시간으로 나누어 떨어지지 않으면, 올림
 *
 *
 *  ----------
 *  fees
 *  기본 시간(분). 기본 요금(원). 단위 시간(분). 단위 요금(원)
 *
 *  records
 *  [(시각, 차량번호, 내역)]
 *  - HH:MM 형식의 길이 5
 *    입차된 후에 출차된 내역이 없다면, 23:59에 출차
 *  - 차량번호 `0'~'9'로 구성된 길이 4인 문자열
 *  - 내역 IN 또는 OUT
 *  ---------------------
 *  : 문자열 구현
 *
 *  records
 *  1. 입-출 짝찾기
 *    - 차량번호 기준으로 ... key로 두고 v를 2개 둘 수 있는 게 있나?
 *    - 그리고 key 가 2개 들어온 이후에는 리셋외어야 하는데
 *    내역이 "입"이면
 *    -> hashmap 에 그냥 put
 *    내역이 "출"이면
 *    -> hashmap 에서 get 해서 [2]시간계산 하기
 *
 *  2. 가격 계산 및 추렭
 *  기본 단위 시간에 벗어나면 ~~..
 *

 * **/
public class PGM_92341 {
  class Solution {
    static Map<Integer, Integer[]> carsData; // 차량번호-시각(분계산)
    static Map<Integer, Integer> carsTime; // 차량번호-실제 주차시간
    static StringTokenizer st;
    public int[] solution(int[] fees, String[] records) {
      carsData = new HashMap<>();
      carsTime = new TreeMap<>();
      for(String record : records){
        st = new StringTokenizer(record);

        String[] tdata = st.nextToken().split(":");
        int time = Integer.parseInt(tdata[0]) * 60 + Integer.parseInt(tdata[1]);

        int num = Integer.parseInt(st.nextToken());
        String IO = st.nextToken();

        if(IO.equals("IN")){
          carsData.put(num, new Integer[] {time, 1});
        }
        else  {
          // AI 사용 : carsData.put을 먼저하여 입차 시간이 출차 시간으로 덮어씌워짐
          int inTime = carsData.get(num)[0];
          carsData.put(num, new Integer[] {time, 2});
          carsTime.put(num, carsTime.getOrDefault(num, 0) + time - inTime);
        }
      }

      int lastTime = 23 * 60 + 59;
      for(Map.Entry<Integer, Integer[]> entry : carsData.entrySet()){
        int notOut = entry.getValue()[1];
        if(notOut == 1) {
          int num = entry.getKey();
          carsTime.put(num, carsTime.getOrDefault(num, 0) + lastTime - entry.getValue()[0]);
        }
      }

      int[] answer = new int[carsTime.size()];
      int idx = 0;
      for(Map.Entry<Integer, Integer> entry : carsTime.entrySet()) {
        int totalTime = entry.getValue();
        int totalPrice = 0;
        if(totalTime > fees[0]){
          totalPrice= fees[1] + (int) Math.ceil((double)(totalTime - fees[0])/fees[2])*fees[3];
        } else {
          totalPrice = fees[1];
        }

        answer[idx++] = totalPrice;
      }

      return answer;
    }
  }
}
