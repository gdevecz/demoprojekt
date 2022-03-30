create table if not exists subjects(
id bigint auto_increment,
subject_name varchar(100) unique not null,
tutor_id bigint,
constraint pk_subjects primary key(id),
constraint fk_tutor_id_in_subjects foreign key (tutor_id) references tutors(id)
);