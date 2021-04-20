/* ! Coded by Stefan */
drop database pickle_rick_db;
create database pickle_rick_db;

/* ! Coded by Stefan */
create table pickle_rick_db.user(
                                    id int NOT NULL AUTO_INCREMENT primary key,
                                    firstname varchar (30),
                                    lastname varchar (30),
                                    email varchar (50),
                                    weekly_schedule decimal,
                                    manager_id int,
                                    is_admin boolean,
                                    foreign key (manager_id) references pickle_rick_db.User(id)
);

/* ! Coded by Clelia*/
create table pickle_rick_db.role(
                                    id int NOT NULL AUTO_INCREMENT primary key,
                                    name VARCHAR(50)
);

/* ! Coded by Clelia*/
create table pickle_rick_db.user_role(
                                         user_id int NOT NULL,
                                         role_id int NOT NULL,
                                         foreign key(user_id) references pickle_rick_db.user(id),
                                         foreign key(role_id) references pickle_rick_db.role(id)
);

/* ! Coded by Stefan & Clelia*/
create table pickle_rick_db.project(
                                       id int NOT NULL AUTO_INCREMENT primary key,
                                       title varchar(100),
                                       description varchar(600),
                                       created_by int,
                                       foreign key (created_by) references pickle_rick_db.user(id)
);

/* ! Coded by Stefan & Clelia */
create table pickle_rick_db.login(
                                     user_id int primary key,
                                     password varchar (1000),
                                     foreign key(user_id) references pickle_rick_db.user(id)
);

/* ! Coded by Stefan */
create table pickle_rick_db.employeeproject(
                                               user_id int,
                                               project_id int,
                                               foreign key (user_id) references pickle_rick_db.user(id),
                                               foreign key (project_id) references pickle_rick_db.project(id)
);

/* ! Coded by Stefan&Clelia */
create table pickle_rick_db.work(
                                    id int NOT NULL AUTO_INCREMENT primary key,
                                    date date,
                                    start_at time,
                                    end_at time,
                                    user_id int,
                                    project_id int,
                                    foreign key (user_id) references pickle_rick_db.user(id),
                                    foreign key (project_id) references pickle_rick_db.project(id)
);

/* ! "Coded" by Stefan */
INSERT INTO pickle_rick_db.user VALUES (1,'Sven', 'Salzig', 'sven@gmail.com', 42, 1, false);
INSERT INTO pickle_rick_db.user VALUES (2,'Eva', 'Svenson', 'eva@gmail.com', 42, 1, true);
INSERT INTO pickle_rick_db.user VALUES (3,'Lilly', 'Peterson', 'Lilly@gmail.com', 40, 2, false);

/* ! "Coded" by Stefan */
INSERT INTO pickle_rick_db.login VALUES (1, '$2y$12$ewXehiKAQOKp.oXBLe1iluvexuU8yrTLSJPXY/QznK4IYdlTR8HUG ');
INSERT INTO pickle_rick_db.login VALUES (2, 'password');
INSERT INTO pickle_rick_db.login VALUES (3, 'password');

/* ! "Coded" by Stefan */
insert into pickle_rick_db.project values (1,'Peace', 'Enhancing peace on the world by fighting poverty',1);
insert into pickle_rick_db.project values (2,'Social justice', 'Enhancing social justice',2);
insert into pickle_rick_db.project values (3,'Saving the planet', 'Saving the planet, protecting flora and fauna world wide',3);

/* ! "Coded" by Stefan */
insert into pickle_rick_db.employeeproject values(1, 1);
insert into pickle_rick_db.employeeproject values(2, 2);
insert into pickle_rick_db.employeeproject values(2, 2);

/* ! Coded by Clelia */
insert into pickle_rick_db.role values(1,'ADMIN');
insert into pickle_rick_db.role values(2,'USER');

/* ! Coded by Clelia */
insert into pickle_rick_db.user_role values(1,2);
insert into pickle_rick_db.user_role values(2,1);
insert into pickle_rick_db.user_role values(3, 2);

/* ! Coded by Clelia */
insert into pickle_rick_db.user_role values(1,2);

/* ! "Coded" by Stefan & Clelia*/
DROP USER IF EXISTS 'pickle'@'localhost';
CREATE USER 'pickle'@'localhost' IDENTIFIED BY 'pickleRICKPW2021';
USE pickle_rick_db;
GRANT ALL PRIVILEGES ON pickle_rick_db.* TO 'pickle'@'localhost';
