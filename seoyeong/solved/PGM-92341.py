def solution(fees, records):
    answer = []
    time_dict = {} #차량 별 이용 시간을 저장할 딕셔너리
    cars_dict = {} #차량의 입차 시간을 저장할 딕셔너리
    
    for str in records:
        time = str[:5] # 시간 분리
        number = str[6:10] #차량 번호 분리
        state = str[11:] #IN OUT 분리
        
        if state == 'IN': #만약 입차라면
            cars_dict[number] = time #딕셔너리에 저장
            continue
        else: #만약 출차라면
            in_time = cars_dict.pop(number) #입차 시간 pop
            
            out_h = int(time[:2]) #출차 시
            out_m = int(time[3:]) #출차 분
            in_h = int(in_time[:2]) #입차 시
            in_m = int(in_time[3:]) #입차 분
            
            end = out_h * 60 + out_m #분으로 통일 계산
            start = in_h * 60 + in_m
            
            remainder = end - start #총 주차 시간
            
            if number not in time_dict.keys():
                time_dict[number] = 0
            time_dict[number] += remainder #차량 번호에 총 주차 시간 더하기
    
    for number, time in cars_dict.items(): #만약 출차 시간이 찍히지 않은 차가 남아있다면
        start_h = int(time[:2]) 
        start_m = int(time[3:])
        
        end = 23 * 60 + 59
        start = start_h * 60 + start_m
        
        remainder = end - start # 출차 시간을 23시 59분으로 계산하여 총 주차 시간 구하기
        
        if number not in time_dict.keys():
            time_dict[number] = 0
        time_dict[number] += remainder #차량 번호에 총 주차 시간 더하기
            
    sorted_dict = dict(sorted(time_dict.items())) #딕셔너리를 차량 번호 순으로 sort
        
    for number, value in sorted_dict.items(): #여기서 주차 요금 계산
        fee = fees[1] #기본 요금 할당
        new_value = value - fees[0] #기본 주차 시간을 뺀 나머지 시간

        if value > fees[0]: #만약 기본 주차 시간보다 많은 시간을 주차했다면
            if new_value % fees[2] != 0: #나머지가 0이 아니라면 추가 비용 할당
                fee += fees[3]
            fee += fees[3] * (new_value // fees[2]) #나머지 분 구해서 요금에 더하기

        answer.append(fee)
    
    return answer
