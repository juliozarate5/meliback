package co.com.mercadolibre.challengefullstack.repository;

import co.com.mercadolibre.challengefullstack.model.Article;
import reactor.core.publisher.Flux;

public interface IProductorepository {
  Flux<Article> findAll();
}
