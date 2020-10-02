package webflux.sse;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@RestController
@RequestMapping("/sse")
public class SseController {
    @GetMapping("/randomNumbers")
    //服务器推送事件(单向的)，返回ServerSentEvent
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1)) //返回每秒数，1,2,3向下传到seq变量
        		//ThreadLocalRandom
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))//向下传为Tupel2到data变量
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }
}