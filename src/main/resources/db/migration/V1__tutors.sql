create table if not exists tutors(
id bigint auto_increment,
tutor_name varchar(255) unique not null,
constraint pk_tutors primary key(id)
);