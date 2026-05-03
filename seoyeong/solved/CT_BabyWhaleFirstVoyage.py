from collections import deque
n, r, c, d = map(int, input().split())
arr = [list(map(int, input().split())) for i in range(n)]

delta1 = [0, -1, 1, 0, 0] # 1번부터 상, 하, 좌, 우로 설정
delta2 = [0, 0, 0, -1, 1]

dir_dict = { # 각각의 방향에 따라 직진, 좌회전, 우회전, 180도 회전 방향 설정
    1 : [1, 3, 4, 2],
    2 : [2, 4, 3, 1],
    3 : [3, 2, 1, 4],
    4 : [4, 1, 2, 3]
}

baby_x, baby_y = r-1, c-1 #첫 시작점
result = set() # 방문 순서대로 좌표를 저장할 세트
visited = [[False] * n for i in range(n)] #방문 여부 확인용
complete = 1 #이미 방문한 좌표의 수

visited[baby_x][baby_y] = True #시작점은 이미 방문했으니 방문 처리
result.add((baby_x, baby_y)) #세트에도 저장

sea_cnt = 0
for x in range(n):
    for y in range(n):
        if arr[x][y] == 0:
            sea_cnt += 1 #총 바다의 개수 구하기

while complete < sea_cnt:
    tour = False #인접 탐험 가능여부 판별용

    for i in dir_dict[d]:
        x1 = baby_x + delta1[i]
        y1 = baby_y + delta2[i]

        if 0 <= x1 < n and 0 <= y1 < n and not visited[x1][y1] and arr[x1][y1] == 0:
            baby_x, baby_y = x1, y1 #인접 탐험 가능한 곳 발견하면
            visited[x1][y1] = True #좌표 갱신 후 방문 처리
            tour = True #인접 탐험 가능 처리
            d = i #방향 바꾸기
            break
    
    if not tour: #인접 탐험이 안될 시
        route = [] #이동할 칸을 찾기 위해 만든 리스트
        distance = [[float('inf')] * n for i in range(n)] #현재 위치에서의 거리를 구하기 위해 만든 리스트
        used = [[False] * n for i in range(n)] #방문 처리 용

        q = deque()
        q.append((baby_x, baby_y, 0))
        distance[baby_x][baby_y] = 0
        used[baby_x][baby_y] = True

        while q:
            x, y, v = q.popleft()

            for i in range(1, 5):
                x1 = x + delta1[i]
                y1 = y + delta2[i]

                if 0 <= x1 < n and 0 <= y1 < n and arr[x1][y1] == 0 and not used[x1][y1]:
                    used[x1][y1] = True #방문 가능한 곳 찾으면 방문 처리하고
                    distance[x1][y1] = v + 1 #거리 저장
                    q.append((x1, y1, v+1))
                    

        for x in range(n):
            for y in range(n):
                if not visited[x][y] and arr[x][y] == 0: #방문 가능한 곳 찾아서
                    route.append([distance[x][y], x, y]) #거리, 행, 열 좌표 루트 리스트에 어펜드
        route.sort()

        target_x, target_y = route[0][1], route[0][2] #타겟 위치 찾기
        new_delta1 = [0, 1, 0, -1] #좌, 하, 우, 상의 순서로 델타 배열 새로 생성
        new_delta2 = [-1, 0, 1, 0]
        new_dir = [3, 2, 4, 1] #그에 따른 이동방향 리스트 생성
        used = [[False] * n for i in range(n)]

        q.append((baby_x, baby_y, 0, 0))
        used[x][y] = True

        while q:
            x, y, v, nd = q.popleft()

            if x == target_x and y == target_y: # 만약 타겟 위치까지 도달했으면
                d = new_dir[nd] #방향 새로 찾이서 할당
                visited[x][y] = True #좌표 방문 처리
                baby_x, baby_y = x, y #아기 고래 위치 갱신
                break
            
            for i in range(4):
                x1 = x + new_delta1[i]
                y1 = y + new_delta2[i]

                if 0 <= x1 < n and 0 <= y1 < n and arr[x1][y1] == 0 and distance[x1][y1] == distance[x][y] + 1:
                    # distance[x1][y1] == distance[x][y] + 1 <--- 이걸로 고래가 다음으로 방문하는 칸인지 아닌지 판별
                    if not used[x1][y1]:
                        q.append((x1, y1, v+1, i))
                        used[x1][y1] = True


    result.add((baby_x, baby_y)) #result에 이동한 고래 좌표 넣어주고
    complete += 1 #방문한 칸 개수 하나 늘리기

for x, y in result:
    print(x+1, y+1)
