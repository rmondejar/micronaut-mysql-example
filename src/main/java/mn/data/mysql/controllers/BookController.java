package mn.data.mysql.controllers;

import java.util.List;

import reactor.core.publisher.Mono;

import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpResponse;

import mn.data.mysql.dtos.BookDto;
import mn.data.mysql.services.AuthorService;
import mn.data.mysql.services.BookService;


import static io.micronaut.http.HttpResponseFactory.INSTANCE;
import static io.micronaut.http.HttpStatus.FORBIDDEN;

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
    public Mono<HttpResponse<BookDto>> postBook(@Body BookDto bookDto) {
        return Mono.just(
                authorService.findAuthor(bookDto.getAuthor()).map(author ->
                        bookService.create(bookDto)
                                .map(book -> HttpResponse.created(book))
                                .orElse(INSTANCE.status(FORBIDDEN))
                )
                        .orElse(HttpResponse.notFound())
        );
    }
}
