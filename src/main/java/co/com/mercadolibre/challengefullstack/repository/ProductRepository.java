package co.com.mercadolibre.challengefullstack.repository;

import co.com.mercadolibre.challengefullstack.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Repository
public class ProductRepository implements IProductorepository {

  private static final Logger LOG = LoggerFactory.getLogger(ProductRepository.class);

  @Autowired private WebClient webClient;

  public Flux<Article> findAll() {
    return webClient.get().retrieve().bodyToFlux(Article.class);
  }
}
