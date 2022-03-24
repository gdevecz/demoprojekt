create table taught_subjects(
id bigint auto_increment,
teacher_id BIGINT,
subject_id BIGINT,
constraint pk_subjects primary key(id),
Constraint fk_teacher_id foreign key (teacher_id) references teachers(id),
Constraint fk_subject_id foreign key (subject_id) references subjects(id)
);