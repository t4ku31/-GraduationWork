CREATE TABLE Games (
    game_id NUMBER(10) PRIMARY KEY,
    title VARCHAR2(50) NOT NULL,
    developer VARCHAR2(50) NOT NULL,
    price NUMBER(10,2) NOT NULL,
    release_date DATE,
    description CLOB,
    thumbnail_url VARCHAR2(50)
);