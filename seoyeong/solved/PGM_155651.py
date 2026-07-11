import heapq
def solution(book_time):
    answer = 0
    book_time.sort()

    end = []    
    
    for s, e in book_time:
        start_h, start_m = int(s[:2]), int(s[3:])
        end_h, end_m = int(e[:2]), int(e[3:])
        
        start_t = start_h * 60 + start_m
        end_t = end_h * 60 + end_m + 10
        
        if len(end) == 0 or end[0] >  start_t:
            heapq.heappush(end, end_t)
            answer = max(answer, len(end))
        elif end[0] <= start_t:
            heapq.heappop(end)
            heapq.heappush(end, end_t)
    
    return answer