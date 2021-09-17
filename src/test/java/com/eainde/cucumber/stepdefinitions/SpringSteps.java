package com.eainde.cucumber.stepdefinitions;

import com.eainde.cucumber.BehaviourState;
import com.eainde.cucumber.containers.ExternalServiceContainer;
import com.eainde.cucumber.containers.PostgresContainer;
import org.junit.ClassRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import com.eainde.cucumber.constants.StateConstants;

import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
@ContextConfiguration(initializers = SpringSteps.Initiliazer.class)
@ActiveProfiles("test")
public class SpringSteps {
  private static final String BASE_URI = "http://localhost";
  private final int port;
  private final BehaviourState behaviourState;

  SpringSteps(@LocalServerPort final int port, final BehaviourState behaviourState) {
    this.port = port;
    this.behaviourState = behaviourState;
  }

  @Given("Spring is running")
  public void startUpSpring() {
    behaviourState.clear();
    behaviourState.putResult(
        StateConstants.URI_BUILDER, () -> UriComponentsBuilder.fromHttpUrl(BASE_URI).port(port));
  }

  @ClassRule
  private static final PostgresContainer POSTGRES_CONTAINER=PostgresContainer.getInstance();

  @ClassRule
  private static final ExternalServiceContainer SERVICE_CONTAINER=ExternalServiceContainer.getInstance();

  static class Initiliazer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      System.setProperty("spring.profiles.active","test");
      POSTGRES_CONTAINER.start();
      SERVICE_CONTAINER.start();
    }
  }
}
