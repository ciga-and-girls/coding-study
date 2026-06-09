def solution(players, m, k):
    answer = 0
    servers = [0] * 100
    live_server = 0
    
    for h in range(24):
        need = players[h] // m
        live_server = -= servers[h]
                
        if live_server < need:
            servers[h + k] += need-live_server
            answer += need-live_server
            live_server = need
            
    return answer