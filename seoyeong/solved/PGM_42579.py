def solution(genres, plays):
    answer = []
    n = len(plays)
    
    genre_dict = {}
    play_dict = {}
    
    for i in range(n):
        if genres[i] not in genre_dict:
            genre_dict[genres[i]] = 0
            play_dict[genres[i]] = []
        
        genre_dict[genres[i]] += plays[i]
        play_dict[genres[i]].append([plays[i], i])
        
    for key, value in play_dict.items():
        new_value = value
        new_value.sort(key=lambda x: (-x[0], x[1]))
        play_dict[key] = new_value
            
    genre_sort = sorted(genre_dict, key=lambda x: genre_dict[x], reverse = True)
    
    for genre in genre_sort:
        if len(play_dict[genre]) < 2:
            answer.append(play_dict[genre][0][1])
        else:
            answer.append(play_dict[genre][0][1])
            answer.append(play_dict[genre][1][1])
    return answer