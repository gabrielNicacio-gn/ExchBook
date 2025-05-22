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


 Method       |       Route           |     Description
:------------:|:---------------------:|:---------------------------------------------------------------
  **GET**     |    /book/{id}         | Get book by Id
  **GET**     |    /books             | Get all books
 **POST**     |     /book             | Create a new book
  **PUT**     |    /book/{id}         | Update a book by Id 
**DELETE**    |     /book/{id}        | Delete a book by Id


Method        |       Route             |     Description
:------------:|:---------------------:|:---------------------------------------------------------------
  **GET**     |    /copy/{id}         | Get copy by Id
  **GET**     |    /copys             | Get all copys
 **POST**     |     /copy             | Create a new copy
  **PUT**     |    /copy/{id}         | Update a copy by Id 
**DELETE**    |     /copy/{id}        | Delete a copy by Id


Method        |       Route             |     Description
:------------:|:---------------------:|:---------------------------------------------------------------
  **GET**     |    /edition/{id}         | Get edition by Id
  **GET**     |    /editions             | Get all editions
 **POST**     |     /edition             | Create a new edition
  **PUT**     |    /edition/{id}         | Update a edition by Id 
**DELETE**    |     /edition/{id}        | Delete a edition by Id


Method        |       Route             |     Description
:------------:|:---------------------:|:---------------------------------------------------------------
  **GET**     |    /offer/{id}         | Get offer by Id
  **GET**     |    /offers             | Get all offers
 **POST**     |     /offer             | Create a new offer
  **PUT**     |    /offer/{id}         | Update a offer by Id 
**DELETE**    |     /offer/{id}        | Delete a offer by Id


Method        |       Route             |     Description
:------------:|:---------------------:|:---------------------------------------------------------------
  **GET**     |    /exchange/{id}         | Get exchange by Id
  **GET**     |    /exchanges             | Get all exchanges
 **POST**     |     /exchange             | Create a new exchange
  **PUT**     |    /exchange/{id}         | Update a exchange by Id 
**DELETE**    |     /exchange/{id}        | Delete a exchange by Id





 
