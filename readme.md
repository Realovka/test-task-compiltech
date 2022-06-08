# User service
REST API application is for creating user (singup), login, update, delete
user, finding all users. JWT token is used for deleting, updating, finding all users and logout.
It is generated upon login. If you send invalid (or expired) token or if you don't use token at all, you see 
http status 403 Forbidden. You have schedule.variable in application.properties, where you can write
date after which application will be close. 

## How to build
1) clone this repository
2) create schema in database MySQL by script from db.sql file
3) change such values as spring.datasource.url, spring.datasource.username,
spring.datasource.password, server.port in application.properties file to match your database and port
4) run command mvn package (you create jar file application)
5) run command java -jar target/complitech-0.0.1-SNAPSHOT.jar (you run application)

## How to work
You should use HTTP-client (Postman) for work and use port which your have written in application.properties
1) to create a new user you should do 
http://localhost:8081/singup (POST) and to write your data as body in JSON format
   ![signup](https://user-images.githubusercontent.com/61760081/172392342-08f8150a-b430-4810-a80e-c6c570687579.jpg)
If such login already exists you will see exception message
   ![duplicate_entity_signup](https://user-images.githubusercontent.com/61760081/172408000-89a222e9-0a4b-40a6-8603-3da672d35d7a.jpg)
2) login and get a token you should do
http://localhost:8081/singup (POST) and to write your login and password as a JSON format
   ![login](https://user-images.githubusercontent.com/61760081/172393865-ee8fde9a-9832-4ed8-8eb9-cf95d984c423.jpg)
3) to find all users and to receive a message with helping WEBSOCKET you should do
a) connect to localhost:8081/websocket and in HEADERS you need write key 'AUTHORIZATION' and its value
'Bearer xxxx', where xxxx - value your token from point 2)
b) http://localhost:8081/users (GET) and in Authorization you need chose Type - Bearer Token and write
its value from point 2)
   ![message](https://user-images.githubusercontent.com/61760081/172397909-881e0f65-e8e9-4921-8a08-3501f59b4551.jpg)
   ![find_all_users](https://user-images.githubusercontent.com/61760081/172398198-3f0f6e07-7f14-4949-9365-ea8f5c7803b7.jpg)
4) to delete user by id you should do
http://localhost:8081/users/{id} (DELETE) and to write as a parameter user id who you want to delete, in Authorization 
you need chose Type - Bearer Token and write its value from point 2)
   ![delete](https://user-images.githubusercontent.com/61760081/172400284-06fc4215-ec52-4449-abdd-d6ef6ce8b2f8.jpg)
If such user is doesn't exist in DB you will see exception message
   ![delete_not_found](https://user-images.githubusercontent.com/61760081/172400729-0a1d017b-69b3-42a2-907e-7e13f8786957.jpg)
5) to update one or several field values of user you should do
http://localhost:8081/users/{id} (PATCH) and to write values as a JSON format, in Authorization
you need chose Type - Bearer Token and write its value from point 2)
   ![update](https://user-images.githubusercontent.com/61760081/172405295-46388ea3-b4fe-4ed8-bff3-8b4ef40a1b7a.jpg)
   ![update_several_fields](https://user-images.githubusercontent.com/61760081/172405879-0cbcf255-10b3-47d7-8338-15f7bb76c747.jpg)
If such user is doesn't exist in DB you will see exception message
   ![update_not_found](https://user-images.githubusercontent.com/61760081/172406447-9f8d7405-9c20-4db0-bec9-ac3efdbdbf81.jpg)
If such login already exists you will see exception message
   ![update_login_exists](https://user-images.githubusercontent.com/61760081/172411038-b8efc327-96c8-4388-8641-8a5e0b904df5.jpg)
6) to delete range users by id you should do
http://localhost:8081/users/{startId}/{finishId} (DELETE) and to write as parameters from id till id
you want to delete users, in Authorization you need chose Type - Bearer Token and write its value from point 2)
   ![delete_several_users](https://user-images.githubusercontent.com/61760081/172431820-3ddfe2c5-93ce-4c13-87d4-1bf9c4cad53e.jpg)
If these ids aren't in DB you will see exception message
   ![delete_several_not_found](https://user-images.githubusercontent.com/61760081/172433748-b28636ce-59cf-439b-a1a6-9b5a877500b7.jpg)
7) to exit from a system
http://localhost:8081/api/logout (POST) in Authorization you need chose Type - Bearer Token and write its value from point 2)
   ![logout](https://user-images.githubusercontent.com/61760081/172564022-18afa923-bfe6-4b46-9df8-653d5464fff4.jpg)
