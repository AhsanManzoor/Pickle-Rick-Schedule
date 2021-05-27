/* ! Coded by Stefan */
drop database pickle_rick_db;
create database pickle_rick_db;

/* ! Coded by Stefan */
create table pickle_rick_db.user(
                                    id int NOT NULL AUTO_INCREMENT primary key,
                                    firstname varchar (30),
                                    lastname varchar (30),
                                    email varchar (50),
                                    weekly_schedule double,
                                    manager_id int,
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

/* ! Coded by Stefan & Clelia */
create table pickle_rick_db.login(
                                     user_id int primary key,
                                     password varchar (1000),
                                     foreign key(user_id) references pickle_rick_db.user(id)
);

/* ! Coded by Stefan&Clelia */
create table pickle_rick_db.work(
                                    id int NOT NULL AUTO_INCREMENT primary key,
                                    date date,
                                    start_at time,
                                    end_at time,
                                    worked_time double,
                                    scheduled_time double,
                                    time_difference double,
                                    user_id int,
                                    proceeded boolean,
                                    foreign key (user_id) references pickle_rick_db.user(id)
);
/* ! Coded by Stefan */
create table pickle_rick_db.working_Day(
                                     id int NOT NULL AUTO_INCREMENT primary key,
                                    date date,
                                    worked_time double,
                                    scheduled_time double,
                                    daily_difference double,
                                    user_id int,
                                    proceeded_week boolean,
                                    proceeded_month boolean,
                                    foreign key (user_id) references pickle_rick_db.user(id)


);
/* ! Coded by Stefan */
create table pickle_rick_db.working_Month(
 id int NOT NULL AUTO_INCREMENT primary key,
                                    start_Date date,
                                    end_Date date,
                                    worked_time double,
                                     scheduled_time double,
                                     monthly_difference double,
                                    user_id int,
                                    foreign key (user_id) references pickle_rick_db.user(id)


);
/* ! Coded by Stefan */
create table pickle_rick_db.working_Week(
 id int NOT NULL AUTO_INCREMENT primary key,
                                    start_Date date,
                                    worked_time double,
                                    scheduled_time double,
                                    weekly_difference double,
                                    user_id int,
                                    foreign key (user_id) references pickle_rick_db.user(id)

);
/* ! "Coded" by Stefan */
INSERT INTO pickle_rick_db.user VALUES (1,'Sven', 'Salzig', 'sven@gmail.com', 42, null );
INSERT INTO pickle_rick_db.user VALUES (2,'Eva', 'Svenson', 'eva@gmail.com', 42, 1);
INSERT INTO pickle_rick_db.user VALUES (3,'Lilly', 'Peterson', 'Lilly@gmail.com', 40, 2);

/* ! "Coded" by Stefan */
INSERT INTO pickle_rick_db.login VALUES (1, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');
INSERT INTO pickle_rick_db.login VALUES (2, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');
INSERT INTO pickle_rick_db.login VALUES (3, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');

/* ! Coded by Clelia */
insert into pickle_rick_db.role values(1,'ROLE_ADMIN');
insert into pickle_rick_db.role values(2,'ROLE_USER');

/* ! Coded by Clelia */
insert into pickle_rick_db.user_role values(1,2);
insert into pickle_rick_db.user_role values(2,1);
insert into pickle_rick_db.user_role values(3, 2);

/* ! "Coded" by Stefan & Clelia*/
DROP USER IF EXISTS 'pickle'@'localhost';
CREATE USER 'pickle'@'localhost' IDENTIFIED BY 'pickleRICKPW2021';
USE pickle_rick_db;
GRANT ALL PRIVILEGES ON pickle_rick_db.* TO 'pickle'@'localhost';

