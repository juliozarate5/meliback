package co.com.mercadolibre.challengefullstack.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import co.com.mercadolibre.challengefullstack.ChallengeFullstackApplication;
import co.com.mercadolibre.challengefullstack.mapper.IProductHelperMapper;
import co.com.mercadolibre.challengefullstack.model.Article;
import co.com.mercadolibre.challengefullstack.model.Event;
import co.com.mercadolibre.challengefullstack.model.Launch;
import co.com.mercadolibre.challengefullstack.model.dto.ArticleDto;
import co.com.mercadolibre.challengefullstack.model.dto.ErrorDto;
import co.com.mercadolibre.challengefullstack.repository.IProductorepository;
import co.com.mercadolibre.challengefullstack.utils.Pagination;
import io.vavr.control.Either;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = ChallengeFullstackApplication.class)
public class ProductServiceTest {

  private static final MockWebServer mockWebServer = new MockWebServer();

  @InjectMocks ProductService productService;

  @Mock private IProductorepository productRepository;

  @Mock private IProductHelperMapper mapper;

  @AfterClass
  public static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void listArticlesError() {
    Article article =
        Article.builder()
            .id("id")
            .featured(true)
            .title("title")
            .url("url")
            .image("image")
            .newsSite("site")
            .summary("summary")
            .publishedAt(LocalDateTime.now())
            .launches(
                Collections.singletonList(Launch.builder().id("id").provider("provider").build()))
            .events(Collections.singletonList(Event.builder().id("id").provider("").build()))
            .build();
    Flux<Article> articleFlux = Flux.just(article);
    when(productRepository.findAll()).thenReturn(articleFlux);
    Flux<ArticleDto> articleDtoFlux =
        Flux.just(ArticleDto.builder().title("title").url("url").image("image").build());
    Pageable page = PageRequest.of(0, 10);
    ErrorDto expected = ErrorDto.builder().error(null).message(null).build();
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .setHeader(HttpHeaders.CONTENT_TYPE, "APPLICATION_JSON"));
    // actual
    Either<ErrorDto, Mono<Pagination<ArticleDto>>> productTest =
        productService.listArticles(PageRequest.of(0, 10));
    // assert
    assertEquals(expected.getError(), productTest.getLeft().getError());
  }
}
