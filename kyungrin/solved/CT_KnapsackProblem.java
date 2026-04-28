package kyungrin.solved;
import java.util.Scanner;
public class CT_KnapsackProblem {
  static final int INT_MIN = Integer.MIN_VALUE;
  static final int MAX_M = 10000;
  static final int MAX_N = 100;

  static int[][] dp = new int[MAX_N + 1][MAX_M + 1];

  static int n, m;

  static int[] weight = new int[MAX_N + 1];
  static int[] value = new int[MAX_N + 1];

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    n = sc.nextInt();
    m = sc.nextInt();

    for(int i = 1; i <= n; i++){
      weight[i] =  sc.nextInt();
      value[i] =  sc.nextInt();
    }

    for(int i = 0; i <= n; i++){
      for(int j = 0; j <= m; j++){
        dp[i][j] = INT_MIN;
      }
    }

    dp[0][0] = 0;

    for(int i = 1; i <= n; i++){

      for(int w = 0; w <= m; w++){

        if(w >= weight[i]){

          dp[i][w] = Math.max(dp[i-1][w - weight[i]] + value[i], dp[i-1][w]);
        }
        else {
          dp[i][w] = dp[i-1][w];
        }
      }
    }

    int result = 0;
    for(int w = 0; w <= m; w++){
      result = Math.max(result, dp[n][w]);
    }
    System.out.println(result);
  }
}
