import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 *   시뮬레이션 (60분 + @)
 *   메모리: 94,504 KB
 *   시간: 356 ms
 */
public class Main_17144_미세먼지안녕_Queue {
    
    static int R, C, T;  // 집의 크기: R x C, T초 후 남아있는 미세먼지의 양 출력
    static int[][] A;  // 미세먼지의 양
    static Queue<int[]> queue;
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
        queue = new ArrayDeque<>();
        airPurifier = new int[2];
        for(int r = 0, idx = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            for(int c = 0; c < C; c++) {
                A[r][c] = Integer.parseInt(st.nextToken());
                
                if(A[r][c] > 0) {
                    queue.add(new int[] {r, c, A[r][c]});
                }
                
                if(A[r][c] == -1) {
                    airPurifier[idx++] = r;
                }
            }
        }
        
        for(int t = 0; t < T; t++) {
            diffusion();
        }
        
        System.out.println(fineDust);
    }
    
    private static void diffusion() {
    	
        // 1. 미세먼지 확산
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cnt = 0;
            for(int dir = 0; dir < 4; dir++) {
                int nr = cur[0] + dr[dir];
                int nc = cur[1] + dc[dir];
                
                if(nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if(A[nr][nc] == -1) continue;
                
                A[nr][nc] += cur[2]/5;
                cnt++;
            }
            A[cur[0]][cur[1]] -= cur[2]/5 * cnt;
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
        
        // 3. 미세먼지 큐
        fineDust = 0;
        for(int nr = 0; nr < R; nr++) {
            for(int nc = 0; nc < C; nc++) {
                if(A[nr][nc] <= 0) continue;
                
                queue.add(new int[] {nr, nc, A[nr][nc]});
                fineDust += A[nr][nc];
                
            }
        }
        
        
        // 출력
//        for(int i = 0; i < R; i++) {
//        	for(int j = 0; j < C; j++) {
//        		System.out.print(A[i][j] + " ");
//        	}
//        	System.out.println();
//        }
//        System.out.println();
    }

}