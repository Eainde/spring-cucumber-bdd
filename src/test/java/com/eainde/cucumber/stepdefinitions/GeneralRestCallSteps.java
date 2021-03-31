package com.eainde.cucumber.stepdefinitions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.eainde.cucumber.BehaviourState;
import com.eainde.cucumber.constants.StateConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.io.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GeneralRestCallSteps {
    private final RestTemplate restTemplate;
    private final BehaviourState behaviourState;
    private final ObjectMapper objectMapper;

    GeneralRestCallSteps(final RestTemplate restTemplate,
                         final BehaviourState behaviourState,
                         final ObjectMapper objectMapper){
        this.restTemplate=restTemplate;
        this.behaviourState=behaviourState;
        this.objectMapper=objectMapper;
    }

    @Given("path {string}")
    public void givenPath(final String path){
        final UriComponentsBuilder uriComponentsBuilder=
                behaviourState.fetchValue(StateConstants.URI_BUILDER, UriComponentsBuilder.class);
        behaviourState.putResult(StateConstants.URI_BUILDER, ()-> uriComponentsBuilder.replacePath(path));
    }

    @Given("query parameter {string} is {string}")
    public void queryParameter(final String parameterName, final String parameterValue){
        behaviourState.map(StateConstants.URI_BUILDER,
                UriComponentsBuilder.class,
                spec -> spec.queryParam(parameterName,parameterValue));
    }

    @When("request body is {string}")
    public void requestBody(final String bodyName) throws IOException{
        final var body=objectMapper.readValue(Resources.toString(
                Resources.getResource(String.format("reuestBody/%s.json", bodyName)),
                StandardCharsets.UTF_8),
                Map.class);
        behaviourState.putResult(StateConstants.REQUEST_BODY, () -> new HttpEntity(body));
    }

    @When("a request is made using method {string}")
    public void sendRequest(final String method){
        final UriComponentsBuilder uriComponentsBuilder=
                behaviourState.fetchValue(StateConstants.URI_BUILDER, UriComponentsBuilder.class);
        behaviourState.putResult(StateConstants.URI_BUILDER, ()-> sendRestCall(method, uriComponentsBuilder));
    }

    @Then("the request should respond with the status code {int}")
    public void shouldResponseWithStatusCode(final int statusCode){
        final var response=behaviourState.fetchValue(StateConstants.RESPONSE_ENTITY, ResponseEntity.class);
        assertThat(response.getStatusCode().value(), equalTo(HttpStatus.valueOf(statusCode)));
    }

    @Then("the response body should match {string}")
    public void responseBodyShouldMatch(final String responseBody) throws IOException{
        final var response= behaviourState.fetchValue(StateConstants.RESPONSE_ENTITY,ResponseEntity.class);
        final var actualMap= objectMapper.readTree((String) response.getBody());
        final var expectedMap= objectMapper.readTree(Resources.toString(Resources.getResource(String.format("expectedResults/%s.json", responseBody)),
                StandardCharsets.UTF_8));
        assertThat(actualMap, equalTo(expectedMap));
    }

    private ResponseEntity<String> sendRestCall(final String method, final UriComponentsBuilder uriComponentsBuilder){
        return restTemplate.exchange(uriComponentsBuilder.toUriString(),
                HttpMethod.resolve(method.toUpperCase()),
                method.equalsIgnoreCase("GET")
                ? null : behaviourState.fetchValue(StateConstants.REQUEST_BODY, HttpEntity.class),
                String.class);
    }

}
