import heapq
def solution(n, s, a, b, fares):
    result = float('inf')
    cost = [[float('inf')]*n for i in range(n)]
    
    for d, c, f in fares: #금액 기록하기
        cost[d-1][c-1] = f #장소는 1부터 시작하므로 -1해주기
        cost[c-1][d-1] = f

    #출발지, a가 도착할 곳, b가 도착할 곳을 기준으로 다익스트라 함수 돌리기 -> 리스트 할당    
    arr_s = dijkstra(s-1, cost, n)
    arr_a = dijkstra(a-1, cost, n)
    arr_b = dijkstra(b-1, cost, n)
    
    for i in range(n):
        result = min(result, arr_s[i] + arr_a[i] + arr_b[i]) #0부터 n까지의 환승 장소 중 가장 저렴한 금액이 저장됨
    
    return result

def dijkstra(i, cost, n): #출발지에 따른 다른 도착지까지의 최소 비용을 구하는 함수
    arr = [float('inf')] * n #리턴할 값이자, 최소 비용이 저장될 리스트
    
    q = []
    arr[i] = 0 #출발지는 비용 0으로 설정
    heapq.heappush(q, (0, i))
    
    while q:
        v, idx = heapq.heappop(q)
        
        for j in range(n):
            if idx != j and cost[idx][j] != float('inf') and v + cost[idx][j] < arr[j]:
                #현재 장소와 다르고, 길이 연결되어 있고, 새로운 비용이 현재 리스트에 저장되어 있는 비용보다 저렴하다면
                arr[j] = v + cost[idx][j] #arr[j]에 새로운 비용 저장
                heapq.heappush(q, (v + cost[idx][j], j))
    
    return arr