package com.eainde.cucumber.stepdefinitions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eainde.cucumber.BehaviourState;
import com.eainde.cucumber.constants.StateConstants;

import com.eainde.cucumber.BehaviourState;
import com.eainde.cucumber.constants.StateConstants;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HealthCheckSteps {
  private static final String HEALTH_PATH = "health";
  private final BehaviourState behaviourState;
  private final RestTemplate restTemplate;

  HealthCheckSteps(final BehaviourState behaviourState, final RestTemplate restTemplate) {
    this.behaviourState = behaviourState;
    this.restTemplate = restTemplate;
  }

  @Given("^send get request for the health service$")
  public void healthUrlGetRequest() {
    final UriComponentsBuilder uriComponentsBuilder =
        behaviourState.fetchValue(StateConstants.URI_BUILDER, UriComponentsBuilder.class);
    behaviourState.putResult(
        StateConstants.RESPONSE_ENTITY,
        () ->
            restTemplate.getForEntity(
                uriComponentsBuilder.path(HEALTH_PATH).build().toUri(), String.class));
  }

  @Then("^it should return status as up$")
  public void userShouldGetStatusUp() {
    final ResponseEntity<?> response =
        behaviourState.fetchValue(StateConstants.RESPONSE_ENTITY, ResponseEntity.class);
    assertThat(response.getStatusCode().value(), is(200));
  }
}
