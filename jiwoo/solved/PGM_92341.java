import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *   구현 (89분)
 * 
 *   정산 기준: 00:00 ~ 23:59 까지의 누적 주차 시간을 기준으로 요금 정산
 */
public class Solution_92341_주차요금계산 {

	public static void main(String[] args) {

		int[][] fees = {
				{180, 5000, 10, 600},
				{120, 0, 60, 591},
				{1, 461, 1, 10}
		};
		String[][] records = {
				{"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"},
				{"16:00 3961 IN","16:00 0202 IN","18:00 3961 OUT","18:00 0202 OUT","23:58 3961 IN"},
				{"00:00 1234 IN"}
		};
		
		for(int tc = 0; tc < 3; tc++) {
			System.out.println(Arrays.toString(solution(fees[tc], records[tc])));
		}
	}
	
	static class Car {
		String carNum;
		String inTime;
		boolean isOut;
		int totalTime;
		
		public Car(String carNum, String inTime) {
			this.carNum = carNum;
			this.inTime = inTime;
			this.isOut = false;
			this.totalTime = 0;
		}
	}
	
	static Map<String, Car> cars;
	
	public static int[] solution(int[] fees, String[] records) {
		
		cars = new HashMap<>();
		
		for(String record : records) {
			String type = record.substring(11);
			String carNum = record.substring(6, 10);
			String time = record.substring(0, 5);
			
			if(type.equals("IN")) {  // 입차
				if(!cars.containsKey(carNum)) {
					cars.put(carNum, new Car(carNum, time));
				} else {
					cars.get(carNum).inTime = time;
					cars.get(carNum).isOut = false;
				}
			} else {  // 출차
				cars.get(carNum).totalTime += calc(carNum, time);
				cars.get(carNum).isOut = true;
			}
		}
		
		// 출차 내역이 없는 경우
		String lastOut = "23:59";
		for(Car car : cars.values()) {
			if(!car.isOut) car.totalTime += calc(car.carNum, lastOut);
		}
		
		// 차량 번호가 작은 자동차부터 오름차순 정렬
		List<String> keys = new ArrayList<>(cars.keySet());
		Collections.sort(keys);
		int[] answer = new int[cars.size()];
		int idx = 0;
		for(String key : keys) {
			answer[idx++] = getFee(fees, cars.get(key).totalTime);
		}
		
		return answer;
	}
	
	private static int calc(String carNum, String outTime) {
		
		int inHour = Integer.parseInt(cars.get(carNum).inTime.substring(0, 2));
		int inMinute = Integer.parseInt(cars.get(carNum).inTime.substring(3));
		int outHour = Integer.parseInt(outTime.substring(0, 2));
		int outMinute = Integer.parseInt(outTime.substring(3));
		
		int totalTime = (outHour - inHour) * 60 + outMinute - inMinute;
		return totalTime;
	}
	
	private static int getFee(int[]fees, int totalTime) {
		
		int fee = fees[1];
		
		if(totalTime <= fees[0]);
		else {
			totalTime -= fees[0];
			fee += (totalTime / fees[2]) * fees[3];
			if(totalTime % fees[2] != 0) fee += fees[3];
		}
		
		return fee;
	}

}
