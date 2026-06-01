def solution(n, money):
    answer = 0
    dp = [0] * (n+1)
    dp[0] = 1
    
    for i in money:
        for j in range(1, n+1):
            if j - i >= 0:
                dp[j] += dp[j-i]
                dp[j] % 1000000007

    return dp[n]