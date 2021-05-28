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
INSERT INTO pickle_rick_db.user VALUES (1,'Bradley','Richards','bradley.richards@fhnw.ch',42,NULL );
INSERT INTO pickle_rick_db.user VALUES (2,'Lukas','Fey','lukas.frey@fhnw.ch',42,1);
INSERT INTO pickle_rick_db.user VALUES (3,'Clelia','Meneghin','clelia.meneghin@students.fhnw.ch',40,2);
INSERT INTO pickle_rick_db.user VALUES (4,'Stefan','Gajic','stefan.gajic@students.fhnw.ch',42,2);
INSERT INTO pickle_rick_db.user VALUES (5,'Ahsan','Manzoor','ahsan.manzoor@students.fhnw.ch',20,2);


/* ! "Coded" by Stefan */
INSERT INTO pickle_rick_db.login VALUES (1, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');
INSERT INTO pickle_rick_db.login VALUES (2, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');
INSERT INTO pickle_rick_db.login VALUES (3, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');
INSERT INTO pickle_rick_db.login VALUES (4, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');
INSERT INTO pickle_rick_db.login VALUES (5, '$2y$12$k7HxqGYsiHoY89A4gAXeqOLHodLJ/OrfVuJR/jy7XwSWlZ6vS.dJi');

/* ! Coded by Clelia */
insert into pickle_rick_db.role values(1,'ROLE_ADMIN');
insert into pickle_rick_db.role values(2,'ROLE_USER');

/* ! Coded by Clelia */
insert into pickle_rick_db.user_role values(1,2);
insert into pickle_rick_db.user_role values(2,1);
insert into pickle_rick_db.user_role values(3, 2);
insert into pickle_rick_db.user_role values(4, 2);
insert into pickle_rick_db.user_role values(5, 2);

/* ! Added by Stefan */
insert into pickle_rick_db.work values(1,'2021-05-28','09:15:00','09:30:00',0.25,NULL,NULL,3,1);
insert into pickle_rick_db.work values(2,'2021-05-28','10:00:00','16:00:00',4,NULL,NULL,5,1);
insert into pickle_rick_db.work values(3,'2021-05-28','18:00:00','20:45:00',2.75,NULL,NULL,5,1);
insert into pickle_rick_db.work values(4,'2021-05-28','09:45:20','09:45:36',0,NULL,NULL,2,1);
insert into pickle_rick_db.work values(5,'2021-05-28','09:00:00','10:00:00',1,NULL,NULL,3,1);
insert into pickle_rick_db.work values(6,'2021-04-05','10:00:00','14:00:00',4,NULL,NULL,3,1);
insert into pickle_rick_db.work values(7,'2021-05-28','11:00:00','16:00:00',5,NULL,NULL,4,1);
insert into pickle_rick_db.work values(8,'2021-05-28','18:00:00','20:00:00',2,NULL,NULL,4,1);
insert into pickle_rick_db.work values(9,'2021-05-25','08:00:00','12:00:00',4,NULL,NULL,1,1);
insert into pickle_rick_db.work values(10,'2021-05-25','13:00:00','17:00:00',4,NULL,NULL,1,1);
insert into pickle_rick_db.work values(11,'2021-05-24','08:00:00','14:00:00',5.75,NULL,NULL,2,1);

/* ! Added by Stefan */
insert into pickle_rick_db.working_Day values(1,'2021-05-28',1.25,8,-6.75,3,1,1);
insert into pickle_rick_db.working_Day values(2,'2021-05-28',7,8.4,-1.4,4,1,1);
insert into pickle_rick_db.working_Day values(3,'2021-04-05',4,8,-4,3,1,1);
insert into pickle_rick_db.working_Day values(4,'2021-05-28',6.75,4,2.75,5,1,1);
insert into pickle_rick_db.working_Day values(5,'2021-05-25',8,8.4,-0.4,1,1,1);
insert into pickle_rick_db.working_Day values(6,'2021-05-24',5.75,8.4,-2.65,2,1,1);

/* ! Added by Stefan */
insert into pickle_rick_db.working_Month values(1,'2021-05-01','2021-05-31',1.25,160,-158.75,3);
insert into pickle_rick_db.working_Month values(2,'2021-05-01','2021-05-31',7,168,-161,4);
insert into pickle_rick_db.working_Month values(3,'2021-04-01','2021-04-30',4,160,-156,3);
insert into pickle_rick_db.working_Month values(4,'2021-05-01','2021-05-31',6.75,80,-73.25,5);
insert into pickle_rick_db.working_Month values(5,'2021-05-01','2021-05-31',8,168,-160,1);
insert into pickle_rick_db.working_Month values(6,'2021-05-01','2021-05-31',5.75,168,-162.25,2);

/* ! Added by Stefan */
insert into pickle_rick_db.working_Week values(1,'2021-05-24',1.25,40,-38.75,3);
insert into pickle_rick_db.working_Week values(2,'2021-05-24',7,42,-35,4);
insert into pickle_rick_db.working_Week values(3,'2021-04-05',4,40,-36,3);
insert into pickle_rick_db.working_Week values(4,'2021-05-24',6.75,20,-13.25,5);
insert into pickle_rick_db.working_Week values(5,'2021-05-24',8,42,-34,1);
insert into pickle_rick_db.working_Week values(6,'2021-05-24',5.75,42,-36.25,2);

/* ! "Coded" by Stefan & Clelia*/
DROP USER IF EXISTS 'pickle'@'localhost';
CREATE USER 'pickle'@'localhost' IDENTIFIED BY 'pickleRICKPW2021';
USE pickle_rick_db;
GRANT ALL PRIVILEGES ON pickle_rick_db.* TO 'pickle'@'localhost';

