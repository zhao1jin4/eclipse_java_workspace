package _reactor.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
@ComponentScan
public class ReactorConfig {

  @Bean(name = "rootReactor")
  public Reactor rootReactor(Environment env) {
    // implicit Environment is injected into bean def method
    return Reactors.reactor().env(env).get();
  }

}