
create table readers
(
    id    serial
        primary key,
    name  varchar(20) not null,
    mail  varchar(20) not null,
    phone integer     not null
);
