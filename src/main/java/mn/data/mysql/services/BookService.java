package mn.data.mysql.services;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import jakarta.inject.Singleton;

import mn.data.mysql.domain.Book;
import mn.data.mysql.dtos.BookDto;
import mn.data.mysql.mappers.BookMapper;
import mn.data.mysql.repositories.BookRepository;
import mn.data.mysql.repositories.AuthorRepository;

@Singleton
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository booksRepository, BookMapper bookMapper, AuthorRepository authorRepository) {
        this.bookRepository = booksRepository;
        this.bookMapper = bookMapper;
        this.authorRepository = authorRepository;
    }

    public List<BookDto> findAll() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookRepository.findAll().forEach(book -> bookDtos.add(bookMapper.toDto(book)));
        return bookDtos;
    }

    public Optional<BookDto> findByTitle(String title) {
        return bookRepository.findByTitle(title).map(bookMapper::toDto);
    }

    public List<BookDto> findAllByAuthorName(String authorName) {
        List<BookDto> bookDtos = new ArrayList<>();

        authorRepository.findByName(authorName).ifPresent(author ->
                bookRepository.findAllByAuthor(author).forEach(book ->
                        bookDtos.add(bookMapper.toDto(book)))
        );
        return bookDtos;
    }

    public Optional<BookDto> create(BookDto bookDto) {
         return authorRepository.findByName(bookDto.getAuthor()).map(author ->
                                bookRepository.save(new Book(bookDto.getTitle(), bookDto.getPubDate(), author)))
                                .map(bookMapper::toDto);
    }
}
