T = int(input())
 
for tc in range(1, T+1):
    n, m = map(int, input().split())
    arr = [list(map(int, input().split())) for i in range(n)]
 
    home = [] #집의 위치를 저장할 리스트
    result = 0
 
    for i in range(n):
        for j in range(n):
            if arr[i][j] == 1:
                home.append((i, j)) #집 위치 저장
 
    r = 0
    while r <= n: #최대 n이면 모든 좌표를 덮을 수 있으므로 최대를 n으로 설정
        r += 1
        cost = -(r*r + (r-1)*(r-1)) #기본 cost값 설정
 
        for i in range(n):
            for j in range(n):
                new_cost = cost #좌표 이동할 때마다 new_cost에 cost 옮겨담기
                cnt = 0
                for x, y in home:
                    if abs(i-x) + abs(j-y) < r: #만약 한 집이 범위 내에 들어와있다면
                        new_cost += m #집이 내는 비용 m을 new_cost에 더하기
                        cnt += 1 #범위 내에 있는 집 cnt + 1
 
                if new_cost >= 0: #만약 손해가 아니라면
                    result = max(result, cnt) #result 갱신
 
    print(f'#{tc} {result}')