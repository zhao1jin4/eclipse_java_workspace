print("---------")
m_total=100
_kind="python"
print("---------"+_kind)
def fib2(n): # 返回到 n 的斐波那契数列
    result = []
    a, b = 0, 1
    while b < n:
        result.append(b)
        a, b = b, a+b
    return result


if __name__ == '__main__':   #区分是不是被引入的
   print('程序自身在运行')
else:
   print('我来自另一模块')

