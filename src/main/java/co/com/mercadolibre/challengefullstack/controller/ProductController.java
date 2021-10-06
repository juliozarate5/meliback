package co.com.mercadolibre.challengefullstack.controller;

import co.com.mercadolibre.challengefullstack.model.dto.ArticleDto;
import co.com.mercadolibre.challengefullstack.model.dto.ErrorDto;
import co.com.mercadolibre.challengefullstack.service.IProductService;
import co.com.mercadolibre.challengefullstack.utils.Messages;
import co.com.mercadolibre.challengefullstack.utils.Pagination;
import co.com.mercadolibre.challengefullstack.utils.Utils;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/** Rest Controller for Articles */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(
    origins = "*",
    methods = {RequestMethod.GET})
public class ProductController {

  private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

  @Autowired private IProductService productService;

  /**
   * get products (articles) per page & size
   *
   * @param page
   * @param size
   * @return
   */
  @GetMapping(value = "/products", produces = "application/json")
  public ResponseEntity<Object> getEntitiesPage(
      @RequestParam(name = "page", defaultValue = Pagination.FIRST_PAGE_NUM) int page,
      @RequestParam(name = "size", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int size) {
    Either<ErrorDto, Mono<Pagination<ArticleDto>>> response =
        productService.listArticles(PageRequest.of(page, size));
    return processResponse(response);
  }

  /**
   * Process Response to ResponseEntity
   *
   * @param response
   * @param <T>
   * @return
   */
  private <T> ResponseEntity<Object> processResponse(Either<ErrorDto, T> response) {
    Either<ErrorDto, T> processedResponse =
        Utils.setBusinessInfo(response, Messages.ERROR_QUERYING_TRANSACTIONS);
    return processedResponse.fold(
        exception ->
            ResponseEntity.status(Utils.getStatus(exception)).body(Utils.wrapError(exception)),
        success -> ResponseEntity.status(HttpStatus.OK).body(success));
  }
}
