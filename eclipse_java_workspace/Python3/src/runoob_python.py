# -*- coding: GBK -*-
#�ļ�����Ĭ��UTF-8 ����ָ��ΪGBK
print ("GBK ����")
"""
����ע�� ,����������,
���� ����˫���� 
"""
#��б��(\)��ʵ�ֶ������    ͬlinux �� [], {}, �� () �еĶ�����䣬����Ҫʹ�÷�б��(\)
#�������ͣ����������������������͸�����


print(r"this is a line with \n abc") # #�ַ���ǰ��r��R����\n����ʾ�������ǻ��С�

#unicode�ַ�������ǰ׺u��U�� �� u"this is an unicode string"��
print(u"unicode \u4e2d \u56fd  56fd ") 
print("\u4e2d \u56fd ")
paragraph = """����һ�����䣬
                �����ɶ������"""
 
str2="this " "is " "string" # "this " "is " "string"�ᱻ�Զ�ת��Ϊthis is string��
print(str2)

#inputStr = input("�����룺");
#print ("�������������: ", inputStr)


import sys; x = 'runoob'; sys.stdout.write(x + '\n') #ͬһ����ʹ�ö�����䣬���֮��ʹ�÷ֺ�(;)�ָ�

#print Ĭ������ǻ��еģ����Ҫʵ�ֲ�������Ҫ�ڱ���ĩβ���� end=":"
print("one",  end=":")
print( "two", end=":") 
#���one:two:

#��ĳ��ģ���е���������,��ʽΪ�� from somemodule import firstfunc, secondfunc, thirdfunc
#��ĳ��ģ���е�ȫ���������룬��ʽΪ�� from somemodule import *
from sys import argv,path 
#from math import *

#python -c "print('a')



#!/usr/bin/env python3
#����д���ɴ������� δ����???
#!/usr/bin/python3

#>>> help(max)  ������ʾ������Ϣ����

#������õ��ĵ��ַ�����  
print(max.__doc__)


#Python3 ����������׼���������ͣ�Number�����֣� String���ַ����� List���б� Tuple��Ԫ�飩 Sets�����ϣ� Dictionary���ֵ䣩
 #int��float��bool��complex����������


a, b, c, d = 20, 5.5, True, 4+3j
print(type(a), type(b), type(c), type(d))
a = 111
print(isinstance(a, int))
# True �� False ����ɹؼ����ˣ������ǵ�ֵ���� 1 �� 0�����ǿ��Ժ��������
print(True+10)

#del���ɾ��һЩ�������á�
del a,b,c,d
#print(a)#����is not defined
  
print(2 / 4)  # �������õ�һ�������� 0.5
print(2 // 4) # �������õ�һ������   0
print(2 ** 5)# �˷�  32

str1 = 'Runoob'
print (str1[2:5])     # ����ӵ�������ʼ����������ַ�
print (str1[0:-1])
print (str1[2:]) 
print (str1[0]) 
#---list
listObj = [ 'abcd', 786 , 2.23, 'runoob', 70.2 ]
tinylist = [123, 'runoob']
  
print (listObj[2:])        # ����ӵ�����Ԫ�ؿ�ʼ������Ԫ��
print (tinylist * 2)    # һ���б�Ԫ����2��
print (listObj + tinylist) # �����б�

del listObj[2]  #Ҳ����del listObj
print (listObj)
a=[['a', 'b', 'c'], [1, 2, 3]]
print(a[0][0])

aTuple = (123, 'Google', 'Runoob', 'Taobao')
list1 = list(aTuple)  #ת��
print ("�б�Ԫ�� : ", list1)
#list copy() ǳ���Ƶ���a[:]��
#append��pop��ϵ�ջʹ��

for i, v in enumerate(['tic', 'tac', 'toe']):   #����λ�úͶ�Ӧֵ����ʹ�� enumerate() ����
   print(i, v)


#---tuple
t1 = ( 'abcd', 786 , 2.23, 'runoob', 70.2  )

#t1[0] = 100 #�޸�Ԫ��Ԫ�ز����ǷǷ��ġ�
tinytuple = (123, 'runoob')
   
print (t1[1:3])        # ����ӵڶ���Ԫ�ؿ�ʼ��������Ԫ��
print (t1 + tinytuple) # ����Ԫ��
tup1 = ()    # ��Ԫ��
tup2 = (20,) # һ��Ԫ�أ���Ҫ��Ԫ�غ���Ӷ���
num = (50) #��intԪ��
print(type(num))
list1= ['Google', 'Taobao', 'Runoob', 'Baidu']
tuple1=tuple(list1)#tupleת������
print(tuple1)
#�������ض��ֵ��ʱ������Ԫ��ķ�ʽ���ص�

def test(*args):  #�䳤����
    print(args)
    return args

print(type(test(1,2,3,4)))    #���Կ����亯���ķ���ֵ��һ��Ԫ��
for x in (1, 2, 3):
    print (x)

t = 12345, 54321, 'hello!' #���ŷָ�Ҳ��tupel
print(type(t))

#--- set ȥ�ص� ,����һ���ռ��ϱ����� set() ������ { }����Ϊ { } ����������һ�����ֵ䡣
basket={'orange', 'banana', 'pear', 'apple'}
print('orange' in basket)
# set���Խ��м�������
a = set('abracadabra')#ת������
b = set('alacazam')
set1= frozenset(range(10))     # ����һ���µĲ��ɱ伯��
set2=  frozenset(['b', 'r', 'u', 'o', 'n'])   # �������ɱ伯��

print(a - b)     # a��b�Ĳ
print(a | b)     # a��b�Ĳ���
print(a & b)     # a��b�Ľ���
print(a ^ b)     # a��b�в�ͬʱ���ڵ�Ԫ��
a = {x for x in 'abracadabra' if x not in 'abc'}
print(a) #{'r', 'd'}

#--dict
tinydict = {'name': 'runoob','code':1, 'site': 'www.runoob.com'}
#print (tinydict["xxx"]) #���ʲ����ڻᱨ��
print (tinydict.values()) # �������ֵ
mydict=dict([('Runoob', 1), ('Google', 2), ('Taobao', 3)])  #ת������
print (mydict)

dict1= {x: x**2 for x in (2, 4, 6)} 
print(dict1)

dict2=dict(Runoob=1, Google=2, Taobao=3)
print(dict2)

dict_2 = dict({('a',1),('b',2),('c',3)})
print(dict_2)
dict_3 = dict([['a', 1], ['b', 2], ['c', 3]])
print(dict_3)
dict_4 = dict((('a',1),('b',2),('c',3)))
print(dict_4)



for c in dict1:
    print(c,end=':');
    print(dict1[c])
dict1 = {'abc':1,"cde":2,"d":4,"c":567,"d":"key1"}
for k,v in dict1.items():
    print(k,":",v)
#�����벻�ɱ䣬���Կ��������֣��ַ�����Ԫ��䵱�������б�Ͳ���
#dict = {['Name']: 'Runoob', 'Age': 7}

#�ֵ� copy() ��������һ���ֵ��ǳ����
seq = ('name', 'age', 'sex')
dict = dict.fromkeys(seq, 10)
print ("�µ��ֵ�Ϊ : %s" %  str(dict))

del dict['name']
{x: x**2 for x in (2, 4, 6)}

questions = ['name', 'quest', 'favorite color']
answers = ['lancelot', 'the holy grail', 'blue']
for q, a in zip(questions, answers): #zip����
    print('What is your {0}?  It is {1}.'.format(q, a))

for i in reversed(range(1, 10, 2)):
    print(i)

#sorted() ��������һ������������У������޸�ԭֵ
basket = ['apple', 'orange', 'apple', 'pear', 'orange', 'banana']
for f in sorted(basket):
     print(f)
print(basket)


#--convert
print(repr('abc\n123'))#ͬr
print(int('15',base=16))#������16������,ת����10����
print(type(float('2.032')))
dict = {'runoob': 'runoob.com', 'google': 'google.com'};
print(type(str(dict)))
print(eval('pow(2,2)'))
print(complex(20,40j))
print(chr(65))
print(ord('A'))#ת��Ϊscii����
print(hex(20))
print(oct(10))

num=0b00100101  #0b�Ƕ�
print(bin(100))

a = 10
list = [1, 2, 3, 4, 5];
if (b not in list):
    print("2 - ���� b ���ڸ������б��� list ��")
#�ж�������ʶ���ǲ���������һ������	x is y, ���� id(x) == id(y)


a = 20
b = 20

if (a is b):
    print("1 - a �� b ����ͬ�ı�ʶ")
if (id(a) == id(b)):
    print("2 - a �� b ����ͬ�ı�ʶ")
b = 30
if (a is not b):
    print("4 - a �� b û����ͬ�ı�ʶ")

#С�����صĸ����ѣ�-5��256�������Ԥ�ȴ����ã�����a��b���������Χ��ʱ�����������ͻ�ָ��ͬ�Ķ����ˣ���˵�ַҲ�᲻һ����
a1=1000
b1=1000
print(id(a1))
print(id(b1))
print(id(a1)==id(b1))
print( a1 is b1 )
#���������·���False,��pycharm�к�pyDev�о�ΪTrue,������IDE��ʱ��ʹ�����Լ��Ľű�?????

#x and y   ��� x Ϊ False��  ���� False������������ y �ļ���ֵ
#x or y	   ��� x �� True��   ���� x ��ֵ������������ y �ļ���ֵ
print(5<3 and 10) #False
print(5>3 and 10)#10
print(3 and 10)#10
print(3 or 4)#3
print(3>5 or 4)#4

#---math
import math
print(math.fabs(10))#float 10.0

print(math.log(math.e))#����1.0
print(math.log(100,10))#����2.0
print(math.log10(100))#����2.0
print ("math.modf(100.12) : ", math.modf(-100.12))# С���������������� (-0.12000000000000455, -100.0)


import random
print (random.choice(range(10))) #�����е�Ԫ���������ѡһ��Ԫ��
print (random.sample(range(10), 2))#�����ѡ2��Ԫ��

# �� 1-100 ��ѡȡһ������
print ("randrange(1,100, 2) : ", random.randrange(1, 100, 2)) #([start,] stop [,step])
# �� 0-99 ѡȡһ�������
print ("randrange(100) : ", random.randrange(100))

list = [20, 16, 10, 5];
random.shuffle(list)
print ("��������б� : ",  list)

print ("uniform 5-10�� �����ʵ�� : ",  random.uniform(5, 10))

print (random.randint(1000,9999))

print ("degrees(math.pi) : ",  math.degrees(math.pi))#����ת��Ϊ�Ƕ�
print ("radians(45) : ",  math.radians(45))#�Ƕ�ת��Ϊ����


print(round(10.5)) #10 round ��BUG????  ��Ϊż���� <6����
print(round(10.6)) #10 round ��BUG????  ��Ϊż���� <6����
#��Ϊ�������ı�ʾ�ڼ�����в���׼ȷ
print(round(11.5)) #12          ���Ϊ����    <5����
print(round(2.355, 2))#2.35
str1 = "����̳�";
str_utf8 = str1.encode("UTF-8")
str_gbk = str1.encode("GBK")

print(str1)
print("UTF-8 ���룺", str_utf8.decode('UTF-8','strict'))
print("GBK ���룺", str_gbk.decode('GBK','strict'))

a,b=0,1
while b < 100:
    print(b, end=',') #end ��������ͬһ�У������������ĩβ��Ӳ�ͬ���ַ�
    a, b = b, a+b

count = 0
while count < 5:
    print(count, " С�� 5")
    count = count + 1
else:  #while �����else
    print(count, " ���ڻ���� 5")

sites = ["Baidu", "Google","Runoob","Taobao"]
for site in sites:   #for һ��Ҫ�� in
    if site == "Runoob":
        print("Runoob!")
        break
    if site == "Google":
        continue
    print( site)
else: #for Ҳ����else
    print("no for data!")

for letter in 'Runoob':
   if letter == 'o':
      pass#pass �����κ�����
      print ('ִ�� pass ��')
   print ('��ǰ��ĸ :', letter)

list=[1,2,3,4]
it = iter(list)    # ��������������
for x in it:
    print (x, end=" ")

print ("----")
#ÿ������ yield ʱ��������ͣ�����浱ǰ���е�������Ϣ������yield��ֵ��������һ��ִ�� next()����ʱ�ӵ�ǰλ�ü�������
def fibonacci(n):
    a, b, counter = 0, 1, 0
    while True:
        if (counter > n):
            return
        yield a
        a, b = b, a + b
        counter += 1
f = fibonacci(10)  # f ��һ��������������������������
while True:
    try:
        print(next(f), end=" ")
    except StopIteration:
        #sys.exit()
        break
        print("iteration done");

l = [i for i in range(0,5)] #�����÷�
print(type(l))  #list

#strings, tuples, �� numbers �ǲ��ɸ��ĵĶ���(ֵ����)���� list,dict �����ǿ����޸ĵĶ���
def printinfo( name,  age = 35 ): #Ĭ��ֵ�����������Ĳ���
   "��ӡ�κδ�����ַ���"
   print ("����: ", name);
   print ("����: ", age);
   return;
printinfo( age=50, name="runoob" );#��ָ������������

sum = lambda arg1, arg2 = 3 : arg1 + arg2; # ��������,Ҳ����Ĭ��ֵarg2 = 3,Ҳ�����������
print("��Ӻ��ֵΪ : ", sum(10, 20)) 
print("��Ӻ��ֵΪ : ", sum(10)) 
#ģ�飨module�����ࣨclass���Լ�������def��lambda���Ż������µ�������
#if/elif/else/��try/except��for/while�ȣ��ǲ��������µ�������ģ�Ҳ����˵����Щ����ڶ���ı������ⲿҲ���Է���



total = 0; # ����һ��ȫ�ֱ���
def sum( arg1, arg2 ):
    #global total
    total = arg1 + arg2;
    print ("�������Ǿֲ����� : ", total)
    return total

sum( 10, 20 )
print ("��������ȫ�ֱ��� : ", total)  # 0,��� global total �� 30



def outer():
    num = 10
    def inner():
        nonlocal num   # nonlocal�ؼ�������
        num = 100
        print(num)
    inner()
    print(num)#100
outer()

def func(country,province,**kwargs):  # **kwargs  ��N���ؼ��ֲ���ת��Ϊ�ֵ�,���봫x=y��ʽ
   print(country,province,kwargs)

func("China","Sichuan",city = "Chengdu", section = "JingJiang")


def changeme(mylist):
    mylist = [1, 2, 3, 4]; #�ⲻ�µ�=����Ӱ��ԭ����,ͬJava
    print("������ȡֵ: ", mylist)
    return

mylist = [10, 20, 30];
changeme(mylist);
print("������ȡֵ: ", mylist)#[10, 20, 30]




a = 10
def test():
    #a = a + 1  #����a ȫ�ֵ��޸� ���� ��ʹ������a
    a=20
    c = a + 1
    print(c)
test()



x = 0
def outer():
    x = 1  
    def inner():
        i = 2
        print(x) #1
    inner()
outer()

#---deque
from collections import deque

queue = deque(["Eric", "John", "Michael"])
queue.append("Terry")
print(queue.popleft())
#--[for ]
vec = [2, 4, 6]
print([3*x for x in vec]) #[6, 12, 18]
print([[x, x**2] for x in vec])

freshfruit = ['  banana', '  loganberry ', 'passion fruit  ']
print([weapon.strip() for weapon in freshfruit])

print( [3*x for x in vec if x > 3])

vec1 = [2, 4, 6]
vec2 = [4, 3, -9]
vecMulti=[x*y for x in vec1 for y in vec2]  #�൱��ǰ���for�����
print(vecMulti)

matrix = [
 [1, 2, 3, 4],\
 [5, 6, 7, 8],\
 [9, 10, 11, 12],\
]
#����ʵ����3X4�ľ����б�ת��Ϊ4X3�б�
transMatrix=[[row[i] for row in matrix] for i in range(4)]#ǰ�����[] ,����ı�������
print(transMatrix)  #[[1, 5, 9], [2, 6, 10], [3, 7, 11], [4, 8, 12]]
#��Ч��ͬ��
transposed = []
for i in range(4):
    print([row[i] for row in matrix])
    transposed.append([row[i] for row in matrix])
print(transposed)
#��Ч��ͬ��
transposed = []
for i in range(4):
    transposed_row = []
    for row in matrix:
        transposed_row.append(row[i])
    transposed.append(transposed_row)
print(transposed) 
          
#---modal
import sys  #pyCharmʹ�õ����Լ�·���е�
#sys.path �Զ���������ģ���·�����б�,��װĿ¼��Lib,DLLs,����������ʾ��һ���մ�,����ǰĿ¼
print('sys.path ·��Ϊ��', sys.path, '\n')

import my_modal
print(my_modal.fib2(10)) #import my_modal  ���ļ���

#from my_modal import fib2  #���԰�����ִ�еĴ���,ֻ��һ�α�����ʱ�Żᱻִ��
from my_modal import *
print(fib2(10)) #from my_modal import * ʹ�þͲ��ü��ļ�����
print(m_total)#����ʹ��ģ���еı���
#print(_kind)# ģ������_��ͷ�ı�����������
print(dir(fib2)) #���õĺ��� dir() �����ҵ�ģ���ڶ�����������ƣ�ȫ����  __xx__ ��ʽ
print(dir())  #���޲����г���ǰ������������ƣ���ʹ�õı�����,������

import sys
#print(sys.ps1)#����ʾ��  >>> ,ֻ����������ʹ��
#print(sys.ps2)# ����ʾ  ...  ,ֻ����������ʹ��

#Ŀ¼ֻ�а���һ������ __init__.py ���ļ��Żᱻ������һ���� ,���԰���һЩ��ʼ������
#from package import item        ��item�ȿ����ǰ��������ģ�飨�Ӱ��������ߺ���������߱���
#import item.subitem.subsubitem  �������һ��������ǰ� �����һ���������ģ������ǰ������ǲ��������࣬�������߱���������

#----
#from sound.effects import *  �����effectsĿ¼�У��ҵ�������������е���ģ�飬һ��һ���İ����Ƕ����������Windows��һ����Сд�����ֵ�ϵͳ
#����������ļ� __init__.py ����һ������ __all__ ���б��������ʹ�� from package import * ��ʱ��Ͱ�����б���(��ָ���Ķ�����ȫ��)����������(��Ϊģ������ֻ�ᵼ��ģ��)��Ϊ�����ݵ���

#��� __all__ ���û�ж��壬��ôʹ��from sound.effects import *�����﷨��ʱ��
#�Ͳ��ᵼ��� sound.effects ����κ���ģ�顣
#��ֻ�ǰѰ�sound.effects�������涨����������ݵ������

#__path__ ��ʽһ
#from sound.effects.echo  import echoFunc
#echoFunc()

#��ʽ��
#from sound.effects.echo  import *
#echoFunc()

#��ʽ��
#from sound.effects  import *
#echo.echoFunc()  #Ҫ��effects���е�__init__.py��д  __all__ =["echo"]
#print(m_effect_echo)  #���� __all__ =["echo"], ����

#��ʽ��
import sound.effects.echo 
sound.effects.echo.echoFunc() #����ģ�飬��ͷû�� from ����ʹ��ȫ��ȥ����
 
#---------------
s = 'Hello, Runoob'
print(repr(s)) #��'' �������׶�

x=2
print(repr(x).rjust(5), repr(x * x).rjust(5), end=' ') #rjust�Ҷ��룬��ljust,center
print("\n12".zfill(5))#��0
print()
for x in range(1, 3):
    print('{0:2d} {1:3d} {2:4d}'.format(x, x*x, x*x*x))  #string.format {}
print('{1} �� {0}'.format('Google', 'Runoob'))
print('{name}��ַ�� {site}'.format(name='����̳�', site='www.runoob.com'))
print('վ���б� {0}, {1}, �� {other}��'.format('Google', 'Runoob', other='Taobao'))

print('���� PI ��ֵ����Ϊ�� {!r}��'.format(math.pi)) #'!a' (ʹ�� ascii()), '!s' (ʹ�� str()) �� '!r' (ʹ�� repr())
print('���� PI ��ֵ����Ϊ {0:.3f}��'.format(math.pi)) #������С�������λ
print('{0:10}==>{1:10d}'.format('Google',1)) #��������ô��Ŀ��

table = {'Google': 1, 'Runoob': 2, 'Taobao': 3}
print('Runoob: {0[Runoob]:d}; Google: {0[Google]:d}; Taobao: {0[Taobao]:d}'.format(table))  #[]�ڵ���dict��key  ,[]ǰҪΪ0
print('Runoob: {Runoob:d}; Google: {Google:d}; Taobao: {Taobao:d}'.format(**table))


f=open("input.txt","r")#��C����һ��b������r,rb,r+,rb+,w,wb,w+,wb+,a,ab,a+,ab+
for line in f:  #����for in file������ÿ�� ,f.next()������һ��
    print(line,end='')

# f.seek(4,0) #�ƶ�4�� ���ڶ�������0��ʾ���ļ�ͷ��ʼ��1��ʾ�ӵ�ǰλ�ã�2��ʾ���ļ�β��ʼ��ǰ�ƶ�
f.seek(4) #r��ʽ�򿪿���seek
print(f.read(1))
f.close() 

f=open("out.txt","a")
f.write("����1")  #�ַ�����GBK(�ļ��ַ���)
f.write("64789")
pos=f.tell() #�ļ���ͷ��ʼ������ֽ�����
print("POS=%s" % pos)
f.close()


from urllib import request

response = request.urlopen("http://www.baidu.com/")
fi = open("baidu.txt", 'w')
page = fi.write(str(response.read()))
fi.close()

#--- ���кͷ����л�
import pprint, pickle
data1 = {'a': [1, 2.0, 3, 4+6j],
         'b': ('string', u'Unicode string'),
         'c': None}
output = open('data.pkl', 'wb')
pickle.dump(data1, output) #pickle.dump �ڶ��������Ѿ�open��file
output.close()

pkl_file = open('data.pkl', 'rb')
data1 = pickle.load(pkl_file)#pickle.load
pprint.pprint(data1)
pkl_file.close()

import os
import os.path
full_path=os.path.join("D:/","test.txt")
print (full_path)

print("hello world hello".count("hello"))
print("hello world hello".replace("hello","HELLO"))
print(os.pardir) #��ʾ .. ��һ��Ŀ¼
print(os.getcwd() + os.sep + "filename"  + os.linesep)

#���кܶ෽�� http://www.runoob.com/python3/python3-os-file-methods.html

#--exception
try:
    f = open('input.txt')
    s = f.readline()
   # 5/0
   # i = int(s.strip())
    #raise NameError('HiThere') #Exception ������
except FileNotFoundError as err: #����
     print("file lookup: {0}".format(err))
except (ValueError, ZeroDivisionError ):  #���Զ��
     print("value Zero ERROR:")
except:   #except�Ӿ���Ժ����쳣������
    print("except error:", sys.exc_info()[0]) #����Ϣ
    raise  #������
else :  #����������е�except�Ӿ�֮�� ,  û�з����κ��쳣��ʱ��ִ��
    print("�ɹ�ִ��" )
finally:
    print("executing finally clause")

#�ؼ��� with ���Ϳ��Ա�֤�����ļ�֮��Ķ�����ʹ����֮��һ������ȷ��ִ������������:
with open("input.txt") as f:
    for line in f:
        print(line, end="")
#���Բ�����f.close()

#---class
class MyClass: #()���п���
    i = 12345
    def f(self): #��һ�㺯�����岻ͬ���෽������������� self����Ϊ��һ������,Ҳ����ʹ�� this
        return 'hello world'

    def __init__(self): #��Ϊ __init__() �����ⷽ�������췽�������ɶ�Ӳ���
        self.data = []
        print(self.__class__) #����
x = MyClass()
print("MyClass ������� i Ϊ��", x.i)
print("MyClass ��ķ��� f ���Ϊ��", x.f())


class MyChildClass(MyClass):
    __weight = 0 # �����»��߿�ͷ������������Ϊ˽��
    name=""

    def __foo(self):  # ˽�з���
        print('����˽�з���')
    def __init__(self, w,name):
        self.__weight = w
        self.name=name
        MyClass.__init__(self)

    def f(self):
        print("child hello world")


s = MyChildClass(200,"wang")
#print(s.__weight) #���ܷ���
#print(s.__foo()) #���ܷ���
print(s.name)
print(s.f())

'''���ר�з����� __init__ 
__del__ : �����������ͷŶ���ʱʹ��
__repr__ : ��ӡ��ת��
__setitem__ : ����������ֵ
__getitem__: ����������ȡֵ
__len__: ��ó���
__cmp__: �Ƚ�����
__call__: ��������
__add__: ������   
__sub__: ������
__mul__: ������
__div__: ������
__mod__: ��������
__pow__: �˷�
'''


class Vector:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __str__(self): #toString()
        return 'Vector (%d, %d)' % (self.a, self.b)

    def __add__(self, other):  # ���������
        return Vector(self.a + other.a, self.b + other.b)
v1 = Vector(2, 10)
v2 = Vector(5, -2)
print(v1 + v2)
#help(os)
help(os.chdir)

import glob
print(glob.glob('*.py'))  #�����ļ�

#---mail
'''
import smtplib
server = smtplib.SMTP('localhost')
server.sendmail('from@example.org', 'to@example.org',"msg")
server.quit()
'''


from datetime import date
now = date.today()
print(now)
print( now.strftime("%Y-%m-%d  week:%A") )
birthday = date(1964, 7, 31)
age = now - birthday
print( age.days)

import zlib
s = b'witch which has which witches wrist watch'
print(len(s) )
t = zlib.compress(s)
print(len(t))
print( zlib.decompress(t) )
print( zlib.crc32(s))


#-----regexp
import re
line = "Cats are smarter than dogs"
matchObj = re.match( r'(.*) are (.*?) .*', line, re.M|re.I)
#re.matchֻƥ���ַ����Ŀ�ʼ������ַ�����ʼ������������ʽ����ƥ��ʧ��
if matchObj:
   print ("matchObj.group() : ", matchObj.group())
   print ("matchObj.group(1) : ", matchObj.group(1))
   print ("matchObj.group(2) : ", matchObj.group(2))
else:
   print ("No match!!")
#��re.searchƥ�������ַ�����ֱ���ҵ�һ��ƥ�䡣
matchObj = re.match( r'dogs', line, re.M|re.I)
if matchObj:
   print ("match --> matchObj.group() : ", matchObj.group())
else:
   print ("No match!!")

#re.M	����ƥ�䣬Ӱ�� ^ �� $
matchObj = re.search( r'dogs', line, re.M|re.I)
if matchObj:
   print ("search --> matchObj.group() : ", matchObj.group())
else:
   print ("No match!!")


# ��ƥ������ֳ��� 2
def doubleFunc(matched):
   value = int(matched.group('value')) #value��key,  ��Ӧ  #(?P<value>\d+)
   #value = int(matched.group())# ��Ӧ '(\d+)'
   return str(value * 2)

s = 'A23G4HFD567'
#print(re.sub('(\d+)', doubleFunc, s))  #matched.group()
print(re.sub('(?P<value>\d+)', doubleFunc, s)) #matched.group('value')
#-----------
import time
ticks = time.time() #1970��Ԫ�󾭹��ĸ�������
print ("��ǰʱ���Ϊ:", ticks)

localtime = time.localtime(time.time())
print ("����ʱ��Ϊ :", localtime)

print (time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))

# ����ʽ�ַ���ת��Ϊʱ���
a = "Sat Mar 28 22:24:24 2016"
print (time.mktime(time.strptime(a,"%a %b %d %H:%M:%S %Y")))


import calendar

cal = calendar.month(2016, 1) #����һ��Ĭ�ϵ�ÿ�ܵ�һ��
print ("�������2016��1�·ݵ�����:")
print (cal)

print("time.ctime() : %s" % time.ctime())
#	time.ctime([secs]) �����൱��asctime(localtime(secs))��δ�������൱��asctime()







