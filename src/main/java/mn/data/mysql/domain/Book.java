package mn.data.mysql.domain;

import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="TITLE", unique = true)
    @Size(max = 150)
    private String title;

    @Column(name="PUB_DATE")
    private Instant pubDate;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "AUTHOR")
    private Author author;

    public Book() {}

    public Book(String title, Instant pubDate, Author author) {
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
    }

    public Long getId() { return id; }

    public String getTitle() {
        return title;
    }

    public Instant getPubDate() {
        return pubDate;
    }

    public Author getAuthor() {
        return author;
    }
}