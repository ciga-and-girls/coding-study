from collections import deque
n, t = map(int, input().split())
F = [list(map(str, input()))for i in range(n)]
B = [list(map(int, input().split())) for i in range(n)]
delta1 = [-1, 1, 0, 0]
delta2 = [0, 0, -1, 1]

for _ in range(t):
    for x in range(n):
        for y in range(n):
            B[x][y] += 1
    
    idx = 0
    G = [[-1] * n for i in range(n)]
    q = deque()
    president = {}

    for x in range(n):
        for y in range(n):
            if G[x][y] == -1:
                candidate = []
                q.append((x, y))
                candidate.append((-B[x][y], x, y))
                G[x][y] = idx

                while q:
                    cx, cy = q.popleft()

                    for i in range(4):
                        x1 = cx + delta1[i]
                        y1 = cy + delta2[i]

                        if 0 <= x1 < n and 0 <= y1 < n and F[x1][y1] == F[x][y] and G[x1][y1] == -1:
                            G[x1][y1] = idx
                            q.append((x1, y1))
                            candidate.append((-B[x1][y1], x1, y1))

                candidate.sort()
                president[idx] = [-candidate[0][0], candidate[0][1], candidate[0][2]]
                #신앙심 양수

                for i in range(1, len(candidate)):
                    B[candidate[i][1]][candidate[i][2]] -= 1

                B[candidate[0][1]][candidate[0][2]] += len(candidate) - 1
                idx += 1


    order = []
    for idx, [v, x, y] in president.items():
        order.append((len(F[x][y]), -B[x][y], x, y))
    order.sort()
    
    diffence = [[False] * n for i in range(n)]

    for length, v, x, y in order:
        if diffence[x][y]: continue

        power = B[x][y] -1
        dir = B[x][y] % 4
        B[x][y] = 1
        j = 0
        while j < n:
            if power <= 0: 
                power = 0
                break
            j += 1

            x1 = x + delta1[dir] * j
            y1 = y + delta2[dir] * j

            if not (0 <= x1 < n and 0 <= y1 < n): break
            if F[x1][y1] == F[x][y]: continue

            if B[x1][y1] < power:
                power -= B[x1][y1] + 1
                B[x1][y1] += 1
                F[x1][y1] = F[x][y]
            else:
                new_F = set()

                for f in F[x1][y1]:
                    new_F.add(f)
                for f in F[x][y]:
                    new_F.add(f)

                new_F = list(new_F)
                new_F.sort()
                new_F = tuple(new_F)

                F[x1][y1] = new_F

                B[x1][y1] += power
                power = 0
            diffence[x1][y1] = True

    menu = [('C', 'M', 'T'), ('C', 'T'), ('M', 'T'), ('C', 'M'), 'M', 'C', 'T']

    result = {}
    for m in menu:
        result[m] = 0

    for x in range(n):
        for y in range(n):
            result[F[x][y]] += B[x][y]

    for m in menu:
        print(result[m], end = ' ')
    print()        