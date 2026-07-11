import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;

/*
*   BFS
*   
*   AI 활용:
*   - Set을 활용해 visted를 관리하는 과정에서 두 좌표를 문자열로 변환할 때의 형태 질문
*   - 이동 가능한 칸인지 확인하는 로직에서 잘못된 부분 수정
*   - 회전 과정에서의 중복 코드를 줄이기 위한 방법 질문
*/
class Solution_60063_블록이동하기 {
    
    // 상 하 좌 우
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    
    static int N;
    static int[][] map;  // (1,1) ~ (N,N)
    static Queue<int[][]> queue;
    static Set<String> visited;
    
    public int solution(int[][] board) {
        
        N = board.length;
        map = new int[N+1][N+1];
        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                map[r][c] = board[r-1][c-1];
            }
        }
        
        return bfs();
    }
    
    private static int bfs() {
        
        queue = new ArrayDeque<>();
        queue.add(new int[][] {{1, 1}, {1, 2}, {0}});  // 로봇의 시작 위치, 시간
        visited = new HashSet<>();
        visited.add("1,1/1,2");
        
        while(!queue.isEmpty()) {
            int[][] cur = queue.poll();
            int r1 = cur[0][0];
            int c1 = cur[0][1];
            int r2 = cur[1][0];
            int c2 = cur[1][1];
            int time = cur[2][0];
            
            if((r1 == N && c1 == N) || (r2 == N && c2 == N)) return time;
            
            // 상하좌우 이동
            for(int dir = 0; dir < 4; dir++) {
                int nr1 = r1 + dr[dir];
                int nc1 = c1 + dc[dir];
                int nr2 = r2 + dr[dir];
                int nc2 = c2 + dc[dir];
                
                if(!checkArea(nr1, nc1) || !checkArea(nr2, nc2)) continue;
                if(!canMove(nr1, nc1, nr2, nc2)) continue;
                
                addNext(nr1, nc1, nr2, nc2, time+1);
            }
            
            // 회전
            rotate(r1, c1, r2, c2, time);
            
        }
        
        return -1;
    }
    
    // AI 활용: 두 좌표를 하나의 문자열로 변환할 때의 형태
    private static String makeKey(int r1, int c1, int r2, int c2) {
        
        String key = "";
        
        if(r1 < r2 || (r1 == r2 && c1 < c2)) {
            key = r1 + "," + c1 + "/" + r2 + "," + c2;
        } else {
            key = r2 + "," + c2 + "/" + r1 + "," + c1;
        }
        
        return key;
    }
    
    private static boolean checkArea(int r, int c) {
        return r > 0 && r <= N && c > 0 && c <= N;
    }
    
	private static boolean canMove(int r1, int c1, int r2, int c2) {
		return map[r1][c1] == 0 && map[r2][c2] == 0;
	}
    
    private static void addNext(int r1, int c1, int r2, int c2, int time) {
    	
    	String key = makeKey(r1, c1, r2, c2);
    	
    	if(visited.contains(key)) return;
    	
    	visited.add(key);
    	queue.add(new int[][] {{r1, c1}, {r2, c2}, {time}});
    }
    
    private static void rotate(int r1, int c1, int r2, int c2, int time) {
    	
    	// 가로 상태
    	if(r1 == r2) {
    		for(int dir : new int[] {-1, 1}) {
    			int nr = r1 + dir;
    			
    			if(!checkArea(nr, c1) || !checkArea(nr, c2)) continue;
    			if(!canMove(nr, c1, nr, c2)) continue;
    			
    			// r1, r2 기준 회전
    			addNext(r1, c1, nr, c1, time+1);
    			
    			// r2, c2 기준 회전
    			addNext(nr, c2, r2, c2, time+1);
    		}
    	}
    	
    	// 가로 상태
    	else if(c1 == c2) {
    		for(int dir : new int[] {-1, 1}) {
    			int nc = c1 + dir;
    			
    			if(!checkArea(r1, nc) || !checkArea(r2, nc)) continue;
    			if(!canMove(r1, nc, r2, nc)) continue;
    			
    			// r1, r2 기준 회전
    			addNext(r1, c1, r1, nc, time+1);
    			
    			// r2, c2 기준 회전
    			addNext(r2, nc, r2, c2, time+1);
    		}
    	}
    }

}