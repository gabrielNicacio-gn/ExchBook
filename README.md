# ExchBook Api (Back-End)  
REST API developed for managing a book exchange platform, enabling users to register books, copies, and authors, create trade offers by selecting desired and offered books, and complete exchanges with interested users.

## Running locally with Docker
Make sure you have docker installed, and tye the following comands:
```
git clone https://github.com/gabrielNicacio-gn/ExchBook.git
cd Exchbook
docker compose up -d
```

Wait for docker to upload all the dependencies and generate the tables in the database

### Using the project ###

- In browser, enter the following URL for open swagger interface <br>
  <http://localhost:8080/swagger>
  
- In terminal, use the curl in some of the API routess 
  ```
  curl http://localhost:8080/"route" 
  ```
- For stop executing the project

  ```
  docker compose down
  ```
  
- For stop and delete the images docker genereated, use
  ```
  docker compose down --rmi all
  ```
  
## Technologies
- Stack -> Java 21, Spring, JPA, Postgres for database and Docker for infrastructure

## API Endpoints
  Method      |       Route           |     Description
:------------:|:---------------------:|:---------------------------------------------------------------
  **GET**     |    /author/{id}       | Get author by Id
  **GET**     |    /authors           | Get all authors
 **POST**     |     /author           | Create a new author
  **PUT**     |    /author/{id}       | Update a author by Id 
**DELETE**    |     /author/{id}      | Delete a author by Id

-------------:|:---------------------:|:-----------------------------------------------------------------
 **GET**      |     /book/{id}        | Get book by Id
 **GET**      |    /expenses          | Get all expenses
 **POST**     |    /expense           | Create a new expense
 **PUT**      |   /expense/{id}        | Update a expense by Id
 **DELETE**   |   /expense/{id}        | Delete a expense by Id
 -------------------------------------------------------------------------------------------------------
 **GET**      | /category-expense/{id} | Get category by Id
 **GET**      | /categorys-expense    | Get all categorys
 **POST**     | /category-expense     | Create a new category
 **PUT**      | /category-expense/{id} | Update a category
 **DELETE**   | /category-expense/{id} | Delete a category
 **GET**      | /financial-target/{id} | Get target by Id
 **GET**      |  /financial-targets   | Get all targets
 **POST**     |  /financial-target    | Create a new target
 **PUT**      | /financial-target/{id} | Update target by Id
 **DELETE**   | /financial-target/{id} | Delete target by Id


 
