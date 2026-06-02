def solution(n, works):
    answer = 0
    if sum(works) <= n:
        return 0
    
    counts = {}
    
    for w in works:
        if w not in counts.keys():
            counts[w] = 0
        counts[w] += 1
    
    max_w = max(works)
    
    while n > 0:
        if max_w == 0: break
        
        if max_w in counts and counts[max_w] > 0:
            minus = min(n, counts[max_w])
            counts[max_w] -= minus
            n -= minus
            
            if max_w-1 not in counts.keys():
                counts[max_w-1] = 0
            counts[max_w-1] += minus
            
            if counts[max_w] == 0:
                max_w -= 1
                
    for num, cnt in counts.items():
        answer += cnt * (num**2)
        
    return answer