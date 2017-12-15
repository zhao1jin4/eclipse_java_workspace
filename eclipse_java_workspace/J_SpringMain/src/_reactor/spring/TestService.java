package _reactor.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;

@Service(value="testService")
public class TestService {

  @Autowired
  private Reactor rootReactor;

  public void fireEvent(String s) {
    rootReactor.notify("test.topic", Event.wrap(s));
  }

}