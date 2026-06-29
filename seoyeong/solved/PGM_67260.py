from collections import deque
def solution(n, path, order):
    answer = True
    
    graph = [[] for i in range(n)]
    for a, b in path: #그래프 연결
        graph[a].append(b)
        graph[b].append(a)
        
    parent = [-1] * n #선행이 최대 1개까지만 있을 수 있으므로 일단 -1로 채우기
    child = [-1] * n
    
    for a, b in order:
        parent[b] = a #부모 노드 기록
        child[a] = b #자식 노드 기록
        
    wait = [[] for i in range(n)] #대기 관리 리스트
    visited = [False] * n

    q = deque()
    q.append(0)
    
    while q:
        if parent[0] != -1: break #만일 0번 노드에 부모가 존재할 경우 이동 자체가 불가하므로 break
        v = q.popleft() 
        
        if visited[v]: continue #이미 방문했다면 다시 확인할 필요가 없으므로 continue
        visited[v] = True #방문처리
                
        for c in graph[v]:#연결된 노드들에 대하여
            if visited[c]: continue #만약 이미 방문이 되었다면 continue
            if parent[c] != -1:# 아직 방문이 되지 않고, 부모 노드가 있다면
                if visited[parent[c]]: #근데 만약 부모노드가 이미 방문처리 되었다면?
                    q.append(c) #큐에 넣기
                else: #아직 부모 노드가 방문처리되지 않았다면
                    wait[parent[c]].append(c) #부모노드 방문했을 때 바로 같이 방문할 수 있도록 대기 리스트에 append
            else:
                q.append(c) #만약 부모 노드가 없다면 바로 방문이 가능하므로 큐에 넣기
                
        for c in wait[v]:#대기 리스트 확인해서
            q.append(c) #안에 있는 노드 큐에 넣기
            
    for i in visited:
        if not i: answer = False
    return answer