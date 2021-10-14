package mn.data.mysql.controllers;

import java.util.List;
import javax.validation.Valid;

import reactor.core.publisher.Mono;

import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpResponse;

import mn.data.mysql.dtos.BookDto;
import mn.data.mysql.services.AuthorService;
import mn.data.mysql.services.BookService;

@Controller("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Get("/all")
    public Mono<List<BookDto>> getAllBooks() {
        return Mono.just(bookService.findAll());
    }

    @Get
    public Mono<HttpResponse<List<BookDto>>> getBooksByAuthor(@QueryValue String author) {
        return Mono.just(
                authorService.findAuthor(author).map(authorDto ->
                            HttpResponse.ok(bookService.findAllByAuthorName(authorDto.getName()))
                        )
                        .orElse(HttpResponse.notFound())
        );
    }

    @Post
    public Mono<HttpResponse> postBook(@Body @Valid BookDto bookDto) {
        if (authorService.findAuthor(bookDto.getAuthor()).isEmpty() ||
            bookService.findByTitle(bookDto.getTitle()).isPresent()) {
            return Mono.just(HttpResponse.badRequest());
        }
        return Mono.just(
                bookService.create(bookDto)
                    .map(book -> HttpResponse.created(book))
                    .orElseGet(HttpResponse::badRequest)
        );
    }
}
