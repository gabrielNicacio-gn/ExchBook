

CREATE TABLE user_owner(
    id_user UUID PRIMARY KEY,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(80) NOT NULL
);

CREATE TABLE user_roles(
    id_roles UUID PRIMARY KEY,
    id_user UUID NOT NULL,
    role VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user_owner(id_user)
);

CREATE TABLE book(
    id_book UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL
);

CREATE TABLE book_copy(
    id_book_copy UUID PRIMARY KEY,
    id_book UUID NOT NULL,
    id_user_owner UUID NOT NULL,
    FOREIGN KEY (id_book) REFERENCES book(id_book),
    FOREIGN KEY (id_user_owner) REFERENCES user_owner(id_user)
);

CREATE TABLE book_review(
     id_book_review UUID PRIMARY KEY,
     id_user_review UUID NOT NULL,
     id_book_evaluated UUID NOT NULL,
     score_book INT NOT NULL,
     comment TEXT NOT NULL,
     date_review TIMESTAMP NOT NULL,
     FOREIGN KEY (id_user_review) REFERENCES user_owner(id_user),
     FOREIGN KEY (id_book_evaluated) REFERENCES book(id_book)
);

CREATE TABLE book_edition(
    id_book_edition UUID PRIMARY KEY,
    id_book UUID NOT NULL,
    year_of_publication VARCHAR(10) NOT NULL,
    number_edition VARCHAR(30) NOT NULL,
    format VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_book) REFERENCES book(id_book)
);

CREATE TABLE exchenge_offer(
    id_exchenge_offer UUID PRIMARY KEY,
    id_copy_offered UUID NOT NULL,
    id_book_disired UUID NOT NULL,
    id_offering_user UUID NOT NULL,
    status_exchenge VARCHAR(30) NOT NULL,
    date_of_offer TIMESTAMP NOT NULL,
    FOREIGN KEY (id_copy_offered) REFERENCES book_copy(id_book_copy),
    FOREIGN KEY (id_book_disired) REFERENCES book(id_book),
    FOREIGN KEY (id_offering_user) REFERENCES user_owner(id_user)
);

CREATE TABLE exchenge(
    id_exchenge UUID PRIMARY KEY,
    id_user_accepted UUID NOT NULL,
    id_exchenge_offer UUID NOT NULL,
    is_approved BOOLEAN NOT NULL,
    date_of_offer TIMESTAMP NOT NULL,
    FOREIGN KEY (id_user_accepted) REFERENCES user_owner(id_user),
    FOREIGN KEY (id_exchenge_offer) REFERENCES exchenge_offer(id_exchenge_offer)
);


