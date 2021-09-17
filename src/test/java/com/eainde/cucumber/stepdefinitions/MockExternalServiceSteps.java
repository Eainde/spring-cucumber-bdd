package com.eainde.cucumber.stepdefinitions;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import com.eainde.cucumber.containers.ExternalServiceContainer;
import com.google.common.io.Resources;
import io.cucumber.java.en.And;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import io.vavr.control.Try;

public class MockExternalServiceSteps {
  private final MockServerClient mockServerClient;
  private static final String BASE_PATH = "expectedResults/";

  public MockExternalServiceSteps() {
    final var mockServiceContainer = ExternalServiceContainer.getInstance();
    this.mockServerClient =
        new MockServerClient(
            mockServiceContainer.getHost(), mockServiceContainer.getFirstMappedPort());
  }

  @And("mock service {string} with response {string}")
  public void mockService(final String url, final String responseFileName) {
    setupResponseBody(responseFileName, url);
  }

  @And("fail service {string}")
  public void failService(final String url) {
    setupResponseBody(url);
  }

  @And("clear all mocked services")
  public void clearMockedService() {
    mockServerClient.reset();
  }

  private void setupResponseBody(final String responseFileName, final String url) {
    mockServerClient
        .when(request().withPath(url))
        .respond(
            response()
                .withStatusCode(200)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(fetchJsonFile(responseFileName)));
  }

  private void setupResponseBody(final String url) {
    mockServerClient.when(request().withPath(url)).respond(response().withStatusCode(500));
  }

  private byte[] fetchJsonFile(final String responseFileName) {
    return Try.of(
            () ->
                Resources.toByteArray(
                    Resources.getResource(String.format("%s%s.json", BASE_PATH, responseFileName))))
        .getOrElseThrow(e -> new IllegalArgumentException("Unable to load response ", e));
  }
}
