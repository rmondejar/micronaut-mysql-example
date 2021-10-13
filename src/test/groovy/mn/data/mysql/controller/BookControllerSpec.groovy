package mn.data.mysql.controller

import jakarta.inject.Inject

import spock.lang.Shared
import spock.lang.Specification

import io.micronaut.core.type.Argument
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer

import mn.data.mysql.domain.Author
import mn.data.mysql.domain.Book
import mn.data.mysql.test.TestHelper
import mn.data.mysql.test.services.TestHelperService
import mn.data.mysql.dtos.BookDto

@MicronautTest(transactional = false)
class BookControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    @Client("/books")
    HttpClient client

    @Inject @Shared TestHelperService testHelperService
    
    void setupSpec() {
        testHelperService.cleanDB()
    }

    void 'Retrieve all the books returns the list of books for the authors'() {
        given: 'one author belonging to the books'
        Author firstAuthor = testHelperService.createAuthor("author1")
        Set<Book> books1 = testHelperService.createBooks(['T1', 'A1', 'G1'], firstAuthor)

        and: 'one author belonging to another set of books'
        Author secondAuthor = testHelperService.createAuthor("author2")
        Set<Book> books2 = testHelperService.createBooks(['T2', 'A2', 'G2'], secondAuthor)

        when:
        String requestUri = UriBuilder.of("/all")
        HttpRequest request = HttpRequest.GET(requestUri)
        HttpResponse<List<BookDto>> response =
            TestHelper.requestWithoutException(client, request, Argument.of(List<BookDto>)) as HttpResponse<List<BookDto>>
        List<BookDto> booksResp = response.body()

        then: "all the books for corresponding authors are returned"
        response.status == HttpStatus.OK
        booksResp
        booksResp.size() == books1.size() + books2.size()
    }

    void 'Create a new book successfully'() {
        given: 'one author belonging to the books'
        Author firstAuthor = testHelperService.createAuthor("author3")
        BookDto firstBook = TestHelper.createBookDto("B1", firstAuthor.name)

        when:
        String requestUri = UriBuilder.of("/")
        HttpRequest request = HttpRequest.POST(requestUri, firstBook)
        HttpResponse<List<String>> response = TestHelper.requestWithoutException(client, request, Argument.of(BookDto))
        BookDto bookResp = response.body()

        then: "the book is created and returned"
        response.status == HttpStatus.CREATED
        bookResp
        bookResp.title == firstBook.title
        bookResp.pubDate == firstBook.pubDate
        bookResp.author == firstBook.author
    }
}
