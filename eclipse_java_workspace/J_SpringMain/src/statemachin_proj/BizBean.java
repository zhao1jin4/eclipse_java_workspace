package statemachin_proj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine 
public class BizBean {
	Logger log =LoggerFactory.getLogger(BizBean.class);
	@OnTransition(target = "PUBLISH_TODO")
    public void online() {
        log.info("操作上线，待发布. target status:{}", States.PUBLISH_TODO.name()); 
    }

    @OnTransition(target = "PUBLISH_DONE")
    public void publish() {
        log.info("操作发布,发布完成. target status:{}", States.PUBLISH_DONE.name()); 
    }

    @OnTransition(target = "DRAFT")//初始启动时的状态,会执行这里,即当目标状态为这个时执行
    public void rollback() {
        log.info("操作回滚,回到草稿状态. target status:{}", States.DRAFT.name()); 
    }
}
