package main

import (
"fmt"
)

func fibonacci(n int, c chan int) {
	x, y := 0, 1
	for i := 0; i < n; i++ {
		c <- x
		x, y = y, x+y
	}
	close(c)//关闭channel
}

func main() {
	c := make(chan int, 10)//10个缓冲区

	go fibonacci(cap(c), c) //slice 的cap函数 容量

	// range 函数遍历每个从通道接收到的数据，因为 c 在发送完 10 个
	// 数据之后就关闭了通道，所以这里我们 range 函数在接收到 10 个数据
	// 之后就结束了。如果上面的 c 通道不关闭，那么 range 函数就不
	// 会结束，从而在接收第 11 个数据的时候就阻塞了。

	for i := range c { //range 来(遍历)取 channel,是在关闭之后
		fmt.Println(i)
	}

}