n, m1, k = map(int, input().split())
atom = {}

delta1 = [-1, -1, 0, 1, 1, 1, 0, -1]
delta2 = [0, 1, 1, 1, 0, -1, -1, -1]

dir = {
    1 : [1, 3, 5, 7],
    0 : [0, 2, 4, 6]
}

for i in range(m1):
    x, y, m, s, d = map(int, input().split())
    atom[(x-1, y-1, d)] = [m, s]


for _ in range(k):
    new_atom = {}
    for (x, y, d), [m, s] in atom.items():
        x1 = x + delta1[d] * s
        y1 = y + delta2[d] * s

        x1 = x1 % n
        y1 = y1 % n

        if (x1, y1) not in new_atom.keys():
            new_atom[(x1, y1)] = [d, m, s, 1, -1] #방향, 질량, 속력, 합쳐진 원자 수, 델타 체크
        else:
            new_atom[(x1, y1)][1] += m
            new_atom[(x1, y1)][2] += s
            new_atom[(x1, y1)][3] += 1

            if new_atom[(x1, y1)][4] == -1:
                if new_atom[(x1, y1)][0] in dir[0] and d in dir[0]:
                    new_atom[(x1, y1)][4] = 0
                elif new_atom[(x1, y1)][0] in dir[1] and d in dir[1]:
                    new_atom[(x1, y1)][4] = 0
                else: new_atom[(x1, y1)][4] = 1
            elif new_atom[(x1, y1)][4] == 0:
                if new_atom[(x1, y1)][0] in dir[0] and d in dir[1]:
                    new_atom[(x1, y1)][4] = 1
                elif new_atom[(x1, y1)][0] in dir[1] and d in dir[0]:
                    new_atom[(x1, y1)][4] = 1
            

    atom = {}
    for (x, y), [d, m, s, cnt, idx] in new_atom.items():
        if cnt == 1:
            atom[(x, y, d)] = [m, s]
        else:
            new_m = m//5
            if new_m == 0: continue
            new_s = s // cnt

            if idx == 0:
                for i in dir[0]:
                    atom[(x, y, i)] = [new_m, new_s]
            else:
                for i in dir[1]:
                    atom[(x, y, i)] = [new_m, new_s]

result = 0
for (x, y, d), [m, s] in atom.items():
    result += m

print(result)
