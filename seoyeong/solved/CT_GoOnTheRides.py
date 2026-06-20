n = int(input())

arr = [[0]*n for i in range(n)]
like = {}
score = {0:0, 1:1, 2:10, 3:100, 4:1000}
delta1 = [1, -1, 0, 0]
delta2 = [0, 0, 1, -1]
result = 0

for i in range(n*n):
    v = list(map(int, input().split()))

    like[v[0]] = v[1:]

    seat = []

    for x in range(n):
        for y in range(n):
            like_num = 0
            blank = 0
            if arr[x][y] != 0:
                continue
            for i in range(4):
                x1 = x + delta1[i]
                y1 = y + delta2[i]

                if 0 <= x1 < n and 0 <= y1 < n:
                    if arr[x1][y1] == 0:
                        blank += 1
                    elif arr[x1][y1] in like[v[0]]:
                        like_num += 1

            seat.append((-like_num, -blank, x, y))
    
    seat.sort()
    a, b, x, y = seat.pop(0)
    arr[x][y] = v[0]
    
for x in range(n):
    for y in range(n):
        cnt = 0
        for i in range(4):
            x1 = x + delta1[i]
            y1 = y + delta2[i]

            if 0 <= x1 < n and 0 <= y1 < n:
                if arr[x1][y1] in like[arr[x][y]]:
                    cnt += 1
        
        result += score[cnt]

print(result)