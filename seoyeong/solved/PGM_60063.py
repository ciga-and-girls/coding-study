from collections import deque
def solution(board):
    answer = 0
    
    delta1 = [1, -1, 0, 0]
    delta2 = [0, 0, 1, -1]
    
    n = len(board)
    visited = set()
    visited.add(((0, 0), (0, 1)))
                
    q = deque()
    q.append((((0, 0), (0, 1)), 0)) # ((x1, y1), (x2, y2)), t 형태로 q에 저장
    
    while q:
        ((x1, y1), (x2, y2)), t = q.popleft()
        
        if (x1 == n-1 and y1 == n-1) or (x2 == n-1 and y2 == n-1): #만약 목표 지점에 도달했다면
            answer = t #answer 갱신 후 break
            break
        
        for i in range(4): # 현재 모양 그대로 상하좌우 이동
            x11, y11 = x1 + delta1[i], y1 + delta2[i]
            x22, y22 = x2 + delta1[i], y2 + delta2[i]
            
            if 0 <= x11 < n and 0 <= y11 < n and 0 <= x22 < n and 0 <= y22 < n: #좌표 내에 있고
                if board[x11][y11] == 0 and board[x22][y22] == 0: #좌표가 전부 이동가능하다면
                    if tuple(sorted([(x11, y11), (x22, y22)])) not in visited: #그리고 아직 방문하지 않았다면
                        visited.add(tuple(sorted([(x11, y11), (x22, y22)]))) #visited 처리
                        q.append((tuple(sorted([(x11, y11), (x22, y22)])), t+1)) #q에 넣기
                        #(0, 0), (0, 1)이든 (0, 1), (0, 0)이든 둘 다 같기 때문에 sorted로 순서 보정
        
        #회전
        if x1 == x2: #가로로 놓여있는 상황일 때
            for i in [1, -1]:
                x11 = x1 + i
                
                if 0 <= x11 < n and board[x11][y1] == 0 and board[x11][y2] == 0: #만약 회전 가능한 상황이라면
                    if tuple(sorted([(x1, y1), (x11, y1)])) not in visited: #아직 방문하지 않았다면
                        visited.add(tuple(sorted([(x1, y1), (x11, y1)]))) #방문처리 (y1 기준 회전)
                        q.append((tuple(sorted([(x1, y1), (x11, y1)])), t+1))
                    if tuple(sorted([(x11, y2), (x2, y2)])) not in visited:
                        visited.add(tuple(sorted([(x11, y2), (x2, y2)]))) #방문처리 (y2 기준 회전)
                        q.append((tuple(sorted([(x11, y2), (x2, y2)])), t+1)) 
        else: #세로로 놓여있는 상황일 때
            for i in [1, -1]:
                y11 = y1 + i
                
                if 0 <= y11 < n and board[x1][y11] == 0 and board[x2][y11] == 0:
                    if tuple(sorted([(x1, y11), (x1, y2)])) not in visited:
                        visited.add(tuple(sorted([(x1, y11), (x1, y2)]))) #방문처리(x1 기준 회전)
                        q.append((tuple(sorted([(x1, y11), (x1, y2)])), t+1))
                    if tuple(sorted([(x2, y11), (x2, y2)])) not in visited:
                        visited.add(tuple(sorted([(x2, y11), (x2, y2)]))) #방문처리 (x2 기준 회전)
                        q.append((tuple(sorted([(x2, y11), (x2, y2)])), t+1))
    return answer