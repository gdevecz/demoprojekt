insert into tutors(tutor_name) values
('Kovács István'), ('Kovács Áron'), ('Kovács Krisztina'), ('Kovács Éva'), ('Kovács Ildikó');

insert into subjects(subject_name, tutor_id) values
('Matematika',1), ('Fizika',1), ('Irodalom',2), ('Nyelvtan',2), ('Történelem',5), ('Informatika',3), ('Testnevelés',4), ('Földrajz',5);

insert into students(student_name) values
('Pintér Dezső'), ('Pintér Miklós'), ('Pintér attila'), ('Pintér Elemér');

insert into marks(mark_type, student_id, subject_id, tutor_id) values
('A',1,1,1), ('D',1,3,2), ('F',2,1,1), ('A',3,2,1), ('B',3,5,5);