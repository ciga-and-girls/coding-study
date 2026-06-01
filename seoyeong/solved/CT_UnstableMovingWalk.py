n, k = map(int, input().split())
arr = list(map(int, input().split()))
people = [False] * (2*n)
t = 0

while True:
    broken = 0
    for i in arr:
        if i == 0:
            broken += 1

    if broken >= k:
        break
    
    t += 1
    temp = arr.pop()
    arr.insert(0, temp)

    temp = people.pop()
    people.insert(0, temp)
    
    if people[n-1]: people[n-1] = False

    for i in range(n-2, -1, -1):
        if people[i] and not people[i+1]:
            if arr[i+1] > 0:
                people[i] = False
                people[i+1] = True
                arr[i+1] -= 1
    
    if people[n-1] : people[n-1] = False

    if arr[0] > 0 and not people[0]:
        people[0] = True
        arr[0] -= 1


print(t)
