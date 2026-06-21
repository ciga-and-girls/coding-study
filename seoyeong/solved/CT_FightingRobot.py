from collections import deque
n = int(input())
arr = [list(map(int, input().split())) for i in range(n)]
delta1 = [-1, 0, 1, 0]
delta2 = [0, -1, 0, 1]
result = 0
level = 2
catch = 0

robot_x, robot_y = 0, 0
for i in range(n):
    for j in range(n):
        if arr[i][j] == 9:
            robot_x, robot_y = i, j
            arr[i][j] = 0
            break

while True:
    q = deque()
    q.append((robot_x, robot_y, 0))
    visited = [[False] * n for i in range(n)]
    visited[robot_x][robot_y] = True
    can_fight = False #몬스터를 잡을 수 있는지 없는지를 확인할 변수

    monster = set() #잡을 수 있는 몬스터 정보를 관리할 세트

    while q:
        x, y, s = q.popleft()
        
        if arr[x][y] != 0 and arr[x][y] < level: #만약 x, y 좌표가 0이 아니고 레벨보다 작다면, 즉 잡을 수 있는 몬스터라면
            monster.add((s, x, y)) #몬스터 세트에 애드
            can_fight = True #몬스터를 잡을 수 있으니 True로 변경

        for i in range(4):
            x1 = x + delta1[i]
            y1 = y + delta2[i]

            if 0 <= x1 < n and 0 <= y1 < n and arr[x1][y1] <= level and not visited[x1][y1]:
                visited[x1][y1] = True
                q.append((x1, y1, s+1))

    if not can_fight:
        break

    monster = list(monster) #sort를 위해 리스트로 변환
    monster.sort()
    s, x, y = monster[0] #sort 정렬 후 맨 앞에 있는 s, x, y가 자동으로 목표 몬스터 정보가 됨
    robot_x, robot_y = x, y #robot 좌표 갱신
    arr[x][y] = 0 #몬스터를 잡았으니 0으로 변경
    result += s #걸린 시간 더하기

    catch += 1

    if catch == level: #만약 catch랑 level이 똑같다면 level 1올리고 catch는 다시 0으로 변경
        level += 1
        catch = 0
    
    
print(result)
    