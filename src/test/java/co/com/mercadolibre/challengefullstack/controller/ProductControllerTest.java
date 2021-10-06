package co.com.mercadolibre.challengefullstack.controller;

import co.com.mercadolibre.challengefullstack.ChallengeFullstackApplication;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/** @author Julio Martinez */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = ChallengeFullstackApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

  @Autowired private ApplicationContext context;

  @Autowired private WebTestClient webTestClient;

  @Before
  public void setUp() {
    webTestClient = WebTestClient.bindToApplicationContext(context).build();
  }

  @Test
  public void getEntitiesPageSuccess() {
    webTestClient
        .get()
        .uri("/api/v1/products")
        .exchange()
        .expectBody()
        .jsonPath("$.data")
        .isArray()
        .jsonPath("$.data.length()")
        .isEqualTo(10)
        .jsonPath("pageNumber")
        .isEqualTo(0)
        .jsonPath("pageSize")
        .isEqualTo(10)
        .jsonPath("totalElements")
        .isEqualTo(10)
        .jsonPath("first")
        .isEqualTo(true)
        .jsonPath("last")
        .isEqualTo(true)
        .jsonPath("totalPages")
        .isEqualTo(1);
  }

  @Test
  public void getEntitiesPagePaginationSuccess() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/v1/products")
                    .queryParam("page", 0)
                    .queryParam("size", 10)
                    .build())
        .exchange()
        .expectBody()
        .jsonPath("$.data")
        .isArray()
        .jsonPath("$.data.length()")
        .isEqualTo(10)
        .jsonPath("pageNumber")
        .isEqualTo(0)
        .jsonPath("pageSize")
        .isEqualTo(10)
        .jsonPath("totalElements")
        .isEqualTo(10)
        .jsonPath("first")
        .isEqualTo(true)
        .jsonPath("last")
        .isEqualTo(true)
        .jsonPath("totalPages")
        .isEqualTo(1);
  }

  @Test
  public void getEntitiesPageError404() {
    webTestClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/api/v1/products")
                    .queryParam("page", 0)
                    .queryParam("size", "xxx")
                    .build())
        .exchange()
        .expectBody()
        .jsonPath("status")
        .isEqualTo(400);
  }

  @Test
  public void getEntitiesPageError() {
    webTestClient.get().uri("").exchange().expectStatus().is4xxClientError();
  }
}
