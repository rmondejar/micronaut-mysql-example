package mn.data.mysql.dtos;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class AuthorDto {

    @NotBlank
    private String name;

    @NotBlank
    private Integer birthYear;

    public AuthorDto() {}

    public AuthorDto(String name, Integer birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }
}
