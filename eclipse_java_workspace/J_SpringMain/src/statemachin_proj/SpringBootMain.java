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
        stateMachine.start();//��ʼdraft״̬
        boolean isOK= stateMachine.sendEvent(Events.ONLINE);//�����¼�,�����״̬�仯configure������,�ᴥ��@OnTransition(target =XX) ���ں���
        stateMachine.sendEvent(Events.PUBLISH);//��ע�����У�״̬�л�Υ��,����false
        isOK=stateMachine.sendEvent(Events.ROLLBACK);
        System.out.println(isOK);
    }
}
*/
