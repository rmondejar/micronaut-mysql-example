package mn.data.mysql.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.inject.Singleton;

import mn.data.mysql.domain.Author;
import mn.data.mysql.dtos.AuthorDto;
import mn.data.mysql.mappers.AuthorMapper;
import mn.data.mysql.repositories.AuthorRepository;

@Singleton
public class AuthorService {

    private final AuthorRepository authorsRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorsRepository,AuthorMapper authorMapper) {
        this.authorsRepository = authorsRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDto> findAll() {
        List<AuthorDto> authorDtos = new ArrayList<>();
        authorsRepository.findAll().forEach(author -> authorDtos.add(authorMapper.toDto(author)));
        return authorDtos;
    }

    public Optional<AuthorDto> findAuthor(String authorName) {
        return authorsRepository.findByName(authorName).map(authorMapper::toDto);
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = authorsRepository.save(authorMapper.toEntity(authorDto));
        return authorMapper.toDto(author);
    }
}
