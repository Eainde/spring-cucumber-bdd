package com.eainde.cucumber.stepdefinitions;

import com.eainde.cucumber.BehaviourState;
import com.eainde.cucumber.constants.StateConstants;
import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
@ContextConfiguration
public class SpringSteps {
    private static final String BASE_URI="http://localhost";
    private final int port;
    private final BehaviourState behaviourState;

    SpringSteps(@LocalServerPort final int port,
                final BehaviourState behaviourState){
        this.port=port;
        this.behaviourState=behaviourState;
    }

    @Given("Spring is running")
    public void startUpSpring(){
        behaviourState.clear();
        behaviourState.putResult(StateConstants.URI_BUILDER, () -> UriComponentsBuilder.fromHttpUrl(BASE_URI).port(port));
    }
}
