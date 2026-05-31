class Solution_단어변환 {
    
    static int answer;
    static String[] list;
    static boolean[] visited;
    
    public int solution(String begin, String target, String[] words) {
        
        boolean flag = true;
        for(String word : words) {
            if(word.equals(target)) {
                flag = false;
                break;
            }
        }
        
        if(flag) return 0;
        
        visited = new boolean[words.length];
        
        answer = Integer.MAX_VALUE;
        list = words;
        dfs(begin, target, 0, 0);
        
        return answer;
    }
    
    private static void dfs(String begin, String target, int idx, int cnt) {
    
        if(begin.equals(target)) {
            answer = Math.min(answer, cnt);
            return;
        }
        
        if(idx == list.length) {
            answer = 0;
            return;
        }

        for(int i = 0; i < list.length; i++) {
            if(visited[i]) continue;

            int count = 0;
            for(int j = 0; j < begin.length(); j++) {
                if(begin.charAt(j) == list[i].charAt(j)) count++;
            }

            if(count != begin.length() - 1) continue;

            visited[i] = true;
            dfs(list[i], target, idx+1, cnt+1);
            visited[i] = false;
        }
    }
}

