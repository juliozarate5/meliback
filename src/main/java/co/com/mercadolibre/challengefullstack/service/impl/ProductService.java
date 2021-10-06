package co.com.mercadolibre.challengefullstack.service.impl;

import co.com.mercadolibre.challengefullstack.mapper.IProductHelperMapper;
import co.com.mercadolibre.challengefullstack.model.Article;
import co.com.mercadolibre.challengefullstack.model.dto.ArticleDto;
import co.com.mercadolibre.challengefullstack.model.dto.ErrorDto;
import co.com.mercadolibre.challengefullstack.repository.IProductorepository;
import co.com.mercadolibre.challengefullstack.service.IProductService;
import co.com.mercadolibre.challengefullstack.utils.Pagination;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.time.Duration;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

  private static final int CACHE_FIXED_RATE = 5 * 60 * 1000;

  @Value("${cache.time}")
  private static int CACHE_TIME;

  @Autowired private IProductorepository productorepository;

  @Autowired private IProductHelperMapper mapper;

  @Autowired private CacheManager cacheManager;

  /**
   * get Products list per page
   *
   * @param page
   * @return
   */
  @Override
  public Either<ErrorDto, Mono<Pagination<ArticleDto>>> listArticles(Pageable page) {
    return Try.of(() -> getListArticles(page))
        .onFailure(error -> LOG.error(error.getMessage()))
        .fold(exception -> Either.left(new ErrorDto()), this::processArticleResponse);
  }

  @Cacheable(value = "articleDto", key = "all") // simulate Redis u other
  private Mono<Pagination<ArticleDto>> getListArticles(Pageable page) {
    if (cacheManager.getCache("articleDto").get("all") != null) {
      LOG.info("cache");
      return ((Flux<ArticleDto>) cacheManager.getCache("articleDto").get("all").get())
          .collectList()
          .map(
              list ->
                  new Pagination<>(
                      list.stream()
                          .skip(page.getPageNumber() * page.getPageSize())
                          .limit(page.getPageSize())
                          .collect(Collectors.toList()),
                      page.getPageNumber(),
                      page.getPageSize(),
                      list.size()));
    }
    LOG.info("not cache");
    Flux<Article> flux = productorepository.findAll();
    Flux<ArticleDto> fluxDto = mapper.mapListToFlux(flux);
    cacheManager.getCache("articleDto").put("all", fluxDto);
    return fluxDto
        .cache(Duration.ofMinutes(CACHE_TIME))
        .collectList()
        .map(
            list ->
                new Pagination<>(
                    list.stream()
                        .skip(page.getPageNumber() * page.getPageSize())
                        .limit(page.getPageSize())
                        .collect(Collectors.toList()),
                    page.getPageNumber(),
                    page.getPageSize(),
                    list.size()));
  }

  private Either<ErrorDto, Mono<Pagination<ArticleDto>>> processArticleResponse(
      Mono<Pagination<ArticleDto>> entry) {
    return Try.of(() -> entry)
        .onFailure(Throwable::printStackTrace)
        .fold(exception -> Either.left(new ErrorDto()), Either::right);
  }

  /** clear cache at time: 5 mins */
  @Scheduled(fixedRate = CACHE_FIXED_RATE)
  public void clearCache() {
    cacheManager.getCache("articleDto").clear();
  }
}
