class Solution:
    def minCostConnectPoints(self, points: List[List[int]]) -> int:
        n = len(points)
        parent = list(range(n))

        def find(x):
            if parent[x] != x:
                parent[x] = find(parent[x])
            return parent[x]
        
        def union(x, y):
            root_x, root_y = find(x), find(y)
            
            if root_x != root_y:
                parent[root_y] = root_x
                return True
            return False

        edges = []

        for i in range(n):
            for j in range(i+1, n):
                if i == j: continue

                [x1, y1], [x2, y2] = points[i], points[j]
                d = abs(x1 - x2) + abs(y1 - y2)
                edges.append([d, i, j])

        edges.sort()

        answer = 0
        cnt = 0

        for cost, i, j in edges:
            if union(i, j):
                answer += cost
                cnt += 1

                if cnt == n-1:
                    break
        
        return answer