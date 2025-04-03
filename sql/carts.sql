DROP TABLE Carts;

CREATE TABLE Carts (
    user_id NUMBER(10),
    game_id NUMBER(10),
    PRIMARY KEY (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id)
);