package mn.data.mysql.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME", nullable = false, unique = true)
    @Size(max = 100)
    private String name;

    @Column(name="BIRTH_YEAR", nullable = false)
    private Integer birthYear;

    public Author() {}

    public Author(String name, Integer birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}

