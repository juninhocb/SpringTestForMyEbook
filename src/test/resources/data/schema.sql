drop table if exists person;
create table person (
    id integer not null auto_increment,
    name varchar(255),
    height integer,
    weight integer,
    primary key (id)
) engine=InnoDB;


