n, k = map(int, input().split())
arr = list(map(int, input().split()))

people = [False] * n

t = 0
while True:
    t += 1

    people.insert(0, people.pop())
    arr.insert(0, arr.pop())

    if people[n-1]:
        people[n-1] = False
    
    for i in range(n-2, -1, -1):
        if people[i]:
            if not people[i+1] and arr[i+1] > 0:
                people[i] = False
                people[i+1] = True
                arr[i+1] -= 1
    
    if people[n-1]:
        people[n-1] = False

    if not people[0] and arr[0] > 0:
        people[0] = True
        arr[0] -= 1
    
    cnt = 0
    for i in arr:
        if i == 0:
            cnt += 1
    
    if cnt >= k:
        break

print(t)
