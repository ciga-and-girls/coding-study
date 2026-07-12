def solution(edges):
    answer = [0] * 4
    out_cnt = {} #각 노드가 끝 간선을 몇 개나 가졌는지 체크할 딕셔너리
    in_cnt = {}# 각 노드가 시작 간선을 몇 개나 가졌는지 체크할 딕셔너리
    
    in_check = [False] * 1000001
    out_check = [False] * 1000001
    
    for a, b in edges:#시작, 끝 간선에 대해서
        if not out_check[a]: #아직 끝 간선이 나오지 않았다면
            out_check[a] = True #끝 간선 나왔음 체크
            out_cnt[a] = 0 #딕셔너리 key, value(=0)생성
        out_cnt[a] += 1 # 간선 수 +1
        
        if not in_check[b]: #시작 간선은 위와 동일
            in_cnt[b] = 0
            in_check[b] = True
        in_cnt[b] += 1
    
    for i in range(1, 1000001):
        if in_check[i] or out_check[i]: #만약 간선을 가진 노드라면
            if in_check[i]: IN = in_cnt[i] #IN 간선 체크
            else: IN = 0 #만약 딕셔너리 내에 존재하지 않는다면 IN 간선 = 0
            if out_check[i]:OUT = out_cnt[i] #OUT 간선 동일
            else: OUT = 0
            
            if IN == 0 and OUT >= 2: answer[0] = i #IN 간선은 없는데 OUT 간선이 2 이상이라면 임의로 넣은 노드라고 판단.
            # 따라서 answer[0]에 저장
            elif OUT == 0: answer[2] += 1 #만약 OUT 간선이 0이라면 막대 모양 그래프의 끝 노드이므로 막대 모양 그래프 수 +1
            elif OUT == 2 and i != answer[0]: answer[3] += 1 #만약 OUT 간선이 2이고 임의로 집어넣은 노드가 아니라면 8자 그래프의 가운데 노드이므로 8자 그래프 수 +1
        else: #간선을 가지지 않은 노드라면 continue
            continue
            
    answer[1] = out_cnt[answer[0]] - answer[2] - answer[3] #임의로 집어넣은 노드의 OUT 간선의 수가 총 그래프의 수이므로, 총 그래프 수 - 8자 그래프 수 - 막대 그래프 수로 계산해서 도넛 모양 그래프 수 구하기
    return answer