import java.util.List;
import java.util.ArrayList;

class Solution_49993_스킬트리 {
    
    static List<Integer>[] tree;
    
    public int solution(String skill, String[] skill_trees) {
        
        tree = new List[26];
        for(int i = 0; i < 26; i++) tree[i] = new ArrayList<>();
        for(int i = 0; i < skill.length()-1; i++) {
            int prev = skill.charAt(i) - 'A';
            int next = skill.charAt(i+1) - 'A';
            
            tree[next].add(prev);
        }
        
        int answer = 0;
        
        for(int i = 0; i < skill_trees.length; i++) {
            if(available(skill_trees[i])) answer++;
        }
        
        return answer;
    }
    
    private static boolean available(String skillTree) {
        
        boolean[] selected = new boolean[26];
        
        for(int i = 0; i < skillTree.length(); i++) {
            
            int cur = skillTree.charAt(i) - 'A';
            selected[cur] = true;
            
            for(int prev : tree[cur]) {
                if(!selected[prev]) return false;
            }
        }
        
        return true;
    }
    
    
}