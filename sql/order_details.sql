DROP TABLE Order_Details;

CREATE TABLE Order_Details (
    purchase_id NUMBER(10),
    game_id NUMBER(10),
    price NUMBER(10) NOT NULL,
    PRIMARY KEY (purchase_id, game_id),
    FOREIGN KEY (purchase_id) REFERENCES Orders(purchase_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id)
);