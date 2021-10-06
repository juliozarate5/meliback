package co.com.mercadolibre.challengefullstack.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Value;

@Value
public class Pagination<T> {

  public static final String FIRST_PAGE_NUM = "0";
  public static final String DEFAULT_PAGE_SIZE = "10";

  List<T> data;
  int pageNumber;
  int pageSize;
  long totalElements;

  @JsonProperty
  public long totalPages() {
    return pageSize > 0 ? (totalElements - 1) / pageSize + 1 : 0;
  }

  @JsonProperty
  public boolean first() {
    return pageNumber == Integer.parseInt(FIRST_PAGE_NUM);
  }

  @JsonProperty
  public boolean last() {
    return (pageNumber + 1) * pageSize >= totalElements;
  }
}
