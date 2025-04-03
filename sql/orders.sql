DROP TABLE orders;

CREATE TABLE Orders (
    purchase_id NUMBER(10) PRIMARY KEY,
    user_id NUMBER(10) NOT NULL,
    card_id NUMBER(10) NOT NULL,
    purchase_date DATE DEFAULT SYSDATE,
    total_price NUMBER(10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (card_id) REFERENCES Payment_Cards(card_id)
);