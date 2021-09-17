package com.eainde.cucumber.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

public class PostgresContainer extends GenericContainer<PostgresContainer> {
    private static final Logger LOGGER= LoggerFactory.getLogger(PostgresContainer.class);
  private static final int MAPPED_PORT = 5432;
  private static final String IMAGE_VERSION = "postgres";
  private static PostgresContainer container;

  private PostgresContainer() {
    super(IMAGE_VERSION);
  }

  public static PostgresContainer getInstance() {
    if (container == null) {
      container = new PostgresContainer();
      container.setExposedPorts(ImmutableList.of(MAPPED_PORT));
      container.addEnv("POSTGRES_PASSWORD", "secret");
      container.addEnv("POSTGRES_USER", "postgres");
    }
    return container;
  }

  @Override
  public void start(){
      super.start();
      System.getProperty("spring.datasource.url", String.format("jdbc:postgresql://%s:%s/postgres",
                      container.getHost(),container.getMappedPort(MAPPED_PORT)));
      System.getProperty("spring.datasource.password", container.getEnvMap().get("POSTGRES_PASSWORD"));
      System.getProperty("spring.datasource.username", container.getEnvMap().get("POSTGRES_USER"));
      LOGGER.info("PostgreSQL started on {} : {}", container.getHost(), container.getMappedPort(MAPPED_PORT));
  }
}
