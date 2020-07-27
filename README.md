# Simple Spring Boot REST API task tracker
Requirements: Spring Boot , Spring Data JPA , Spring Security, JWT Token , MySQL , Liquibase , Lombok , JUnit, Mockito, REST Assured, Docker 

About:

 - Database : using liquibase for creating database, tables and loading sample data on startup 
 - Authorization & authentication with Spring Security and JWT token
 - Proper exception handling with custom detailed exception response
 - Dynamic database queries with Spring Data JPA and Specification
 - Unit & Integration Tests using JUnit, Mockito and REST Assured
 - Docker & docker-compose

## Available operations via API:


## User
<pre>
User registration:              POST /api/v1/register 
User login :                    POST /api/v1/auth/login 
Get user details :              GET /api/v1/users/{userId} 
Get all users :                 GET /api/v1/users/  
   - Pageable result
   - Available multiple request sorting and filtering parameters
Edit user details :             PUT /api/v1/users/ 
Delete user:                    DELETE /api/v1/users/{userId}  </pre>

## Tasks
<pre>
Create task :              POST /api/v1/tasks/ 
When creating new task, current logged in user will be assign to this task 
Edit task:                 PUT /api/v1/tasks/ 
Change task status :       PUT /api/v1/tasks/{taskId}?status={statusId}
Delete task :              DELETE /api/v1/tasks/{taskId}  
Assign new user :          PUT /api/v1/tasks/task/{taskId}/user/{userId} 
Get all tasks , available multiple request sorting and filtering parameters. Example :
- GET /api/v1/tasks?title=call&description=call&status=2&date=2020-01-01&operation=<&sort=created&order=desc

</pre>

