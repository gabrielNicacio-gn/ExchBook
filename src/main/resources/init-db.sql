

CREATE TABLE user_owner(
    id_user INT PRIMARY KEY,
    email VARCHAR(80) UNIQUE NOT NULL,
    password VARCHAR(80) NOT NULL
);

CREATE TABLE user_roles(
    id_roles INT PRIMARY KEY,
    id_user INT NOT NULL,
    role VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user_owner(id_user)
);

CREATE TABLE book(
    id_book INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL
);

CREATE INDEX ON book(title);

CREATE TABLE book_copy(
    id_book_copy INT PRIMARY KEY,
    id_book INT NOT NULL,
    id_user_owner INT NOT NULL,
    condition VARCHAR(30),
    FOREIGN KEY (id_book) REFERENCES book(id_book),
    FOREIGN KEY (id_user_owner) REFERENCES user_owner(id_user)
);

CREATE TABLE book_review(
     id_book_review INT PRIMARY KEY,
     id_user_review INT NOT NULL,
     id_book_evaluated INT NOT NULL,
     score_book INT NOT NULL,
     comment TEXT NOT NULL,
     date_review TIMESTAMP NOT NULL,
     FOREIGN KEY (id_user_review) REFERENCES user_owner(id_user),
     FOREIGN KEY (id_book_evaluated) REFERENCES book(id_book)
);

CREATE TABLE book_edition(
    id_book_edition INT PRIMARY KEY,
    id_book INT NOT NULL,
    year_of_publication VARCHAR(10) NOT NULL,
    number_edition VARCHAR(30) NOT NULL,
    format VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_book) REFERENCES book(id_book)
);

CREATE TABLE exchenge_offer(
    id_exchenge_offer INT PRIMARY KEY,
    id_copy_offered INT NOT NULL,
    id_book_disired INT NOT NULL,
    id_offering_user INT NOT NULL,
    status_exchenge VARCHAR(30) NOT NULL,
    date_of_offer TIMESTAMP NOT NULL,
    FOREIGN KEY (id_copy_offered) REFERENCES book_copy(id_book_copy),
    FOREIGN KEY (id_book_disired) REFERENCES book(id_book),
    FOREIGN KEY (id_offering_user) REFERENCES user_owner(id_user)
);

CREATE TABLE exchenge(
    id_exchenge INT PRIMARY KEY,
    id_user_accepted INT NOT NULL,
    id_exchenge_offer INT NOT NULL,
    is_approved BOOLEAN NOT NULL,
    date_of_offer TIMESTAMP NOT NULL,
    FOREIGN KEY (id_user_accepted) REFERENCES user_owner(id_user),
    FOREIGN KEY (id_exchenge_offer) REFERENCES exchenge_offer(id_exchenge_offer)
);


