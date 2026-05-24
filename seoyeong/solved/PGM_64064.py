def solution(user_id, banned_id):
    idx = 0
    id_dict = {} #banned_id의 조건을 만족하는 user_id를 저장할 딕셔너리
    result = set()
    
    for id1 in banned_id:
        id_dict[idx] = [] #banned_id 순서에 대해서 딕셔너리 키 생성(value는 빈 리스트)
        for id2 in user_id: #user_id 안의 id2에 대해서 조건 만족 여부 확인
            if len(id1) != len(id2): #길이가 다르면 continue
                continue
            match = True
            for char in range(len(id1)): #글자 하나하나 비교
                if not(id1[char] == id2[char] or id1[char] == '*'): #만약 두 글자가 같지 않거나, id1의 글자가 *이 아니라면
                    match = False #매칭 여부 False로 변경
                    break
            if match:
                id_dict[idx].append(id2) #매칭 여부가 True일 때만 append
        idx += 1 #banned_id의 id 판별 하나가 끝났으니, 순서 += 1
        
    temp = []
    def func(level):
        
        if level == idx:
            
            result.add(tuple(sorted(temp))) #정렬 후 튜플로 반환해서 result에 저장
            
            return
        
        for i in id_dict[level]: #딕셔너리에 저장되어있는 조건 만족 id에 대해서
            if i not in temp: #아직 temp에 저장되어 있지 않다면(중복 아이디가 아니라면)
                temp.append(i) #temp에 append
                func(level+1)
                temp.pop()
                
    func(0)
    
    return len(result) #마지막은 result의 길이를 리턴