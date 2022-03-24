create table students_marks(
id bigint auto_increment,
student_id BIGINT,
subject_id BIGINT,
mark varchar(20),
constraint pk_students_marks_id primary key(id),
constraint fk_student_id foreign key (student_id) references students(id),
constraint fk_subject__id foreign key (subject_id) references subjects(id)
);