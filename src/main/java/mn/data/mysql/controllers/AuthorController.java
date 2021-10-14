package mn.data.mysql.controllers;

import java.util.List;
import javax.validation.Valid;

import io.micronaut.http.annotation.*;
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
    public Mono<HttpResponse<AuthorDto>> createAuthor(@Body @Valid AuthorDto authorDto) {
        return Mono.just(
                authorService.findAuthor(authorDto.getName()).isPresent() ?
                        HttpResponse.badRequest()
                        :
                        HttpResponse.created(authorService.createAuthor(authorDto))
        );
    }
}
