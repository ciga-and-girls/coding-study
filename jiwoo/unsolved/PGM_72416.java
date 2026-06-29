import java.util.ArrayList;
import java.util.List;

/*
 *   구현 (120분)
 *   
 *   AI 활용: 반례 생성
 */
public class Solution_72416_매출하락최소화 {

	public static void main(String[] args) {

		int[][] sales = {
				{14, 17, 15, 18, 19, 14, 13, 16, 28, 17},
				{5, 6, 5, 3, 4},
				{5, 6, 5, 1, 4},
				{10, 10, 1, 1},
				{1, 1, 2, 1}  // AI 생성 반례 1
		};
		int[][][] links = {
				{{10, 8}, {1, 9}, {9, 7}, {5, 4}, {1, 5}, {5, 10}, {10, 6}, {1, 3}, {10, 2}},
				{{2,3}, {1,4}, {2,5}, {1,2}},
				{{2,3}, {1,4}, {2,5}, {1,2}},
				{{3,2}, {4,3}, {1,4}},
				{{1, 2}, {2, 3}, {3, 4}}  // AI 생성 반례 1 (정답: 2)
		};
		
		for(int tc = 0; tc < 5; tc++) {
			System.out.println(solution(sales[tc], links[tc]));
		}
	}
	
	static class Member {
		int leader;
		int number;
		int sales;
		boolean isLeader;
		
		public Member(int number, int sales) {
			this.number = number;
			this.sales = sales;
		}
	}
	
	public static int solution(int[] sales, int[][] links) {
		
		int people = sales.length;
		
		Member[] members = new Member[people];
		List<Member>[] teams = new List[people];  // teams[i]: (i+1)번 직원이 팀장으로 있는 팀의 팀원 리스트 (본인 포함)
		for(int i = 0; i < people; i++) {
			members[i] = new Member(i, sales[i]);
			teams[i] = new ArrayList<>();
		}
		teams[0].add(members[0]);  // CEO
		int totalTeam = 1;  // 총 팀 수
		for(int[] link : links) {
			
			int leader = link[0] - 1;
			int member = link[1] - 1;
			
			if(teams[leader].isEmpty()) {
				members[leader].isLeader = true;
				teams[leader].add(members[leader]);
				totalTeam++;
			}
			
			members[member].leader = leader;
			teams[leader].add(members[member]);
			
		}
		
		for(int i = 0; i < people; i++) {
			teams[i].sort((a, b) -> a.sales - b.sales);  // 매출액 기준 오름차순 정렬
		}
		
		
		int answer = Integer.MAX_VALUE;
		boolean[] attended = new boolean[people];  // 각 팀의 참여 여부
		
		// 1. 각 팀의 매출액 최소 합
		for(int i = 0, totalSales = 0, attendedTeam = 0; i < people; i++) {
			
			if(teams[i].isEmpty()) continue;
			if(attended[i]) continue;
			
			Member member = teams[i].get(0);  // 워크숍 참석 멤버(매출액이 가장 적은 멤버)
			
			totalSales += member.sales;
			attended[i] = true;
			attendedTeam++;
			
			if(member.isLeader) {  // 또 다른 팀의 멤버인 경우
				int anotherTeam = member.leader == i ? member.number : member.leader;
				
				if(!attended[anotherTeam]) {
					attended[anotherTeam] = true;
					attendedTeam++;
				}
			}
			
			if(attendedTeam == totalTeam) {
				answer = Math.min(answer, totalSales);
				break;
			}
		}
		
		
		attended = new boolean[people];
		
		// 2. 참석인원 최소 - 팀장이면서 팀원인 사람 위주
		for(int i = 1, totalSales = 0, attendedTeam = 0; i < people; i++) {
			
			if(teams[i].isEmpty()) continue;
			if(attended[i]) continue;
			
			totalSales += sales[i];
			attended[i] = true;
			attendedTeam++;
			
			if(!attended[members[i].leader]) {
				attended[members[i].leader] = true;
				attendedTeam++;
			}
			
			
			if(attendedTeam == totalTeam) {
				answer = Math.min(answer, totalSales);
				break;
			}
		}
		
		return answer;
	}

}
