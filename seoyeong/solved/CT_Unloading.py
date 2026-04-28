n, m = map(int, input().split())
arr = [[0] * n for i in range(n)]

box = {} #box의 정보를 저장할 딕셔너리

def down(k, h, w, c, arr):
    row = 0
    for r in range(n-h+1): #탐색할 최대 높이는 n-h+1로 설정
        idx = True
        for i in range(r, r+h): #행은 r부터 r+h까지
            for j in range(c, c+w): #열은 c부터 c+w까지
                if arr[i][j] != 0: #만약 다른 박스가 있다면
                    idx = False #idx는 False로 바꾸기
                    break
            if not idx:
                break
        if idx: #만약 idx가 True라면 row에 r 할당
            row = r #row에는 박스가 들어갈 수 있는 최대의 행 값이 할당되게 됨
        else:
            break
    
    for i in range(row, row+h):
        for j in range(c, c+w):
            arr[i][j] = k #박스 넣기

def left():
    temp = []
    for i in range(n):
        for j in range(n):
            if arr[i][j] != 0:
                if arr[i][j] not in temp:
                    temp.append(arr[i][j]) #왼쪽에서 보이는 박스들의 번호를 일단 다 temp에 저장
                break
    temp.sort() #박스 번호를 오름차순으로 정렬
    for i in temp: #박스 하나씩 탐색
        can_out = True
        for x in range(n):
            for y in range(n):
                if arr[x][y] == i:
                    for k in range(n):
                        if y - k >= 0:
                            if arr[x][y-k] != 0 and arr[x][y-k] != i: # 만약 왼쪽으로 가려진 박스가 있다면
                                can_out = False #can_out을 False로 변경
                                break
                        else:
                            break
        if can_out: #만약 나갈 수 있는 박스를 찾으면 리턴
            return i

def right(): #오른쪽 동일
    temp = []
    for i in range(n):
        for j in range(n-1, -1, -1):
            if arr[i][j] != 0:
                if arr[i][j] not in temp:
                    temp.append(arr[i][j])
                break

    temp.sort()
    for i in temp:
        can_out = True
        for x in range(n):
            for y in range(n):
                if arr[x][y] == i:
                    for k in range(n):
                        if y + k < n:
                            if arr[x][y+k] != 0 and arr[x][y+k] != i:
                                can_out = False
                                break
                        else:
                            break
        if can_out:
            return i

def delete(k, arr): #arr에서 k번호를 가진 박스를 삭제하는 함수
    for x in range(n):
        for y in range(n):
            if arr[x][y] == k:
                arr[x][y] = 0

def push(arr): #맨 아래 행부터 탐색해서 박스 번호를 temp에 저장하는 함수
    temp = []
    for i in range(n-1, -1, -1):
        for j in range(n):
            if arr[i][j] != 0 and arr[i][j] not in temp:
                temp.append(arr[i][j])
    return temp


for _ in range(m):
    k, h, w, c = map(int, input().split())
    down(k, h, w, c-1, arr)
    box[k] = (h, w, c)

result = []


while len(result) != m:
    v = left()
    result.append(v)

    if len(result) == m:
        break

    delete(v, arr)
    new_arr = [[0]*n for i in range(n)]

    temp = push(arr)

    for i in temp:
        down(i, box[i][0], box[i][1], box[i][2]-1, new_arr)

    arr = new_arr

    v = right()
    result.append(v)

    if len(result) == m:
        break

    delete(v, arr)
    new_arr = [[0] * n for i in range(n)]

    temp = push(arr)

    for i in temp:
        down(i, box[i][0], box[i][1], box[i][2]-1, new_arr)

    arr = new_arr
    
for i in result:
    print(i)
