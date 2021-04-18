/* ! Coded by Stefan */
drop database pickle_rick_db;
create database pickle_rick_db;

/* ! Coded by Stefan */
create table pickle_rick_db.user(
    user_id int auto_increment primary key,
    firstname varchar (30),
    lastname varchar (30),
    email varchar (50),
    weekly_schedule decimal,
    manager_id int,
    foreign key (manager_id) references pickle_rick_db.User(id)
);

/* ! Coded by Stefan & Clelia*/
create table pickle_rick_db.project(
   id int auto_increment primary key,
   title varchar(100),
   description varchar(600),
   created_by int,
   foreign key (created_by) references pickle_rick_db.user(id)
);

/* ! Coded by Stefan & Clelia */
create table pickle_rick_db.login(
  user_id int primary key,
  password varchar (1000)
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
    id int auto_increment primary key,
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
INSERT INTO pickle_rick_db.login VALUES (1, 'password');
INSERT INTO pickle_rick_db.login VALUES (2, 'password');
INSERT INTO pickle_rick_db.login VALUES (3, 'password');

/* ! "Coded" by Ahsan */
create table pickle_rick_db.roles(
  role_id int(11) Not Null auto_increment,
  role_name varchar(45) Not Null,
  primary key (id)
);

/* ! "Coded" by Ahsan */
CREATE TABLE pickle_rick_db.users_roles (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  KEY user_fk_idx (user_id),
  KEY role_fk_idx (role_id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
);

/* ! "Coded" by Ahsan */
INSERT INTO pickle_rick_db.roles (name) VALUES (Employee);
INSERT INTO pickle_rick_db.roles (name) VALUES (Manager);
INSERT INTO pickle_rick_db.roles (name) VALUES (ADMIN,CREATOR);

/* ! "Coded" by Stefan */
insert into pickle_rick_db.project values (1, 'Peace', 'Enhancing peace on the world by fighting poverty',1);
insert into pickle_rick_db.project values (2, 'Social justice', 'Enhancing social justice',2);
insert into pickle_rick_db.project values (3, 'Saving the planet', 'Saving the planet, protecting flora and fauna world wide',3);

/* ! "Coded" by Stefan */
insert into pickle_rick_db.employeeproject values(1, 1);
insert into pickle_rick_db.employeeproject values(2, 2);
insert into pickle_rick_db.employeeproject values(2, 2);


/* ! "Coded" by Stefan & Clelia*/
DROP USER IF EXISTS 'pickle'@'localhost';
CREATE USER 'pickle'@'localhost' IDENTIFIED BY 'pickleRICKPW2021';
USE pickle_rick_db;
GRANT ALL PRIVILEGES ON pickle_rick_db.* TO 'pickle'@'localhost';
