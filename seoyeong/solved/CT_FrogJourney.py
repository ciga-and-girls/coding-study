import heapq

n = int(input())
arr = [list(map(str, input())) for i in range(n)]
moving = int(input())
delta1 = [1, -1, 0, 0]
delta2 = [0, 0, 1, -1]

for _ in range(moving):
    start_x, start_y, end_x, end_y = map(int, input().split())
    start_x, start_y, end_x, end_y = start_x - 1, start_y - 1, end_x - 1, end_y - 1

    value = [[[float('inf')]*6 for j in range(n)] for i in range(n)]
    value[start_x][start_y][1] = 0
    q = []
    heapq.heappush(q, (0, start_x, start_y, 1))

    while q:
        t, x, y, jump = heapq.heappop(q)

        if x == end_x and y == end_y: break

        for i in range(4):
            for j in range(1, 6):
                x1 = x + delta1[i] * j
                y1 = y + delta2[i] * j

                if not (0 <= x1 < n and 0 <= y1 < n): break
                
                if j == jump:
                    new_t = t + 1
                elif j < jump:
                    new_t = t + 2
                else:
                    new_t = t + 1
                    for k in range(jump+1, j+1):
                        new_t += k**2
                
                if arr[x1][y1] == '.' and value[x1][y1][j] > new_t:
                    value[x1][y1][j] = new_t
                    heapq.heappush(q, (new_t, x1, y1, j))
                elif arr[x1][y1] == '#':
                    break

    result = float('inf')
    for i in range(1, 6):
        result = min(result, value[end_x][end_y][i])
    if result == float('inf'): result = -1
    
    print(result)