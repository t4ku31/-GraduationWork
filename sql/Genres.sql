DROP TABLE Genres;

CREATE TABLE Genres (
    game_id NUMBER(10),
    genre VARCHAR2(50),
    PRIMARY KEY (game_id, genre),
    FOREIGN KEY (game_id) REFERENCES Games(game_id)
);