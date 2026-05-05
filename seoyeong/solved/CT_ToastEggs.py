from collections import deque

n, L, R = map(int, input().split())
arr = [list(map(int, input().split())) for i in range(n)]

delta1 = [1, -1, 0, 0]
delta2 = [0, 0, 1, -1]

t = 0

while True:
    visited = [[False] * n for i in range(n)]
    total = []

    for cx in range(n):
        for cy in range(n):
            if not visited[cx][cy]:
                egg = [(cx, cy)]
                q = deque()
                q.append((cx, cy))
                visited[cx][cy] = True
                v = arr[cx][cy]

                while q:
                    x, y = q.popleft()

                    for i in range(4):
                        x1 = x + delta1[i]
                        y1 = y + delta2[i]

                        if 0 <= x1 < n and 0 <= y1 < n and not visited[x1][y1]:
                            if L <= abs(arr[x][y] - arr[x1][y1]) <= R:
                                q.append((x1, y1))
                                visited[x1][y1] = True
                                egg.append((x1, y1))
                                v += arr[x1][y1]

                if len(egg) > 1:
                    total.append((egg, v // len(egg)))
                 
    if len(total) == 0:
        break
    for egg, v in total:
        for x, y in egg:
            arr[x][y] = v
    
    t += 1
    

print(t)

