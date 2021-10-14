-- author definition
-- Drop table
-- DROP TABLE author;

CREATE TABLE author
(
    ID         BIGINT       NOT NULL AUTO_INCREMENT,
    NAME       VARCHAR(100) NOT NULL UNIQUE,
    BIRTH_YEAR INT          NOT NULL,
    CONSTRAINT pk_author PRIMARY KEY (ID)
);

-- book definition
-- Drop table
-- DROP TABLE book;

CREATE TABLE book
(
    ID       BIGINT       NOT NULL AUTO_INCREMENT,
    TITLE    VARCHAR(150) NULL,
    PUB_DATE datetime     NULL,
    AUTHOR   BIGINT       NULL,
    CONSTRAINT pk_book PRIMARY KEY (ID)
);

ALTER TABLE book
    ADD CONSTRAINT uc_book_title UNIQUE (TITLE);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_AUTHOR FOREIGN KEY (AUTHOR) REFERENCES author (ID);