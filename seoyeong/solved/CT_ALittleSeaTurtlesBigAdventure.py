from collections import deque
n, m, k = map(int, input().split())
arr = [list(map(int, input().split())) for i in range(n)]
delta1 = [0, 1, 0, -1]
delta2 = [1, 0, -1, 0]
turtle = {}
volcano = {}
result = {}

idx = 1
for i in range(m):
    r, c = map(int, input().split())
    turtle[idx] = [r, c]
    result[idx] = -1
    idx += 1

for i in range(k):
    r, c, P = map(int, input().split())
    volcano[(r, c)] = [P, 0]

t = 0
while t <= 100:
    t += 1
    for i in range(1, idx):
        if i not in turtle.keys():
            continue
        cx, cy = turtle[i]
        q = deque()
        visited = [[False]*n for j in range(n)]
        q.append((cx, cy, []))
        visited[cx][cy] = True

        while q:
            x, y, route = q.popleft()

            if x == n-1 and y == n-1:
                x1 = cx + delta1[route[0]]
                y1 = cy + delta2[route[0]]
                turtle[i] = [x1, y1]
                break
            
            for j in range(4):
                x1 = x + delta1[j]
                y1 = y + delta2[j]

                if 0 <= x1 < n and 0 <= y1 < n and not visited[x1][y1] and arr[x1][y1] == 0:
                    if (x1, y1) == (n-1, n-1) or [x1, y1] not in turtle.values():
                        q.append((x1, y1, route+[j]))
                        visited[x1][y1] = True
        
        if turtle[i] == [n-1, n-1]:
            result[i] = t
    
    for key in volcano.keys():
        volcano[key][1] += 10
    
    magma = [[0] * n for i in range(n)]
    explode = [[False]*n for i in range(n)]

    while True:
        can_explode = False
        for r, c in volcano.keys():
            if volcano[(r, c)][0] <= volcano[(r, c)][1] + magma[r][c] and not explode[r][c]:
                explode[r][c] = True
                can_explode = True
                magma[r][c] += volcano[(r, c)][1]
                power = volcano[(r, c)][0]
                volcano[(r, c)][1] = 0

                for i in range(4):
                    j = 0
                    while j < n:
                        j += 1
                        r1 = r + delta1[i] * j
                        c1 = c + delta2[i] * j

                        if 0 <= r1 < n and 0 <= c1 < n:
                            if arr[r1][c1] == 1: break
                            p = power // (2**j)
                            if p == 0: break
                            magma[r1][c1] += p
                        else: break

        if not can_explode: break
    
    new_turtle = {}
    for i, [x, y] in turtle.items():
        if (x, y) == (n-1, n-1):
            continue
        if magma[x][y] >= 20:
            arr[x][y] = 2
            continue
        new_turtle[i] = [x, y]
    turtle = new_turtle

    if len(new_turtle) == 0: break

for i in range(1, idx):
    print(result[i])
