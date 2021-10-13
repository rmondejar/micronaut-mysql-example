package mn.data.mysql.controllers;

import java.util.List;
import java.util.Optional;

import io.micronaut.http.annotation.*;
import io.netty.handler.codec.http.QueryStringDecoder;
import reactor.core.publisher.Mono;

import io.micronaut.http.HttpResponse;

import mn.data.mysql.dtos.AuthorDto;
import mn.data.mysql.services.AuthorService;

@Controller("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Get("/all")
    public Mono<List<AuthorDto>> getAllAuthors() {
        return Mono.just(authorService.findAll());
    }

    @Get
    public Mono<HttpResponse<AuthorDto>> getAuthor(@QueryValue String name) {
        return Mono.just(
                authorService.findAuthor(name).map(HttpResponse::ok)
                        .orElse(HttpResponse.notFound())
        );
    }

    @Post
    public Mono<HttpResponse<AuthorDto>> createAuthor(@Body AuthorDto authorDto) {
        Optional<AuthorDto> existingAuthor = authorService.findAuthor(authorDto.getName());

        return Mono.just(existingAuthor.isPresent() ? HttpResponse.badRequest() :
                        HttpResponse.created(authorService.createAuthor(authorDto))
        );
    }
}
