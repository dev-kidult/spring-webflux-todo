spring webflux + r2dbc todo web study
---
#### Run the Application 
with maven

```sbtshell
mvn spring-boot:run
```

#### Using it
###### DBMS
init data in `src` > `resources` > `schema.sql` 

if you want fix the data modify the `schema.sql` file

###### APIs
GET `/api/todos` get all todos

GET `/api/todos/{id}` get todo by id

POST `/api/todos` save the todo 

with json body `{ "content" : "your todo" }` 

PUT `/api/todos/{id}/done` done todo

PUT `/api/todos/{id}/content` update todo

with json body `{ "content" : "update todo" }`

DELETE `/api/todos/{id}` delete todo  
