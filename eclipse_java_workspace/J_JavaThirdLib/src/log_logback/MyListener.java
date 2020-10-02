package log_logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
//这个类会在 启动 SpringBoot项目的 main方法前被执行
//这个类要配置在logback.xml中才生效 <contextListener class="logback.MyListener"/>
public class MyListener extends ContextAwareBase  implements LoggerContextListener,LifeCycle{
	private boolean started=false;
	@Override
	public boolean isStarted() {
		return false;
	}
	@Override
	public void start() {
		if(started)
			return;
		Context context=getContext();
		context.putProperty("INIT_ROOT_LEVEL", "debug");
		//这个就可以放在logback.xml中${INIT_ROOT_LEVEL}
		started=true;
	}
	@Override
	public boolean isResetResistant() {
		return false;
	}
	@Override
	public void stop() {
	}
	@Override
	public void onLevelChange(Logger arg0, Level arg1) {
	}

	@Override
	public void onReset(LoggerContext arg0) {
	}
	@Override
	public void onStart(LoggerContext arg0) {
	}
	@Override
	public void onStop(LoggerContext arg0) {
	}
}
