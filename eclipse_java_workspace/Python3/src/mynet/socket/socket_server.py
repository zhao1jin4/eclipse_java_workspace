#!/usr/bin/python3
import socket
import sys
serversocket = socket.socket(
    socket.AF_INET, socket.SOCK_STREAM)  #socket.SOCK_STREAM ,socket.SOCK_DGRAM

host = socket.gethostname()
port = 9999
serversocket.bind((host, port))

# 设置最大连接数，超过后排队
serversocket.listen(5)

while True:
    clientsocket, addr = serversocket.accept() #返回元组

    print("连接地址: %s" % str(addr))

    msg = '欢迎访问菜鸟教程！' + "\r\n"
    clientsocket.send(msg.encode('utf-8'))
    clientsocket.close()