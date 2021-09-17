package com.eainde.cucumber.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class ExternalServiceContainer extends GenericContainer<ExternalServiceContainer> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExternalServiceContainer.class);
  public static final int MAPPED_PORT = 1080;
  public static final String IMAGE_VERSION = "mockserver/mockserver:mockserver-5.11.2";
  private static ExternalServiceContainer container;

  private ExternalServiceContainer() {
    super(IMAGE_VERSION);
  }

  public static ExternalServiceContainer getInstance() {
    if (container == null) {
      container =
          new ExternalServiceContainer()
              .withEnv("MOCKSERVER_LIVENESS_HTTP_GET_PATH", "/health")
              .waitingFor(Wait.forHttp("/health").forStatusCode(200));
      container.addExposedPort(MAPPED_PORT);
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    final var baseUrl =
        String.format("http://%s:%s", container.getHost(), container.getMappedPort(MAPPED_PORT));
   // System.setProperty("ads.env", baseUrl);
    LOGGER.info("Mock service started on {}", baseUrl);
  }
}
