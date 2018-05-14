#!/usr/bin/python3

import threading
import time

threadLock = threading.Lock()
class myThread (threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)  #必须调用
        self.threadID = threadID
        self.name = name
        self.counter = counter
    def run(self):
        print ("开始线程：" + self.name)
        threadLock.acquire()
        print_time(self.name, self.counter, 5)
        threadLock.release()
        print ("退出线程：" + self.name)

def print_time(threadName, delay, counter):
    while counter:
        time.sleep(delay)#秒
        print ("%s: %s" % (threadName, time.ctime(time.time())))
        counter -= 1

# 创建新线程
thread1 = myThread(1, "Thread-1", 1)
thread2 = myThread(2, "Thread-2", 2)

# 开启新线程
thread1.start()
thread2.start()
thread1.join()  #和Java不同
thread2.join()
print ("退出主线程")