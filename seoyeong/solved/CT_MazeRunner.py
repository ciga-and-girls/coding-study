N, M, K = map(int, input().split())
arr = [list(map(int, input().split())) for i in range(N)] # 벽의 내구도를 저장할 리스트
people = [[0] * N for i in range(N)] #리스트에 위치하는 사람 수를 저장할 리스트
moving = 0 #총 이동 수를 저장할 변수

for i in range(M):
    r, c = map(int, input().split())
    people[r-1][c-1] += 1 #people 리스트에 위치하는 사람 수 1씩 더하기

exit_r, exit_c = map(int, input().split())
exit_r -= 1
exit_c -= 1
arr[exit_r][exit_c] = -1 # 출구 정보 저장

for _ in range(K):
    temp = set() #사람이 있는 위치 정보를 저장할 임시 세트
    for i in range(N):
        for j in range(N):
            if arr[i][j] == -1: #만약 출구위치라면?
                exit_x, exit_y = i, j #exit_x, exit_y 갱신
            elif people[i][j] > 0: #만약 사람이 존재하는 위치라면?
                temp.add((i, j)) #temp에 임시 저장

    moves = {} #사람들의 동시 이동 정보를 저장할 딕셔너리
    for x, y in temp:
        new_x, new_y = x, y #임시로 new_x, new_y에 x, y 할당
        if x == exit_x: #x 좌표(r 좌표)가 같을 때
            if y < exit_y: # y 좌표 비교 -> y가 출구 y좌표보다 작으면
                if arr[x][y+1] <= 0: #(x, y+1)로 이동 가능하다면
                    new_y += 1 #좌표 이동
            else: #만약 y 좌표가 출구 y좌표보다 크다면
                if arr[x][y-1] <= 0: #(x, y-1)로 이동 가능하다면
                    new_y -= 1 #좌표 이동
        elif x < exit_x: #만약 x좌표가 출구의 x좌표보다 작다면?
            if arr[x+1][y] <= 0: #x좌표 이동이 가능한지부터 판단.
                new_x += 1 #이동 가능하다면 좌표 이동
            else: #만약 x좌표 이동이 불가하다면 y좌표 비교
                if y < exit_y and arr[x][y+1] <= 0: #y 좌표 이동 가능하다면 좌표 이동
                    new_y += 1
                elif y > exit_y and arr[x][y-1] <= 0:
                    new_y -= 1
        else: #만약 x좌표의 출구가 x좌표보다 크다면?
            if arr[x-1][y] <= 0: #x좌표 이동 가능 여부 판단
                new_x -= 1
            else: #위와 동일.
                if y < exit_y and arr[x][y+1] <= 0:
                    new_y += 1
                elif y > exit_y and arr[x][y-1] <= 0:
                    new_y -= 1
        
        if x != new_x or y != new_y: #만약 이동 했다면?
            moving += people[x][y] #그 좌표에 있었던 사람 수만큼 moving에 더하기
        cnt = people[x][y] #cnt에 이동한 사람 수 저장
        
        if new_x != exit_x or new_y != exit_y: #만약 출구에 도착하지 않았다면
            if (new_x, new_y) not in moves.keys(): #만약 아직 moves에 좌표가 저장되지 않았다면
                moves[(new_x, new_y)] = 0 #좌표 key 할당
            moves[(new_x, new_y)] += cnt #사람 수 만큼 value 더하기
        #출구에 도착한 사람들은 moves에 저장하지 않으므로, 자동으로 출구로 빠져나가게 됨

    new_people = [[0] * N for i in range(N)] #새로운 people 배열 생성
    for (x, y), cnt in moves.items(): #moves에 있는 좌표, 사람 수에 대하여
        new_people[x][y] += cnt #새로운 people 배열에 위치 정보, 사람 수 저장하기
    people = new_people #people 배열 갱신
    
    target = [float('inf'), float('inf'), float('inf')] #임시 target 리스트

    for (x, y) in moves.keys(): #moves에 저장되어있는 사람들이 존재하는 위치에 대해서
        x_dist, y_dist = abs(x - exit_x), abs(y - exit_y) #x, y 거리 계산

        dist = max(x_dist, y_dist) #둘 중에 큰 값을 dist에 할당
        min_r = max(0, max(x, exit_x) - dist) #min_r, min_c에 각각 가장 작은 r, c 좌표 할당
        min_c = max(0, max(y, exit_y) - dist)

        if min_r + dist >= N: min_r = N - dist - 1 #만약 좌표 범위를 넘어갔을 시에, 재할당
        if min_c + dist >= N: min_c = N - dist - 1

        if [dist, min_r, min_c] < target: # target과 비교하여 작으면 target에 할당
            target = [dist, min_r, min_c]
        

    if moves: #만약 사람이 남아 있다면 회전 로직 실행
        size, min_r, min_c = target

        new_arr = [row[:] for row in arr] #new_arr, new_people 복사
        new_people = [row[:] for row in people]

        for i in range(size+1): #new_arr, new_people에 회전한 값을 각각 할당
            for j in range(size+1):
                new_arr[min_r + j][min_c + size - i] = arr[min_r + i][min_c + j]
                new_people[min_r + j][min_c + size - i] = people[min_r + i][min_c + j]

        for i in range(size+1):
            for j in range(size+1):
                if new_arr[min_r + i][min_c + j] > 0: #만약 회전한 좌표 중 벽이 있다면
                    new_arr[min_r + i][min_c + j] -= 1 #벽 내구도 1 차감
        
        arr = new_arr #arr, people 배열 재할당
        people = new_people

        new_exit_x = min_r + exit_y - min_c #exit의 위치도 회전
        new_exit_y = min_c + size - exit_x + min_r
        exit_x, exit_y = new_exit_x, new_exit_y
        arr[exit_x][exit_y] = -1 #exit 위치 저장
    
print(moving)
print(exit_x + 1, exit_y + 1)
        
        