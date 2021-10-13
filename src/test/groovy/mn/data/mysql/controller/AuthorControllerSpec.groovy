package mn.data.mysql.controller

import io.micronaut.http.uri.UriBuilder
import jakarta.inject.Inject

import spock.lang.Shared
import spock.lang.Specification

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.core.type.Argument
import io.micronaut.test.extensions.spock.annotation.MicronautTest

import mn.data.mysql.dtos.AuthorDto
import mn.data.mysql.test.TestHelper
import mn.data.mysql.test.services.TestHelperService

@MicronautTest(transactional = false)
class AuthorControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    @Client("/authors")
    HttpClient client

    @Inject @Shared TestHelperService testHelperService

    void setupSpec() {
        testHelperService.cleanDB()
    }

    def "Create author correctly"() {

        given: 'Author data'
        AuthorDto authorDto = TestHelper.createAuthorDto()

        when: 'Send the request with the proper data'
        String requestUri = UriBuilder.of("/")
        HttpRequest request = HttpRequest.POST(requestUri, authorDto)
        HttpResponse<AuthorDto> rsp = TestHelper.requestWithoutException(client, request, Argument.of(AuthorDto))

        then: 'The author has been created'
        rsp.status == HttpStatus.CREATED
        rsp.body().name == authorDto.name
        rsp.body().birthYear == authorDto.birthYear
    }

    def "Create again same author returns BAD REQUEST"() {

        given: 'Author data'
        AuthorDto authorDto = TestHelper.createAuthorDto()

        when: 'Creation endpoint is called with the proper data'
        String requestUri = UriBuilder.of("/")
        HttpRequest request = HttpRequest.POST(requestUri, authorDto)
        HttpResponse<AuthorDto> rsp = TestHelper.requestWithoutException(client, request, Argument.of(AuthorDto))

        then: 'The author has been created'
        rsp.status == HttpStatus.BAD_REQUEST
    }

    def "Retrieve the new author correctly"() {

        given: 'Author data'
        AuthorDto authorDto = TestHelper.createAuthorDto()

        when: 'Retrieve endpoint is called with valid name'
        String requestUri = UriBuilder.of("/")
                .queryParam("name", authorDto.getName() )
                .build()
        HttpRequest request = HttpRequest.GET(requestUri)
        HttpResponse<AuthorDto> rsp = TestHelper.requestWithoutException(client, request, Argument.of(AuthorDto))

        then: 'The author has been returned'
        rsp.status == HttpStatus.OK
        rsp.body().name == authorDto.name
        rsp.body().birthYear == authorDto.birthYear
    }
}
