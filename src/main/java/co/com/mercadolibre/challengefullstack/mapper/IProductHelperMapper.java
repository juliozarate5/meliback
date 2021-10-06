package co.com.mercadolibre.challengefullstack.mapper;

import co.com.mercadolibre.challengefullstack.model.Article;
import co.com.mercadolibre.challengefullstack.model.dto.ArticleDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;

/** No necesary */
@Mapper(componentModel = "spring")
public interface IProductHelperMapper {
  IProductHelperMapper INSTANCE = Mappers.getMapper(IProductHelperMapper.class);

  ArticleDto articleToDto(Article article);

  List<ArticleDto> mapListToDto(List<Article> articles);

  public default Flux<ArticleDto> mapListToFlux(Flux<Article> articleFlux) {
    return articleFlux.map(
        a -> {
          return articleToDto(a);
        });
  }
}
