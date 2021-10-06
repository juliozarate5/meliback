package co.com.mercadolibre.challengefullstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  private String id;
  private Boolean featured;
  private String title;
  private String url;

  @JsonProperty("imageUrl")
  private String image;

  private String newsSite;
  private String summary;
  private LocalDateTime publishedAt;
  private List<Launch> launches;
  private List<Event> events;
}
