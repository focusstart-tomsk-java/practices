create schema library;

create table author
(
    id          bigserial     not null,
    name        varchar(256)  not null,
    description varchar(4096) not null
);

alter table author
    add constraint author_pk primary key (id);

create table book
(
    id          bigserial     not null,
    name        varchar(256)  not null,
    description varchar(4096) not null,
    isbn        varchar(64)   not null,
    author_id   bigint        not null
);

alter table book
    add constraint book_pk primary key (id);

alter table book
    add constraint book_author_id_fk foreign key (author_id) references author (id);
