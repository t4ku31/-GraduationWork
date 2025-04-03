DROP TABLE Users;

CREATE TABLE Users (
    user_id NUMBER(10) PRIMARY KEY,
    username VARCHAR2(50) NOT NULL UNIQUE,
    email VARCHAR2(50) NOT NULL UNIQUE,
    role VARCHAR2(20) DEFAULT 'user',
    avatar VARCHAR2(50)
);

col role for a10
col email for a20
col avatar for a20


SELECT constraint_name, constraint_type, status, search_condition
FROM user_constraints
WHERE table_name = 'users';
