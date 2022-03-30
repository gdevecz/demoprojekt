create table if not exists marks(
id bigint auto_increment,
student_id bigint not null,
subject_id bigint not null,
mark_type varchar(255) not null,
constraint pk_marks primary key(id),
constraint fk_student_id_in_marks foreign key (student_id) references students(id) on delete cascade,
constraint fk_subject_id_in_marks foreign key (subject_id) references subjects(id)
)