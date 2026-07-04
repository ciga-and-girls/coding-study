n, m, k = map(int, input().split())
plus = [list(map(int, input().split())) for i in range(n)]
arr = [[5] * n for i in range(n)]

delta1 = [1, -1, 0, 0, 1, 1, -1, -1]
delta2 = [0, 0, 1, -1, 1, -1, 1, -1]

virus = {} #(x, y) : {age:count} 형식

for i in range(m):
    r, c, age = map(int, input().split())
    virus[(r-1, c-1)] = {age: 1}

for _ in range(k):
    dead = {} #죽은 바이러스의 정보를 저장할 딕셔너리. (x, y) : {age:count} 형식
    new_virus = {} #virus의 성장 및 번식 정보를 저장할 딕셔너리(virus와 형식 같음)
    increase = [] #번식 예정인 바이러스의 정보를 저장할 리스트

    for (x, y), info in virus.items():
        for age in sorted(info.keys()): #info의 key가 age이므로 age가 작은 순으로 자동 sort됨
            cnt = info[age]
            if arr[x][y] >= age*cnt: #해당 칸 age 바이러스가 전부 성장이 가능한 경우
                if (x, y) not in new_virus: #new_virus에 아직 좌표 정보가 없는 경우
                    new_virus[(x, y)] = {age+1 : cnt} #새롭게 키와 밸류를 저장
                else: #new_virus에 해당 좌표정보가 저장되어 있는 경우
                    if age+1 in new_virus[(x, y)]: #해당 좌표 정보 내에 age+1 나이의 바이러스가 이미 존재하는 경우
                        new_virus[(x,y)][age+1] += cnt #cnt를 늘리기
                    else:#만약 아직 age+1 나이의 바이러스가 없다면
                        new_virus[(x,y)][age+1] = cnt #새롭게 밸류를 저장
                
                if (age+1) % 5 == 0: #만약 번식 예정인 바이러스라면
                    increase.append([x, y, age+1, cnt]) #increase 리스트에 저장

                arr[x][y] -= age*cnt #arr의 좌표에서 바이러스가 먹은 만큼 값 감소

            else: #만약 해당 칸 age 바이러스가 전부는 성장이 불가능한 경우
                survive_cnt = arr[x][y] // age #성장 가능한 바이러스의 수
                dead_cnt = cnt - survive_cnt #성장 하지 못하고 소멸 예정인 바이러스의 수
                
                if (x, y) not in dead: #만약 dead에 좌표 정보가 아직 저장되어있지 않은 경우
                    dead[(x, y)] = {age : dead_cnt} #새롭게 키와 밸류를 저장
                else: #만약 dead에 좌표 정보가 이미 저장되어 있는 경우
                    if age in dead[(x, y)]: #해당 age 나이의 바이러스가 있는지 확인
                        dead[(x, y)][age] += dead_cnt #만약 있으면 dead_cnt만큼 증가
                    else: #만약 해당 age 나이의 바이러스가 아직 없다면
                        dead[(x, y)][age] = dead_cnt #새롭게 밸류를 저장

                arr[x][y] %= age # arr의 좌표에서 바이러스가 먹은 만큼 값 감소
                if survive_cnt != 0: #만약 해당 바이러스가 전부 소멸하는 경우가 아니라면
                    if (x, y) not in new_virus: #위와 똑같은 로직으로 new_virus에 생존한 만큼 저장
                        new_virus[(x, y)] = {age+1: survive_cnt}
                    else:
                        if age+1 in new_virus[(x, y)]:
                            new_virus[(x, y)][age+1] += survive_cnt
                        else:
                            new_virus[(x, y)][age+1] = survive_cnt

                    if (age+1) % 5 == 0:
                        increase.append([x, y, age+1, survive_cnt])

    for (x, y), info in dead.items(): #죽은 virus를 순회
        for age in sorted(info.keys()):
            cnt = info[age]

            feed = age//2 #새롭게 양분이 되는 값
            arr[x][y] += feed * cnt #feed * cnt만큼 arr 좌표에 저장

    for x, y, age, cnt in increase: #바이러스 번식
        for i in range(8):
            x1 = x + delta1[i]
            y1 = y + delta2[i]

            if 0 <= x1 < n and 0 <= y1 < n: #만약 주변 8칸이 격자 내에 있다면
                if (x1, y1) not in new_virus.keys(): #위와 똑같은 로직으로 new_virus에 저장
                    new_virus[(x1, y1)] = {1:cnt}
                else:
                    if 1 in new_virus[(x1, y1)]:
                        new_virus[(x1, y1)][1] += cnt
                    else:
                        new_virus[(x1, y1)][1] = cnt
    
    for x in range(n):
        for y in range(n):
            arr[x][y] += plus[x][y] #증가하는 값만큼 arr 좌표에 증가

    virus = new_virus #virus 갱신

result = 0
for (x, y), info in virus.items():
    for age, cnt in info.items():
        result += cnt #virus 수만큼 result 증가

print(result)