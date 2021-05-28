Initial Readme
<!--Initialized by Clelia-->
# preinstalled
You need to have MYSQL installed and you need to have a running Java 8 on your computer

Download the project code or you could just
download the /src/main/resources/db/pickle-rick-db.sql
and the /src/main/resources/rest-service-0.0.1-SNAPSHOT.jar localy

## Install DB
go into your project folder.
When you are in the folder pickle_rick_schedule/ then run
```mysql -u root -p pickle_rick_db < src/main/resources/db/pickle-rick-db.sql```
you will need to insert your mysql root password after you hit enter.
then the database should be installed on your local environment.

alternatively you can open the pickle-rick-db.sql file and just run it in the mysql interface

## Run application
go into the terminal and go into the directory where your jar file is located at and do
``java -jar rest-service-0.0.1-SNAPSHOT.jar``

## Run source code

``mvn clean install`` needs to be done first



