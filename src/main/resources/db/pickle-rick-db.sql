/* @author Clelia Meneghin*/
CREATE LOGIN pickle WITH PASSWORD = 'pickle-rick2021!';
GO;

CREATE USER pickle-rick FOR LOGIN pickle-rick;
GO
/*@author Clelia Meneghin*/
GRANT CREATE, DELETE, INSERT, SELECT, UPDATE ON pickle_rick_db TO 'pickle-rick'@'localhost';

/* ! Coded by Stefan & Clelia */
drop database IF EXISTS pickle_rick_db;
create database pickle_rick_db;



/* ! Coded by Stefan */
create table pickle_rick_db.User(
id int auto_increment primary key,
 firstname varchar (30),
 lastname varchar (30),
 email varchar (50),
 weekly_schedule decimal,
 manager_id int,
 is_admin boolean,
 foreign key (manager_id) references pickle_rick_db.User(id)
);

/* ! Coded by Stefan */
create table pickle_rick_db.Project(
id int primary key,
title varchar(100),
description varchar(600),
created_by int,
foreign key (created_by) references pickle_rick_db.User(id)
);

/* ! Coded by Stefan */
create table pickle_rick_db.Login(
user_id int primary key,
password varchar (1000)
);

/* ! Coded by Stefan */
create table pickle_rick_db.EmployeeProject(
user_id int,
project_id int,
foreign key (user_id) references pickle_rick_db.User(id),
foreign key (project_id) references pickle_rick_db.Project(id)
);

/* ! Coded by Stefan */
create table pickle_rick_db.Work(
id int primary key,
date date,
start_at time,
end_at time,
user_id int,
project_id int,
foreign key (user_id) references pickle_rick_db.User(id),
foreign key (project_id) references pickle_rick_db.Project(id)
);
