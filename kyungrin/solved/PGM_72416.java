package kyungrin.solved;

import java.util.Arrays;

public class PGM_72416 {

  public int solution(int[] sales, int[][] links) {
    int nodeCnt = sales.length;

    // 인접 리스트: 부모 → 자식
    int[] heads = new int[nodeCnt];
    Arrays.fill(heads, -1);

    int[] to = new int[nodeCnt - 1];
    int[] next = new int[nodeCnt - 1];

    for (int i = 0; i < links.length; i++) {
      int parent = links[i][0] - 1;
      int child = links[i][1] - 1;

      to[i] = child;
      next[i] = heads[parent];
      heads[parent] = i;
    }

    // 부모 → 자식 순서 생성
    int[] stack = new int[nodeCnt];
    int[] order = new int[nodeCnt];

    int top = 0;
    int size = 0;

    stack[top++] = 0;

    while (top > 0) {
      int cur = stack[--top];
      order[size++] = cur;

      for (int edge = heads[cur]; edge != -1; edge = next[edge]) {
        stack[top++] = to[edge];
      }
    }

    // dp[node][0] = 현재 직원 불참
    // dp[node][1] = 현재 직원 참석
    long[][] dp = new long[nodeCnt][2];

    // 자식 → 부모 순서로 DP 계산
    for (int i = nodeCnt - 1; i >= 0; i--) {
      int cur = order[i];

      // 현재 직원 참석
      dp[cur][1] = sales[cur];

      // 리프 노드
      if (heads[cur] == -1) {
        dp[cur][0] = 0;
        continue;
      }

      long sum = 0;
      long minExtra = Long.MAX_VALUE;

      for (int edge = heads[cur]; edge != -1; edge = next[edge]) {
        int child = to[edge];

        long minCost = Math.min(dp[child][0], dp[child][1]);

        sum += minCost;
        dp[cur][1] += minCost;

        // 현재 직원이 불참하면 자식 중 최소 한 명은 참석해야 한다.
        minExtra = Math.min(
            minExtra,
            dp[child][1] - minCost
        );
      }

      dp[cur][0] = sum + minExtra;
    }

    return (int) Math.min(dp[0][0], dp[0][1]);
  }
}
