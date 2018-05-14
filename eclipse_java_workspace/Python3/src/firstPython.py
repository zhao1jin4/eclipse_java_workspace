#!/usr/local/bin/python3
#/usr/bin/python  是2 版本

'''
  书中的
多行注释
控制台退出使用exit()  或ctrl+Z
'''
from sys import stderr

for i in range(5): 
   print(i)  #utf-8 file ,必须缩进   大小写严格 
print('hello \nworld') #可用' ' ,也可用 " "
print ((6-4j)*(6+4j)) #复数
print ( 7/2)
print ( 7.0/2) 


#n=n+1 #变量使用前必须初始化 
n=2
#n++ #没有 ++ --

#转换
print( float(26))
print( int(5.2))
print( hex(26))

(x,y)=(10.0,50)#多变量赋值
print(x)
print(y)


import math
print(math.pi,math.e) # , 打印出是空格分隔
print(math.sqrt(3**2 + 4**2)) #** 是平方

import cmath #得数
import random
print ("random=",random.random()) #0到1

#-----------string

print ("first="+str(20) )#必须str转换
print ("first=", 20  ,) #最后一个,没有空格
print ("string=%s num=%d" % ('hello',20 )) # %像函数

name="wang"
print ("name=%s  " % name) #  

print ("_" *5) #显示5个_
print ( len("hello"))
print (  "hello"[1])

worlds= "   left and right strip  "
print ( worlds.strip())
print ( worlds.lstrip())
print ( worlds.rstrip())
print ( worlds.center(100))

print ( worlds.split())#默认空白,可传,返回list
print ( "name,20".split(",")) 

print( "-".join(worlds.split()) )

print( "/usr/bin/bash".find("/") )
print( "/usr/bin/bash".rfind("/") )
print( "/usr/bin/bash".replace("/", "\\") ) 
print( "/usr/bin/bash".count("/") ) #出现次数
#----------list



myList=[3,"my list item",2.345,n] #任意类型
print(myList[1]) 
myList[0]+=3
print(myList[0])
print(myList[-1]) #最后一个元素
print("len=",len(myList)) #最后一个元素


#----range
numList=range(10) 
sublist=numList[2:6]
print("sublist=",sublist)
sublistHead=numList[:6]
sublistTail=numList[5:]
print(sublist)


print( numList[-1])
print("numList=",numList[-1])
#---list methond

strList=["10","5","a","x"]
strList[1:4]=["55","aa"]
strList.sort()
strList.reverse()
strList.append(99)
print(" append strList=",strList )

oneList=["one","tow","three"]
oneList.extend(strList)
print("oneList=",oneList )
oneList.insert(2,"2222") #后面的元素向后推
print("after insert oneList=",oneList )

delElement=oneList.pop(2);
print("  oneList=",oneList )
oneList.pop() #删最后一个,并返回
print("  oneList=",oneList )


#-------dict

dict={} #空的
dict['1']="Monday"
dict['2']=['Tuesday',"two"]
dict['2'].append("二")

print(dict)

weekDict={
    "one":"monday",
    "two":"tuesday",
    "three":"wedsday"
    }
print(weekDict)
print(weekDict.keys())#dict_keys(['one', 'two', 'three'])
print(weekDict.__contains__("one")) 
print(weekDict.__len__()  ) 
print(len(weekDict))

weekDict.__delitem__("one") 
print(weekDict)
 
#------------function ,for, if
def countWorld(lines,mapWord): #后要有:
    for num in lines: # 后要有:
        if num == 3 : # 后要有: == 可用于任可相同的类型
            print("three")
        elif num ==4: # 后要有:
            print("four")
        else:  # 后要有:
            print("other=",num)
    print("map len=",len(mapWord))

countWorld(range(0,5),{})         
print("123"=="123")      

inputList=["1","2",".","3"]
while True:  #True 首字母大字 关键字
    print( inputList.pop(0))
    if inputList[0]==".":
        break
#------------IO 
''' 
print("please input a lot of world,会把命行参数做为文件去读内容,如没有就从stdin读入内容")
import fileinput,re
for line in fileinput.input():
    print(fileinput.filename()+" first line is ")#<stdin>
    print(line)
    worlds=re.findall(r"\w+",line.lower())
    print(worlds)
    break

 '''
import time
print("准备写文件" )
fileOut=open("output.txt","a") #w a
fileOut.write(time.asctime()+"\n")#write不会在文件未尾增加空行
#fileOut.close()

filein=open("input.txt","r") #当前目录 中
'''
print(filein.readline())#readline包括换行符
print(filein.readline().rstrip())
print(filein.readline().rstrip())
filein.close()
'''

'''
for line in filein.readlines():  #readlines返回list
    print(line.rstrip().center(80))

'''
 
print( filein.read() )
print("end of read file" )

import sys
#print("please input something")
#stdinMsg=sys.stdin.read() #结束输入 按ctrl+Z

sys.stderr.write("this is my error message") # 不一定是按顺序打印的

print("program file name =%s ,args=%s" % (sys.argv[0],sys.argv[1:] ))

#fileOut.write(filein.read()) #复制文件

#getopt模块 ,linux shell也有这个功能

#----------OS command

import os,sys
'''
if not os.path.isfile("c:/tmp/input1.txt"):
    print("file input1 not exist")
    
if not os.path.isdir("c:/temp"):
    print("dir temp not exist")

for filename in os.listdir(os.getcwd()): #cwc当前目录
    print(filename)
    
if not os.path.isdir("c:/temp"):
    os.mkdir("c:/temp")
if not os.path.isfile("c:/temp/input_new.txt"):  
    os.renames("c:/tmp/input.txt", "c:/temp/input_new.txt")#移动重命名
    
import shutil
shutil.copy("c:/temp/input_new.txt", "c:/tmp/input.txt")

os.remove("c:/temp/input_new.txt")
os.removedirs("c:/temp")  #目录必须是空的
shutil.copytree("c:/tmp/", "c:/temp/")


os.system("where python") #结果写到标准输出上
'''
#---------- pipe
'''
readpipe=os.popen("netstat -an ","r")
for line in readpipe.readlines():
    print (line.rstrip())


#netstat -an | find "LISTENING"
readpipe=os.popen("netstat -an ","r")
writepipe=os.popen('find  "LISTENING"',"w")
writepipe.write(readpipe.read())
'''
#---------- regular expression
import re
maillist=["lisi@sina.com","wang@163.com}","sun@163.com"]
mailRe=re.compile(r"sina")
for email in maillist:
    if mailRe.search (email):
        print (email,"is match")
 
worlds="knock the Knote"
regexp=re.compile(r"kn",re.M|re.I )#re.I忽略大小写
if regexp.match(worlds): #只从开头开始找
    groupRes=regexp.match(worlds).group()
    print(groupRes)

mailReg=re.compile(r"(.*)@(.*)")
(username,hostname)=mailReg.search("lisi@sina.com").groups()
print(username,hostname)


regSplit=re.compile(r",")
splitList=regSplit.split("lisi,30")
print(splitList)


hello="Hello World"
hellRe=re.compile(r"World")
newhello=hellRe.sub("sailor",hello ) #替换
print(newhello)
#写法二
newhello=re.sub(r"World","sailor",hello)
print(newhello)

numReg=re.compile(r"\d")
res=numReg.sub('X','input 123,password 455')
print(res)

#-----------class
#class MyClass(ParentClass):
class MyClass():
    def method1(self):
        self.x=1024
    def method2(self,newx):
        self.x=newx   
    def method3(self):
        return self.x    
obj=MyClass()
obj.method1()
obj.method2(333)
print(obj.method3())

#-----------Exception

try:
    filein=open("file1.txt","r")
except IOError:
    sys.stderr.write("can not open file file1.txt")
    #如何打印错误消息
    sys.exit(2)
    
#命令行调试程序  python -m pdb firstPython.py  
        



