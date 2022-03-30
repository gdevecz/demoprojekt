create table if not exists students(
id bigint auto_increment,
student_name varchar(255) unique not null,
constraint pk_students primary key(id)
);