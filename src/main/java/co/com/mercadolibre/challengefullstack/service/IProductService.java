package co.com.mercadolibre.challengefullstack.service;

import co.com.mercadolibre.challengefullstack.model.dto.ArticleDto;
import co.com.mercadolibre.challengefullstack.model.dto.ErrorDto;
import co.com.mercadolibre.challengefullstack.utils.Pagination;
import io.vavr.control.Either;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IProductService {

  Either<ErrorDto, Mono<Pagination<ArticleDto>>> listArticles(Pageable page);
}
