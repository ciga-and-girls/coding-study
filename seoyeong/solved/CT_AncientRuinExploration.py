from collections import deque
def turn_func(x, y, new_arr):
    arr = [row[:] for row in new_arr]
    temp = arr[x-1][y-1:y+2]
    
    idx = y+1
    for i in range(x-1, x+2):
        arr[x-1][idx] = arr[i][y-1]
        idx -= 1

    idx = x-1
    for i in range(y-1, y+2):
        arr[idx][y-1] = arr[x+1][i]
        idx += 1
    
    idx = y-1
    for i in range(x+1, x-2, -1):
        arr[x+1][idx] = arr[i][y+1]
        idx += 1
    
    idx = x-1
    for i in temp:
        arr[idx][y+1] = i
        idx += 1

    visited = [[False]*5 for i in range(5)]
    cnt = 0

    q = deque()
    map = set()

    for cx in range(5):
        for cy in range(5):
            if not visited[cx][cy]:
                q.append((cx, cy))
                visited[cx][cy] = True
                v = 1
                position = set()
                position.add((cx, cy))

                while q:
                    new_x, new_y = q.popleft()

                    for i in range(4):
                        x1 = new_x + delta1[i]
                        y1 = new_y + delta2[i]

                        if 0 <= x1 < 5 and 0 <= y1 < 5 and arr[x1][y1] == arr[new_x][new_y]:
                            if not visited[x1][y1]:
                                v += 1
                                visited[x1][y1] = True
                                q.append((x1, y1))
                                position.add((x1, y1))
                
                if v >= 3: 
                    cnt += v
                    for xx, yy in position:
                        map.add((xx, yy))

    return arr, cnt, map

k, m = map(int, input().split())
arr = [list(map(int, input().split())) for i in range(5)]
number = list(map(int, input().split()))
idx = 0

delta1 = [1, -1, 0, 0]
delta2 = [0, 0, 1, -1]
for _ in range(k):
    result = 0
    arr_dict = {}
    order = []
    max_v = 0

    for x in range(1, 4):
        for y in range(1, 4):
            new_arr = [row[:] for row in arr]

            for i in range(1, 4):
                new_arr, v, map = turn_func(x, y, new_arr)
                if v >= max_v:
                    if v > max_v:
                        arr_dict = {}
                        order = []
                        max_v = v
                    arr_dict[(i, y, x)] = new_arr, map
                    order.append([i, y, x])

    if max_v == 0:
        break
    result += max_v
    order.sort()

    target_i, target_y, target_x = order[0]
    target_arr, target_map = arr_dict[target_i, target_y, target_x]

    while True:

        target_map = [(y, -x) for x, y in target_map] 
        target_map.sort()


        for y, x in target_map:
            target_arr[-x][y] = number[idx]
            idx += 1
        
        target_map = set()
        visited = [[False] * 5 for i in range(5)]
        q = deque()

        for x in range(5):
            for y in range(5):
                if not visited[x][y]:
                    target_position = set()
                    cnt = 1
                    q.append((x, y))
                    visited[x][y] = True
                    target_position.add((x, y))

                    while q:
                        cx, cy = q.popleft()

                        for i in range(4):
                            x1 = cx + delta1[i]
                            y1 = cy + delta2[i]

                            if 0 <= x1 < 5 and 0 <= y1 < 5 and not visited[x1][y1] and target_arr[x1][y1] == target_arr[cx][cy]:
                                visited[x1][y1] = True
                                target_position.add((x1, y1))
                                cnt += 1
                                q.append((x1, y1))
                    
                    if cnt >= 3:
                        for cx, cy in target_position:
                            target_map.add((cx, cy))
                        result += cnt
        if len(target_map) == 0:
            break
    arr = target_arr       
    print(result, end = ' ')