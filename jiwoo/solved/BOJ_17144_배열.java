import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 *   메모리: 36,420 KB
 *   시간: 260 ms
 */
public class Main_17144_미세먼지안녕_배열 {
    
    static int R, C, T;  // 집의 크기: R x C, T초 후 남아있는 미세먼지의 양 출력
    static int[][] A;  // 미세먼지의 양
    static int fineDust;
    static int[] airPurifier;
    
    // 상 우 하 좌
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        
        A = new int[R][C];
        airPurifier = new int[2];
        for(int r = 0, idx = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            for(int c = 0; c < C; c++) {
                A[r][c] = Integer.parseInt(st.nextToken());
                
                if(A[r][c] == -1) airPurifier[idx++] = r;
                
            }
        }
        
        for(int t = 0; t < T; t++) {
            diffusion();
        }
        
        for(int r = 0; r < R; r++) {
        	for(int c = 0; c < C; c++) {
        		if(A[r][c] > 0) fineDust += A[r][c];
        	}
        }
        
        System.out.println(fineDust);
    }
    
    private static void diffusion() {
    	
    	int[][] temp = new int[R][C];
    	for(int r = 0; r < R; r++) temp[r] = A[r].clone();
    	
        // 1. 미세먼지 확산
        for(int i = 0; i < R; i++) {
        	for(int j = 0; j < C; j++) {
        		if(temp[i][j] <= 0) continue;
        		
        		int cnt = 0;
                for(int dir = 0; dir < 4; dir++) {
                    int nr = i + dr[dir];
                    int nc = j + dc[dir];
                    
                    if(nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                    if(A[nr][nc] == -1) continue;
                    
                    A[nr][nc] += temp[i][j]/5;
                    cnt++;
                }
                A[i][j] -= temp[i][j]/5 * cnt;
        	}
        }
        
        // 2. 공기청정기 작동
        // 2-1 위쪽 공기청정기 (반시계방향으로 순환)
        int r = airPurifier[0] - 1;
        int c = 0;
        int dir = 0;
        while(true) {
        	
            int nr = r + dr[dir];
            int nc = c + dc[dir];
            
            if(A[nr][nc] == -1) {
                A[r][c] = 0;
                break;
            }
            
            A[r][c] = A[nr][nc];
            
            r = nr;
            c = nc;
            
            if((r == 0 && (c == 0 || c == C - 1)) || (r == airPurifier[0] && c == C - 1)) dir++;
            
        }
     // 2-2 아래쪽 공기청정기 (시계방향으로 순환)
        r = airPurifier[1] + 1;
        c = 0;
        dir = 2;
        while(true) {
            int nr = r + dr[dir];
            int nc = c + dc[dir];
            
            if(A[nr][nc] == -1) {
                A[r][c] = 0;
                break;
            }
            
            A[r][c] = A[nr][nc];
            
            r = nr;
            c = nc;
            
            if((r == R - 1 && (c == 0 || c == C - 1)) || (r == airPurifier[1] && c == C - 1)) dir = (--dir + 4) % 4;
            
            
        }
        
    }

}