# -*- coding: GBK -*-
#文件编码默认UTF-8 上面指定为GBK
print ("GBK 中文")
"""
多行注释 ,三个单引号,
或者 三个双引号 
"""
#反斜杠(\)来实现多行语句    同linux 在 [], {}, 或 () 中的多行语句，不需要使用反斜杠(\)
#四种类型：整数、长整数、浮点数和复数。


print(r"this is a line with \n abc") # #字符串前加r或R。则\n会显示，并不是换行。

#unicode字符串，加前缀u或U， 如 u"this is an unicode string"。
print(u"unicode \u4e2d \u56fd  56fd ") 
print("\u4e2d \u56fd ")
paragraph = """这是一个段落，
                可以由多行组成"""
 
str2="this " "is " "string" # "this " "is " "string"会被自动转换为this is string。
print(str2)

#inputStr = input("请输入：");
#print ("你输入的内容是: ", inputStr)


import sys; x = 'runoob'; sys.stdout.write(x + '\n') #同一行中使用多条语句，语句之间使用分号(;)分割

#print 默认输出是换行的，如果要实现不换行需要在变量末尾加上 end=":"
print("one",  end=":")
print( "two", end=":") 
#输出one:two:

#从某个模块中导入多个函数,格式为： from somemodule import firstfunc, secondfunc, thirdfunc
#将某个模块中的全部函数导入，格式为： from somemodule import *
from sys import argv,path 
#from math import *

#python -c "print('a')



#!/usr/bin/env python3
#上面写法可代替下面 未测试???
#!/usr/bin/python3

#>>> help(max)  ……显示帮助信息……

#仅仅想得到文档字符串：  
print(max.__doc__)


#Python3 中有六个标准的数据类型：Number（数字） String（字符串） List（列表） Tuple（元组） Sets（集合） Dictionary（字典）
 #int、float、bool、complex（复数）。


a, b, c, d = 20, 5.5, True, 4+3j
print(type(a), type(b), type(c), type(d))
a = 111
print(isinstance(a, int))
# True 和 False 定义成关键字了，但它们的值还是 1 和 0，它们可以和数字相加
print(True+10)

#del语句删除一些对象引用。
del a,b,c,d
#print(a)#报错is not defined
  
print(2 / 4)  # 除法，得到一个浮点数 0.5
print(2 // 4) # 除法，得到一个整数   0
print(2 ** 5)# 乘方  32

str1 = 'Runoob'
print (str1[2:5])     # 输出从第三个开始到第五个的字符
print (str1[0:-1])
print (str1[2:]) 
print (str1[0]) 
#---list
listObj = [ 'abcd', 786 , 2.23, 'runoob', 70.2 ]
tinylist = [123, 'runoob']
  
print (listObj[2:])        # 输出从第三个元素开始的所有元素
print (tinylist * 2)    # 一个列表元素数2倍
print (listObj + tinylist) # 连接列表

del listObj[2]  #也可以del listObj
print (listObj)
a=[['a', 'b', 'c'], [1, 2, 3]]
print(a[0][0])

aTuple = (123, 'Google', 'Runoob', 'Taobao')
list1 = list(aTuple)  #转换
print ("列表元素 : ", list1)
#list copy() 浅复制等于a[:]。
#append和pop结合当栈使用

for i, v in enumerate(['tic', 'tac', 'toe']):   #索引位置和对应值可以使用 enumerate() 函数
   print(i, v)


#---tuple
t1 = ( 'abcd', 786 , 2.23, 'runoob', 70.2  )

#t1[0] = 100 #修改元组元素操作是非法的。
tinytuple = (123, 'runoob')
   
print (t1[1:3])        # 输出从第二个元素开始到第三个元素
print (t1 + tinytuple) # 连接元组
tup1 = ()    # 空元组
tup2 = (20,) # 一个元素，需要在元素后添加逗号
num = (50) #是int元素
print(type(num))
list1= ['Google', 'Taobao', 'Runoob', 'Baidu']
tuple1=tuple(list1)#tuple转换函数
print(tuple1)
#函数返回多个值的时候，是以元组的方式返回的

def test(*args):  #变长参数
    print(args)
    return args

print(type(test(1,2,3,4)))    #可以看见其函数的返回值是一个元组
for x in (1, 2, 3):
    print (x)

t = 12345, 54321, 'hello!' #逗号分隔也是tupel
print(type(t))

#--- set 去重的 ,创建一个空集合必须用 set() 而不是 { }，因为 { } 是用来创建一个空字典。
basket={'orange', 'banana', 'pear', 'apple'}
print('orange' in basket)
# set可以进行集合运算
a = set('abracadabra')#转换函数
b = set('alacazam')
set1= frozenset(range(10))     # 生成一个新的不可变集合
set2=  frozenset(['b', 'r', 'u', 'o', 'n'])   # 创建不可变集合

print(a - b)     # a和b的差集
print(a | b)     # a和b的并集
print(a & b)     # a和b的交集
print(a ^ b)     # a和b中不同时存在的元素
a = {x for x in 'abracadabra' if x not in 'abc'}
print(a) #{'r', 'd'}

#--dict
tinydict = {'name': 'runoob','code':1, 'site': 'www.runoob.com'}
#print (tinydict["xxx"]) #访问不存在会报错
print (tinydict.values()) # 输出所有值
mydict=dict([('Runoob', 1), ('Google', 2), ('Taobao', 3)])  #转换函数
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
#键必须不可变，所以可以用数字，字符串或元组充当，而用列表就不行
#dict = {['Name']: 'Runoob', 'Age': 7}

#字典 copy() 函数返回一个字典的浅复制
seq = ('name', 'age', 'sex')
dict = dict.fromkeys(seq, 10)
print ("新的字典为 : %s" %  str(dict))

del dict['name']
{x: x**2 for x in (2, 4, 6)}

questions = ['name', 'quest', 'favorite color']
answers = ['lancelot', 'the holy grail', 'blue']
for q, a in zip(questions, answers): #zip函数
    print('What is your {0}?  It is {1}.'.format(q, a))

for i in reversed(range(1, 10, 2)):
    print(i)

#sorted() 函数返回一个已排序的序列，并不修改原值
basket = ['apple', 'orange', 'apple', 'pear', 'orange', 'banana']
for f in sorted(basket):
     print(f)
print(basket)


#--convert
print(repr('abc\n123'))#同r
print(int('15',base=16))#参数是16进制制,转换成10进制
print(type(float('2.032')))
dict = {'runoob': 'runoob.com', 'google': 'google.com'};
print(type(str(dict)))
print(eval('pow(2,2)'))
print(complex(20,40j))
print(chr(65))
print(ord('A'))#转换为scii数字
print(hex(20))
print(oct(10))

num=0b00100101  #0b是二
print(bin(100))

a = 10
list = [1, 2, 3, 4, 5];
if (b not in list):
    print("2 - 变量 b 不在给定的列表中 list 中")
#判断两个标识符是不是引用自一个对象	x is y, 类似 id(x) == id(y)


a = 20
b = 20

if (a is b):
    print("1 - a 和 b 有相同的标识")
if (id(a) == id(b)):
    print("2 - a 和 b 有相同的标识")
b = 30
if (a is not b):
    print("4 - a 和 b 没有相同的标识")

#小整数池的概念，会把（-5，256）间的数预先创建好，而当a和b超过这个范围的时候，两个变量就会指向不同的对象了，因此地址也会不一样，
a1=1000
b1=1000
print(id(a1))
print(id(b1))
print(id(a1)==id(b1))
print( a1 is b1 )
#在命令行下返回False,在pycharm中和pyDev中就为True,可能是IDE运时都使用了自己的脚本?????

#x and y   如果 x 为 False，  返回 False，否则它返回 y 的计算值
#x or y	   如果 x 是 True，   返回 x 的值，否则它返回 y 的计算值
print(5<3 and 10) #False
print(5>3 and 10)#10
print(3 and 10)#10
print(3 or 4)#3
print(3>5 or 4)#4

#---math
import math
print(math.fabs(10))#float 10.0

print(math.log(math.e))#返回1.0
print(math.log(100,10))#返回2.0
print(math.log10(100))#返回2.0
print ("math.modf(100.12) : ", math.modf(-100.12))# 小数部分与整数部分 (-0.12000000000000455, -100.0)


import random
print (random.choice(range(10))) #从序列的元素中随机挑选一个元素
print (random.sample(range(10), 2))#随机挑选2个元素

# 从 1-100 中选取一个奇数
print ("randrange(1,100, 2) : ", random.randrange(1, 100, 2)) #([start,] stop [,step])
# 从 0-99 选取一个随机数
print ("randrange(100) : ", random.randrange(100))

list = [20, 16, 10, 5];
random.shuffle(list)
print ("随机排序列表 : ",  list)

print ("uniform 5-10间 的随机实数 : ",  random.uniform(5, 10))

print (random.randint(1000,9999))

print ("degrees(math.pi) : ",  math.degrees(math.pi))#弧度转换为角度
print ("radians(45) : ",  math.radians(45))#角度转换为弧度


print(round(10.5)) #10 round 的BUG????  边为偶数： <6，舍
print(round(10.6)) #10 round 的BUG????  边为偶数： <6，舍
#因为浮点数的表示在计算机中并不准确
print(round(11.5)) #12          左边为奇数    <5，舍
print(round(2.355, 2))#2.35
str1 = "菜鸟教程";
str_utf8 = str1.encode("UTF-8")
str_gbk = str1.encode("GBK")

print(str1)
print("UTF-8 解码：", str_utf8.decode('UTF-8','strict'))
print("GBK 解码：", str_gbk.decode('GBK','strict'))

a,b=0,1
while b < 100:
    print(b, end=',') #end 结果输出到同一行，或者在输出的末尾添加不同的字符
    a, b = b, a+b

count = 0
while count < 5:
    print(count, " 小于 5")
    count = count + 1
else:  #while 后可用else
    print(count, " 大于或等于 5")

sites = ["Baidu", "Google","Runoob","Taobao"]
for site in sites:   #for 一定要用 in
    if site == "Runoob":
        print("Runoob!")
        break
    if site == "Google":
        continue
    print( site)
else: #for 也可用else
    print("no for data!")

for letter in 'Runoob':
   if letter == 'o':
      pass#pass 不做任何事情
      print ('执行 pass 块')
   print ('当前字母 :', letter)

list=[1,2,3,4]
it = iter(list)    # 创建迭代器对象
for x in it:
    print (x, end=" ")

print ("----")
#每次遇到 yield 时函数会暂停并保存当前所有的运行信息，返回yield的值。并在下一次执行 next()方法时从当前位置继续运行
def fibonacci(n):
    a, b, counter = 0, 1, 0
    while True:
        if (counter > n):
            return
        yield a
        a, b = b, a + b
        counter += 1
f = fibonacci(10)  # f 是一个迭代器，由生成器返回生成
while True:
    try:
        print(next(f), end=" ")
    except StopIteration:
        #sys.exit()
        break
        print("iteration done");

l = [i for i in range(0,5)] #特殊用法
print(type(l))  #list

#strings, tuples, 和 numbers 是不可更改的对象(值传递)，而 list,dict 等则是可以修改的对象
def printinfo( name,  age = 35 ): #默认值至少是最后面的参数
   "打印任何传入的字符串"
   print ("名字: ", name);
   print ("年龄: ", age);
   return;
printinfo( age=50, name="runoob" );#可指定参数名调用

sum = lambda arg1, arg2 = 3 : arg1 + arg2; # 匿名函数,也可有默认值arg2 = 3,也是至少在最后
print("相加后的值为 : ", sum(10, 20)) 
print("相加后的值为 : ", sum(10)) 
#模块（module），类（class）以及函数（def、lambda）才会引入新的作用域
#if/elif/else/、try/except、for/while等）是不会引入新的作用域的，也就是说这这些语句内定义的变量，外部也可以访问



total = 0; # 这是一个全局变量
def sum( arg1, arg2 ):
    #global total
    total = arg1 + arg2;
    print ("函数内是局部变量 : ", total)
    return total

sum( 10, 20 )
print ("函数外是全局变量 : ", total)  # 0,如加 global total 是 30



def outer():
    num = 10
    def inner():
        nonlocal num   # nonlocal关键字声明
        num = 100
        print(num)
    inner()
    print(num)#100
outer()

def func(country,province,**kwargs):  # **kwargs  把N个关键字参数转化为字典,必须传x=y格式
   print(country,province,kwargs)

func("China","Sichuan",city = "Chengdu", section = "JingJiang")


def changeme(mylist):
    mylist = [1, 2, 3, 4]; #这不新的=不会影响原来的,同Java
    print("函数内取值: ", mylist)
    return

mylist = [10, 20, 30];
changeme(mylist);
print("函数外取值: ", mylist)#[10, 20, 30]




a = 10
def test():
    #a = a + 1  #报错，a 全局的修改 不能 再使本变量a
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
vecMulti=[x*y for x in vec1 for y in vec2]  #相当于前面的for在外层
print(vecMulti)

matrix = [
 [1, 2, 3, 4],\
 [5, 6, 7, 8],\
 [9, 10, 11, 12],\
]
#以下实例将3X4的矩阵列表转换为4X3列表：
transMatrix=[[row[i] for row in matrix] for i in range(4)]#前面加了[] ,后面的变成了外层
print(transMatrix)  #[[1, 5, 9], [2, 6, 10], [3, 7, 11], [4, 8, 12]]
#上效果同下
transposed = []
for i in range(4):
    print([row[i] for row in matrix])
    transposed.append([row[i] for row in matrix])
print(transposed)
#上效果同下
transposed = []
for i in range(4):
    transposed_row = []
    for row in matrix:
        transposed_row.append(row[i])
    transposed.append(transposed_row)
print(transposed) 
          
#---modal
import sys  #pyCharm使用的是自己路径中的
#sys.path 自动查找所需模块的路径的列表,安装目录的Lib,DLLs,命令行下显示有一个空串,代表当前目录
print('sys.path 路径为：', sys.path, '\n')

import my_modal
print(my_modal.fib2(10)) #import my_modal  加文件名

#from my_modal import fib2  #可以包括可执行的代码,只第一次被导入时才会被执行
from my_modal import *
print(fib2(10)) #from my_modal import * 使用就不用加文件名了
print(m_total)#可以使用模块中的变量
#print(_kind)# 模块中以_开头的变量不可引用
print(dir(fib2)) #内置的函数 dir() 可以找到模块内定义的所有名称，全部是  __xx__ 格式
print(dir())  #如无参数列出当前定义的所有名称，已使用的变量名,函数名

import sys
#print(sys.ps1)#主提示符  >>> ,只在命令行下使用
#print(sys.ps2)# 副提示  ...  ,只在命令行下使用

#目录只有包含一个叫做 __init__.py 的文件才会被认作是一个包 ,可以包含一些初始化代码
#from package import item        在item既可以是包里面的子模块（子包），或者函数，类或者变量
#import item.subitem.subsubitem  除了最后一项，都必须是包 ，最后一项则可以是模块或者是包，但是不可以是类，函数或者变量的名字

#----
#from sound.effects import *  会进入effects目录中，找到这个包里面所有的子模块，一个一个的把它们都导入进来。Windows是一个大小写不区分的系统
#如果包定义文件 __init__.py 存在一个叫做 __all__ 的列表变量，在使用 from package import * 的时候就把这个列表中(是指定的而不是全部)的所有名字(如为模块名，只会导入模块)作为包内容导入

#如果 __all__ 真的没有定义，那么使用from sound.effects import *这种语法的时候，
#就不会导入包 sound.effects 里的任何子模块。
#他只是把包sound.effects和它里面定义的所有内容导入进来

#__path__ 方式一
#from sound.effects.echo  import echoFunc
#echoFunc()

#方式二
#from sound.effects.echo  import *
#echoFunc()

#方式三
#from sound.effects  import *
#echo.echoFunc()  #要求effects包中的__init__.py中写  __all__ =["echo"]
#print(m_effect_echo)  #如有 __all__ =["echo"], 报错

#方式四
import sound.effects.echo 
sound.effects.echo.echoFunc() #导入模块，开头没有 from 必须使用全名去访问
 
#---------------
s = 'Hello, Runoob'
print(repr(s)) #带'' 解释器易读

x=2
print(repr(x).rjust(5), repr(x * x).rjust(5), end=' ') #rjust右对齐，有ljust,center
print("\n12".zfill(5))#左补0
print()
for x in range(1, 3):
    print('{0:2d} {1:3d} {2:4d}'.format(x, x*x, x*x*x))  #string.format {}
print('{1} 和 {0}'.format('Google', 'Runoob'))
print('{name}网址： {site}'.format(name='菜鸟教程', site='www.runoob.com'))
print('站点列表 {0}, {1}, 和 {other}。'.format('Google', 'Runoob', other='Taobao'))

print('常量 PI 的值近似为： {!r}。'.format(math.pi)) #'!a' (使用 ascii()), '!s' (使用 str()) 和 '!r' (使用 repr())
print('常量 PI 的值近似为 {0:.3f}。'.format(math.pi)) #保留到小数点后三位
print('{0:10}==>{1:10d}'.format('Google',1)) #至少有这么多的宽度

table = {'Google': 1, 'Runoob': 2, 'Taobao': 3}
print('Runoob: {0[Runoob]:d}; Google: {0[Google]:d}; Taobao: {0[Taobao]:d}'.format(table))  #[]内的是dict的key  ,[]前要为0
print('Runoob: {Runoob:d}; Google: {Google:d}; Taobao: {Taobao:d}'.format(**table))


f=open("input.txt","r")#像C多了一个b，共有r,rb,r+,rb+,w,wb,w+,wb+,a,ab,a+,ab+
for line in f:  #可用for in file返回是每行 ,f.next()返回下一行
    print(line,end='')

# f.seek(4,0) #移动4个 ，第二个参数0表示从文件头开始，1表示从当前位置，2表示从文件尾开始向前移动
f.seek(4) #r方式打开可以seek
print(f.read(1))
f.close() 

f=open("out.txt","a")
f.write("中文1")  #字符集是GBK(文件字符集)
f.write("64789")
pos=f.tell() #文件开头开始算起的字节数。
print("POS=%s" % pos)
f.close()


from urllib import request

response = request.urlopen("http://www.baidu.com/")
fi = open("baidu.txt", 'w')
page = fi.write(str(response.read()))
fi.close()

#--- 序列和反序列化
import pprint, pickle
data1 = {'a': [1, 2.0, 3, 4+6j],
         'b': ('string', u'Unicode string'),
         'c': None}
output = open('data.pkl', 'wb')
pickle.dump(data1, output) #pickle.dump 第二参数是已经open的file
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
print(os.pardir) #表示 .. 上一级目录
print(os.getcwd() + os.sep + "filename"  + os.linesep)

#还有很多方法 http://www.runoob.com/python3/python3-os-file-methods.html

#--exception
try:
    f = open('input.txt')
    s = f.readline()
   # 5/0
   # i = int(s.strip())
    #raise NameError('HiThere') #Exception 的子类
except FileNotFoundError as err: #别名
     print("file lookup: {0}".format(err))
except (ValueError, ZeroDivisionError ):  #可以多个
     print("value Zero ERROR:")
except:   #except子句可以忽略异常的名称
    print("except error:", sys.exc_info()[0]) #有信息
    raise  #向中抛
else :  #必须放在所有的except子句之后 ,  没有发生任何异常的时候执行
    print("成功执行" )
finally:
    print("executing finally clause")

#关键词 with 语句就可以保证诸如文件之类的对象在使用完之后一定会正确的执行他的清理方法:
with open("input.txt") as f:
    for line in f:
        print(line, end="")
#可以不调用f.close()

#---class
class MyClass: #()可有可无
    i = 12345
    def f(self): #与一般函数定义不同，类方法必须包含参数 self，且为第一个参数,也可以使用 this
        return 'hello world'

    def __init__(self): #名为 __init__() 的特殊方法（构造方法），可多加参数
        self.data = []
        print(self.__class__) #类名
x = MyClass()
print("MyClass 类的属性 i 为：", x.i)
print("MyClass 类的方法 f 输出为：", x.f())


class MyChildClass(MyClass):
    __weight = 0 # 两个下划线开头，声明该属性为私有
    name=""

    def __foo(self):  # 私有方法
        print('这是私有方法')
    def __init__(self, w,name):
        self.__weight = w
        self.name=name
        MyClass.__init__(self)

    def f(self):
        print("child hello world")


s = MyChildClass(200,"wang")
#print(s.__weight) #不能仿问
#print(s.__foo()) #不能仿问
print(s.name)
print(s.f())

'''类的专有方法： __init__ 
__del__ : 析构函数，释放对象时使用
__repr__ : 打印，转换
__setitem__ : 按照索引赋值
__getitem__: 按照索引获取值
__len__: 获得长度
__cmp__: 比较运算
__call__: 函数调用
__add__: 加运算   
__sub__: 减运算
__mul__: 乘运算
__div__: 除运算
__mod__: 求余运算
__pow__: 乘方
'''


class Vector:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __str__(self): #toString()
        return 'Vector (%d, %d)' % (self.a, self.b)

    def __add__(self, other):  # 运算符重载
        return Vector(self.a + other.a, self.b + other.b)
v1 = Vector(2, 10)
v2 = Vector(5, -2)
print(v1 + v2)
#help(os)
help(os.chdir)

import glob
print(glob.glob('*.py'))  #搜索文件

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
#re.match只匹配字符串的开始，如果字符串开始不符合正则表达式，则匹配失败
if matchObj:
   print ("matchObj.group() : ", matchObj.group())
   print ("matchObj.group(1) : ", matchObj.group(1))
   print ("matchObj.group(2) : ", matchObj.group(2))
else:
   print ("No match!!")
#而re.search匹配整个字符串，直到找到一个匹配。
matchObj = re.match( r'dogs', line, re.M|re.I)
if matchObj:
   print ("match --> matchObj.group() : ", matchObj.group())
else:
   print ("No match!!")

#re.M	多行匹配，影响 ^ 和 $
matchObj = re.search( r'dogs', line, re.M|re.I)
if matchObj:
   print ("search --> matchObj.group() : ", matchObj.group())
else:
   print ("No match!!")


# 将匹配的数字乘于 2
def doubleFunc(matched):
   value = int(matched.group('value')) #value是key,  对应  #(?P<value>\d+)
   #value = int(matched.group())# 对应 '(\d+)'
   return str(value * 2)

s = 'A23G4HFD567'
#print(re.sub('(\d+)', doubleFunc, s))  #matched.group()
print(re.sub('(?P<value>\d+)', doubleFunc, s)) #matched.group('value')
#-----------
import time
ticks = time.time() #1970纪元后经过的浮点秒数
print ("当前时间戳为:", ticks)

localtime = time.localtime(time.time())
print ("本地时间为 :", localtime)

print (time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))

# 将格式字符串转换为时间戳
a = "Sat Mar 28 22:24:24 2016"
print (time.mktime(time.strptime(a,"%a %b %d %H:%M:%S %Y")))


import calendar

cal = calendar.month(2016, 1) #星期一是默认的每周第一天
print ("以下输出2016年1月份的日历:")
print (cal)

print("time.ctime() : %s" % time.ctime())
#	time.ctime([secs]) 作用相当于asctime(localtime(secs))，未给参数相当于asctime()







