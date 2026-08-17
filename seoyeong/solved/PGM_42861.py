def solution(n, costs):
    answer = 0
    parent = [0] * n
    
    for i in range(n):
        parent[i] = i
    
    def find(x):
        while parent[x] != x:
            parent[x] = parent[parent[x]]
            x = parent[x]
        return x        
    
    def union(x, y):
        root_x, root_y = find(x), find(y)
        if root_x == root_y:
            return False
        parent[root_x] = root_y
        return True
    
    costs.sort(key = lambda x: x[2])
    
    used = 0
    for a, b, cost in costs:
        if union(a, b):
            answer += cost
            used += 1
            if used == n-1:
                break
    
    return answer