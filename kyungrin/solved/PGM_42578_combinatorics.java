import java.util.*;

public class PGM_42578_combinatorics {

    public int solution(String[][] clothes) {
        int answer = 1;
        Map<String, Integer> clothAndCount = new HashMap<>();

        for (String[] cloth : clothes) {
            String type = cloth[1];

            clothAndCount.put(
                type,
                clothAndCount.getOrDefault(type, 0) + 1
            );
        }

        for (int count : clothAndCount.values()) {
            answer *= count + 1;
        }

        return answer - 1;
    }
}
