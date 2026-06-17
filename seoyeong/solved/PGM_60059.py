def solution(key, lock):
    answer = False
    n = len(lock)
    m = len(key)
    
    arr = [[0] * (3*n) for i in range(3*n)] # (3*n, 3*n) 크기의 빈 배열 먼저 생성
    for i in range(n):
        for j in range(n):
            arr[n+i][n+j] = lock[i][j] # (n~2n, n~2n) 에 원래의 lock 배열 할당하기
    # 상, 하, 좌, 우, 대각선으로 n*n크기의 빈 배열을 생성하면 격자 밖으로 빠져나가는 key의 인덱스 처리가 쉬워짐!
            
    for _ in range(4): # 회전 4번
        key = rotate(key, m)
        
        for i in range(n+m-1): # 자물쇠 밖으로 빠져나가는 key의 최대 인덱스까지 생각하여 최대 범위를 n+m-1까지 잡음
            for j in range(n+m-1):
                for x in range(m): # key배열 크기만큼 인덱스 잡기
                    for y in range(m):
                        arr[n-m+1+i+x][n-m+1+j+y] += key[x][y] # 각 격자 범위에 맞춰 key 값 더하기
                
                check = True # 체크 기본값은 true
                for x in range(n, 2*n): #원래의 자물쇠 범위 탐색
                    for y in range(n, 2*n):
                        if arr[x][y] != 1: #하나라도 맞지 않으면
                            check = False #check를 false로 변경
                            break
                
                if check: #만약 전부 맞는 경우가 있었을 시
                    answer = True #answer를 true로 변경
                    break
                else: #만약 전부 맞는 경우가 아니라면
                    for x in range(m): #똑같은 범위에서 더한 key 값 다시 빼주기
                        for y in range(m):
                            arr[n-m+1+i+x][n-m+1+j+y] -= key[x][y]
                    
    return answer

def rotate(matrix, m): #회전 함수
    return [[matrix[m-1-j][i] for j in range(m)] for i in range(m)]