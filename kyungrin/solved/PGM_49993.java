package kyungrin.solved;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PGM_49993 {
  public int solution(String skill, String[] skill_trees) {
    int answer = 0;

    Map<String, String> map = new HashMap<>();
    int len = skill.length();
    for(int i = 1; i < len; i++){
      map.put(String.valueOf(skill.charAt(i)), String.valueOf(skill.charAt(i-1)));
    }

    for(String tree : skill_trees){
      Set<String> set = new HashSet<>();
      boolean alright = true;
      for(String s : tree.split("")){
        set.add(s);

        if(map.containsKey(s)) {
          if(!set.contains(map.get(s))) {
            alright = false;
            break;
          }
        }

      }
      if(alright) answer++;
    }

    return answer;
  }
}
