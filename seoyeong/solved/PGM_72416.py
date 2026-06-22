def solution(sales, links):
    length = len(sales)
    
    children = [[] for i in range(length)] # 자식 노드를 관리할 리스트
    for x, y in links:
        children[x-1].append(y-1) #자식 노드 저장
        
    answer1, answer2 = func(0, children, sales)
    answer = min(answer1, answer2) #최솟값 저장
    return answer

# 해당 직원을 선택하는 경우와 선택하지 않는 경우의 최솟값을 return하는 함수
def func(value, children, sales):
    if not children[value]: #만약 자식이 없다면
        return (0, sales[value]) #그룹 내에서 선택하지 않는 경우 금액은 0, 선택하는 경우는 sales[value]
    
    choose = sales[value] #해당 직원을 선택했을 시에는 sales[value]를 choose의 기본값으로 설정
    not_choose = 0 #선택하지 않았을 때의 금액은 0
    chosen = False #아무도 선택하지 않았는지 판별하는 변수
    child_result = [] #아무도 선택하지 않았을 때, 이미 계산된 결과 중 가장 금액이 덜 드는 직원의 금액을 판별하기 위해 사용하는 리스트
    
    for c in children[value]: #자식 노드들 하나하나에 대해서 탐색
        not_ch, ch = func(c, children, sales) #재귀 -> 그래프를 타고 들어가서 가장 맨 밑의 그룹부터 탐색
        child_result.append((not_ch, ch)) #아무도 선택하지 않았을 경우를 대비하여 리스트에 저장
        choose += min(not_ch, ch) #일단 선택했을 때의 금액에 최솟값 더하기
        
        if ch <= not_ch: #만약 선택했을 때의 금액이 더 저렴하다면
            not_choose += ch # 그룹 내의 더 저렴한 다른 직원을 뽑으면 되므로 부모 노드의 직원은 뽑지 않음(not_choose) 변수에 더하기
            chosen = True #직원을 선택했으므로 True로 변경
        else:
            not_choose += not_ch #만약 뽑지 않았을 때가 더 저렴하다면 not_ch(뽑지 않음)을 더하기
    
    if not chosen: # 만약 아무도 뽑히지 않았다면
        # 어쨌든 그룹 내에서 한 명은 뽑아야 하니 뽑혔을 때의 금액 - 뽑히지 않았을 때의 금액이 가장 작은 직원 선택
        # 즉 추가되는 비용(뽑혔을 때의 금액-뽑히지 않았을 때의 금액)이 가장 작은 직원
        extra = min(ch - not_ch for not_ch, ch in child_result)
        not_choose += extra
    
    return(not_choose, choose)