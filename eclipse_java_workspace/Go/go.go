package main

import (
	"fmt"
	"time"
)

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)//线程等待
		fmt.Println(s)
	}
}

func main() {
	go say("world") //启动新线程
	say("hello")
}