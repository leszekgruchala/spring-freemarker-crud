drop table people if exists;
create table people (id numeric IDENTITY, name varchar(100) not null, birthdate date not null, email varchar(50) not null);
