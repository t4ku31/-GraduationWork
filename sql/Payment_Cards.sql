DROP TABLE Payment_Cards;

CREATE TABLE Payment_Cards (
    card_id          NUMBER(10)      PRIMARY KEY,
    user_id          NUMBER(10)      NOT NULL,
    card_type        VARCHAR2(20)    NOT NULL,
    card_number      VARCHAR2(16)    NOT NULL,
    security_code    NUMBER(3)       NOT NULL,
    card_expiry_month NUMBER(2)      NOT NULL,
    card_expiry_year NUMBER(4)       NOT NULL,
    card_holder_name VARCHAR2(50)    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
