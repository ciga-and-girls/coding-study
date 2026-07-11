public class PGM_92342 {
    class Solution {
        public int solution(int[][] board, int[][] skill) {
            int answer = 0;
            int n = board.length;
            int m = board[0].length;
            
            // 스킬 당 board를 업데이트 하는 작업을 하지 않고,
            // 스킬들의 범위와 수치를 누적하여 단 한번 board를 업데이트 하게 한다.
            
            // diff 자료구조 만들기
            int[][] diff = new int[n+1][m+1];
            
            // 스킬을 순회하면서 diff에 기록하기
            // 4점을 찍고, 시작점, 끝점+1 위치를 찍는다.
            for(int[] s : skill){
                int type = s[0];
                int r1 = s[1];
                int c1 = s[2];
                int r2 = s[3];
                int c2 = s[4];
                int degree = s[5];
                
                if(type == 1) degree = -degree;
                
                diff[r1][c1] += degree;
                diff[r1][c2+1] += -degree;
                diff[r2+1][c1] += -degree;
                diff[r2+1][c2+1] += degree;
            }
            
            // 모든 작업을 마친 후에, 가로 | 세로로 값 누적으로 값 도출하기
            // 변화가 생기기 전까지 현재 상태를 전파한다.
            for(int i = 0; i < n+1; i++){
                for(int j = 0; j < m; j++){
                    diff[i][j+1] += diff[i][j]; 
                }
            }
            
            for(int i = 0; i < m+1; i++){
                for(int j = 0; j < n; j++){
                    diff[j+1][i] += diff[j][i]; 
                }
            }
            
            // board <- diff
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    board[i][j] += diff[i][j];
                    if(board[i][j] >= 1) answer++;
                }
            }
            
            return answer;
        }
    }
}