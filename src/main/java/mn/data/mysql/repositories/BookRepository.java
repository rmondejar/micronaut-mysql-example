package mn.data.mysql.repositories;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import mn.data.mysql.domain.Author;
import mn.data.mysql.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAllByAuthor(Author author);
}

