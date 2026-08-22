import java.util.*;

/**
10^9 테스트 1 시간초과
**/
public class PGM_42578_subset {

    boolean[] selected;
    int answer;
    int len;

    public int solution(String[][] clothes) {
        answer = 0;
        len = clothes.length;
        selected = new boolean[len];

        subset(new HashSet<>(), 0, clothes);

        return answer - 1;
    }

    private void subset(Set<String> isContains, int idx, String[][] clothes) {
        if (idx == len) {
            answer++;
            return;
        }

        String kindOfCloth = clothes[idx][1];

        if (!isContains.contains(kindOfCloth)) {
            selected[idx] = true;
            isContains.add(kindOfCloth);

            subset(isContains, idx + 1, clothes);

            isContains.remove(kindOfCloth);
        }

        selected[idx] = false;
        subset(isContains, idx + 1, clothes);
    }
}
