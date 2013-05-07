drop table people if exists;
create table people (id numeric IDENTITY, name varchar(100 CHARACTERS) not null, birthdate date not null, email varchar(100 CHARACTERS) not null,
    hash VARCHAR(64 CHARACTERS) NOT NULL);
ALTER TABLE people ADD CONSTRAINT people_hash_unique UNIQUE (hash);