create table if not exists subjects (
    id bigint not null auto_increment,
    subject_name varchar(100),
    constraint pk_subjects primary key (id)
);