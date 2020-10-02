package statemachin_proj;
/*
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class SpringBootMain implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }
  	@Resource
    StateMachine<States, Events> stateMachine;
    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();//初始draft状态
        boolean isOK= stateMachine.sendEvent(Events.ONLINE);//发送事件,会产生状态变化configure方法中,会触发@OnTransition(target =XX) 所在函数
        stateMachine.sendEvent(Events.PUBLISH);//如注释这行，状态切换违法,返回false
        isOK=stateMachine.sendEvent(Events.ROLLBACK);
        System.out.println(isOK);
    }
}
*/
