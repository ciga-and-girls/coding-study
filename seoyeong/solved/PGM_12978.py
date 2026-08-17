from collections import deque
def solution(N, road, K):
    answer = 0

    graph = [[float('inf')] * (N+1) for i in range(N+1)]
    
    for a, b, cost in road:
        graph[a][b] = min(graph[a][b], cost)
        graph[b][a] = min(graph[b][a], cost)
        
    q = deque()
    q.append((1, 0))
    result = [float('inf')] * (N+1)
    result[1] = 0
    
    while q:
        village, cost = q.popleft()
        
        for i in range(1, N+1):
            if graph[village][i] != float('inf') and cost + graph[village][i] <= result[i] and cost + graph[village][i] <= K:
                
                q.append((i, cost+graph[village][i]))
                result[i] = cost + graph[village][i]

    for i in range(N+1):
        if result[i] != float('inf'):
            answer += 1
    
    return answer