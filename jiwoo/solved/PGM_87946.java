/*
*   순열(DFS) (15분)
*/
class Solution_87946_피로도 {
    
    static int n;  // 던전의 개수
    static boolean[] selected;
    static int answer;
    
    public int solution(int k, int[][] dungeons) {
        
        n = dungeons.length;
        selected = new boolean[n];
        answer = 0;
        
        perm(0, k, 0, dungeons);
        
        return answer;
    }
    
    private static void perm(int idx, int cur, int cnt, int[][] dungeons) {
        
        if(idx == n) {
            answer = Math.max(answer, cnt);
            return;
        }
        
        for(int i = 0; i < n; i++) {
            
            if(selected[i]) continue;
            
            selected[i] = true;
            
            if(cur >= dungeons[i][0]) {  // 던전 탐험 가능
                perm(idx+1, cur-dungeons[i][1], cnt+1, dungeons);
            } else {  // 던전 탐험 불가능
                perm(idx+1, cur, cnt, dungeons);
            }
            
            selected[i] = false;
        }
    }
}