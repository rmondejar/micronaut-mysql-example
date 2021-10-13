package mn.data.mysql.mappers;

import jakarta.inject.Singleton;

import mn.data.mysql.domain.Author;
import mn.data.mysql.dtos.AuthorDto;

@Singleton
public class AuthorMapper {

    public Author toEntity(AuthorDto authorDto) {
       Author author = new Author(authorDto.getName(), authorDto.getBirthYear());
        return author;
    }

    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getName(), author.getBirthDate());
    }
}
