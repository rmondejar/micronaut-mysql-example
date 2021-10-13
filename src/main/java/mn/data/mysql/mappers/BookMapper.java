package mn.data.mysql.mappers;

import jakarta.inject.Singleton;

import mn.data.mysql.domain.Author;
import mn.data.mysql.domain.Book;
import mn.data.mysql.dtos.AuthorDto;
import mn.data.mysql.dtos.BookDto;

@Singleton
public class BookMapper {

    final private AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public BookDto toDto(Book book) {
        AuthorDto authorDto = authorMapper.toDto(book.getAuthor());
        return new BookDto(book.getTitle(), book.getPubDate(), authorDto.getName());
    }
}
