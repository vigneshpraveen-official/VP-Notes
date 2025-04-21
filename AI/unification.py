t1=tuple(input("Enter the variables of the first atomic sentence: ").split())
t2=tuple(input("Enter the variables of the Second atomic sentence: ").split())
a=1
f="{ "
if (t1[0]==t2[1] or (t1[1]==t2[0])):
    a=0
if a==0:
    print("Unification is not applicable!")
else:
    for i in range(0,len(t1)):
        if len(t1[i])==1:
            f+=t1[i]+"/"+t2[i]+" "
        elif len(t2[i])==1:
            f+=t2[i]+"/"+t1[i]+" "
    print(f+"}")
    print("Unification is Successfull")