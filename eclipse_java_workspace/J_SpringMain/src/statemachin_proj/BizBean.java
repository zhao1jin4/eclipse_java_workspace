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
        log.info("�������ߣ�������. target status:{}", States.PUBLISH_TODO.name()); 
    }

    @OnTransition(target = "PUBLISH_DONE")
    public void publish() {
        log.info("��������,�������. target status:{}", States.PUBLISH_DONE.name()); 
    }

    @OnTransition(target = "DRAFT")//��ʼ����ʱ��״̬,��ִ������,����Ŀ��״̬Ϊ���ʱִ��
    public void rollback() {
        log.info("�����ع�,�ص��ݸ�״̬. target status:{}", States.DRAFT.name()); 
    }
}
