package mn.data.mysql.test.services

import jakarta.inject.Inject
import jakarta.inject.Singleton
import mn.data.mysql.domain.Author
import mn.data.mysql.domain.Book
import mn.data.mysql.test.TestHelper

import mn.data.mysql.repositories.AuthorRepository
import mn.data.mysql.repositories.BookRepository

@Singleton
class TestHelperService {

    @Inject AuthorRepository authorRepository
    @Inject BookRepository bookRepository

    Author createAuthor(String name = "no name") {
        Author author = TestHelper.createAuthor(name)
        authorRepository.save(author)
        return author
    }

    Set<Book> createBooks(List<String> titles, Author author) {
        Set<Book> books = new LinkedHashSet(titles.collect { String title -> createBook(title, author) } )
        authorRepository.update(author)
        return books
    }

    Book createBook(String title, Author author) {
        Book book = TestHelper.createBook(title, author)
        bookRepository.save(book)
        return book
    }
    
    void cleanDB() {
        authorRepository.deleteAll()
        bookRepository.deleteAll()
    }
}
