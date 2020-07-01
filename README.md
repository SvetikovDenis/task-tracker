# Simple Srping Boot REST API Task Tracker
Technologies used : Spring Boot , Spring Data JPA , Spring Security, JWT Token , MySQL , Liquibase , Lombok

Database : With help of Liquibase auto creating tables and loading data 


## Available operations via API:

## User
<pre>
User registration:              POST /api/v1/register 
User login :                    POST /api/v1/auth/login Response token 
Edit user details :             PUT /api/v1/users/ 
Delete user(only for admin):    DELETE /api/v1/users/{userId} 
Get user details :              GET /api/v1/users/{userId} 
Get all users :                 GET /api/v1/users/ pageable result  </pre>

## Tasks
<pre>
Create task :              POST /api/v1/tasks/ 
When creating new task, current logged user will be assign to this task 
Edit task:                 PUT /api/v1/tasks/ 
Change task status :       PUT /api/v1/tasks/{taskId}?status=1 
Delete task :              DELETE /api/v1/tasks/{taskId}  
Assign new user to task:   PUT /api/v1/tasks/task/{taskId}/user/{userId} 
Get all tasks(examples):
  - filter by status :     GET /api/v1/tasks/?filter=status&value=2 
  - sort by new users :    GET /api/v1/tasks/?sort=user&order=desc 
  - sort by new users :    GET /api/v1/tasks/?sort=user&order=asc 
  - combined :             GET /api/v1/tasks/?filter=status&value=2&sort=user&order=asc 
  - by user id :           GET /api/v1/tasks/user?id=2 
  - by username :          GET /api/v1/tasks/user?username=mike99 

Admin login : (username: root, password: root)
</pre>

