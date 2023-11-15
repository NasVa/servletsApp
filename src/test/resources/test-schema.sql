create table if not exists readers
(
    id    serial
        primary key,
    name  varchar(20) not null,
    mail  varchar(20) not null,
    phone integer     not null
);

create table if not exists authors
(
    id   serial
        primary key,
    name varchar(20) not null
);

create table if not exists books
(
    id               serial
        primary key,
    name             varchar(20) not null,
    yearOfPublishing integer,
    locationOfPublishing varchar(20),
    pages integer,
    owner_id integer REFERENCES readers(id)
);

create table if not exists authors_books
(
    author_id integer REFERENCES authors(id),
    book_id integer REFERENCES books(id),
    PRIMARY KEY (author_id, book_id)
)
