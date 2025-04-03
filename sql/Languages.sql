DROP TABLE Languages;

CREATE TABLE Languages (
    game_id NUMBER(10),
    language VARCHAR2(50),
    PRIMARY KEY (game_id, language),
    FOREIGN KEY (game_id) REFERENCES Games(game_id)
);
