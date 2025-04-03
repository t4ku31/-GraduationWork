DROP TABLE User_Passwords;

CREATE TABLE User_Passwords (
    user_id NUMBER(10) PRIMARY KEY,
    hashed_password VARCHAR2(255) NOT NULL,
    salt VARCHAR2(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);