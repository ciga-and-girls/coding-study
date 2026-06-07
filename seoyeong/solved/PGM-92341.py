def solution(fees, records):
    answer = []
    time_dict = {}
    cars_dict = {}
    
    for str in records:
        time = str[:5]
        number = str[6:10]
        state = str[11:]
        
        if state == 'IN':
            cars_dict[number] = time
            continue
        else:
            in_time = cars_dict.pop(number)
            
            out_h = int(time[:2])
            out_m = int(time[3:])
            in_h = int(in_time[:2])
            in_m = int(in_time[3:])
            
            end = out_h * 60 + out_m
            start = in_h * 60 + in_m
            
            remainder = end - start
            
            if number not in time_dict.keys():
                time_dict[number] = 0
            time_dict[number] += remainder
    
    for number, time in cars_dict.items():
        start_h = int(time[:2])
        start_m = int(time[3:])
        
        end = 23 * 60 + 59
        start = start_h * 60 + start_m
        
        remainder = end - start
        
        if number not in time_dict.keys():
            time_dict[number] = 0
        time_dict[number] += remainder
            
    sorted_dict = dict(sorted(time_dict.items()))
        
    for number, value in sorted_dict.items():
        fee = fees[1]
        new_value = value - fees[0]

        if value > fees[0]:
            if new_value % fees[2] != 0:
                fee += fees[3]
            fee += fees[3] * (new_value // fees[2])

        answer.append(fee)
    
    return answer
