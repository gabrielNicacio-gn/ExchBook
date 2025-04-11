

CREATE TABLE user_owner(
    id_user SERIAL PRIMARY KEY,
    email VARCHAR(80) UNIQUE NOT NULL,
    password VARCHAR(80) NOT NULL
);

CREATE TABLE user_roles(
    id_roles SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    role VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user_owner(id_user)
);

CREATE TABLE book(
    id_book SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);
CREATE INDEX ON book(title);

CREATE TABLE author(
    id_author SERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE book_author(
    id_book INTEGER NOT NULL,
    id_author INTEGER NOT NULL,
    FOREIGN KEY (id_book) REFERENCES book(id_book),
    FOREIGN KEY (id_author) REFERENCES author(id_author)
);

CREATE TABLE book_copy(
    id_book_copy SERIAL PRIMARY KEY,
    id_book INTEGER NOT NULL,
    --id_user_owner INTEGER NOT NULL,
    condition VARCHAR(30),
    FOREIGN KEY (id_book) REFERENCES book(id_book)
    --FOREIGN KEY (id_user_owner) REFERENCES user_owner(id_user)
);

CREATE TABLE book_review(
     id_book_review SERIAL PRIMARY KEY,
     id_user_review INTEGER NOT NULL,
     id_book_evaluated INTEGER NOT NULL,
     score_book INTEGER NOT NULL,
     comment TEXT NOT NULL,
     date_review TIMESTAMP NOT NULL,
     FOREIGN KEY (id_user_review) REFERENCES user_owner(id_user),
     FOREIGN KEY (id_book_evaluated) REFERENCES book(id_book)
);

CREATE TABLE book_edition(
    id_book_edition SERIAL PRIMARY KEY,
    id_book INTEGER NOT NULL,
    year_of_publication VARCHAR(10) NOT NULL,
    number_edition VARCHAR(30) NOT NULL,
    format VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_book) REFERENCES book(id_book)
);

CREATE TABLE exchange_offer(
    id_exchange_offer SERIAL PRIMARY KEY,
    id_copy_offered INTEGER NOT NULL,
    id_book_desired INTEGER NOT NULL,
   -- id_offering_user INTEGER NOT NULL,
    status_exchange VARCHAR(30) NOT NULL,
    date_of_offer TIMESTAMP NOT NULL,
    FOREIGN KEY (id_copy_offered) REFERENCES book_copy(id_book_copy),
    FOREIGN KEY (id_book_desired) REFERENCES book(id_book)
   -- FOREIGN KEY (id_offering_user) REFERENCES user_owner(id_user)
);

CREATE TABLE exchange(
    id_exchange SERIAL PRIMARY KEY,
   -- id_user_accepted INTEGER NOT NULL,
    id_exchange_offer INTEGER NOT NULL,
    is_approved BOOLEAN NOT NULL,
    date_of_exchange TIMESTAMP NOT NULL,
   -- FOREIGN KEY (id_user_accepted) REFERENCES user_owner(id_user),
    FOREIGN KEY (id_exchange_offer) REFERENCES exchange_offer(id_exchange_offer)
);


