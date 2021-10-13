package mn.data.mysql.dtos;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class BookDto {

    @NotBlank
    private String title;
    @NotNull
    private Instant pubDate;
    @NotNull
    private String author;

    public BookDto() {}

    public BookDto(String title, Instant pubDate, String author) {
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public Instant getPubDate() {
        return pubDate;
    }

    public String getAuthor() {
        return author;
    }
}
