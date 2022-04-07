create table author (
    id_author bigint auto_increment not null primary key,
    name varchar(255) not null
);

create table genre (
    id_genre bigint auto_increment not null primary key,
    name varchar(255) not null
);

create table book (
    id_book bigint auto_increment not null primary key,
    title varchar(255) not null,
    id_author bigint not null,
    id_genre bigint not null);

alter table book add foreign key (id_author) references author(id_author);
create index ix_book_id_author on book(id_author);

alter table book add foreign key (id_genre) references genre(id_genre);
create index ix_book_id_genre on book(id_genre);

create table comment (
    id_comment bigint auto_increment not null primary key,
    id_book bigint not null,
    comment_text varchar(255) not null
);

alter table comment add foreign key (id_book) references book(id_book) on delete cascade;
create index ix_comment_id_book on comment(id_book);