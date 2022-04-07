insert into author(id_author, name) values(1, 'Author1');
insert into author(id_author, name) values(2, 'Author2');
insert into author(id_author, name) values(3, 'Author3');

insert into genre(id_genre, name) values(1, 'Genre1');
insert into genre(id_genre, name) values(2, 'Genre2');

insert into book(title, id_author, id_genre) values ('Title1', 1, 1);
insert into book(title, id_author, id_genre) values ('Title2', 2, 2);

insert into comment(id_book, comment_text) values (1, 'Comment11');
insert into comment(id_book, comment_text) values (1, 'Comment12');
insert into comment(id_book, comment_text) values (2, 'Comment21');

