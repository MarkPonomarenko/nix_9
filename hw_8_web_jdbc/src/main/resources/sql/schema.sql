CREATE SCHEMA hw_8_web_jdbc;

CREATE TABLE hw_8_web_jdbc.courses
(
    id             BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    created        DATETIME(6)  NULL,
    updated        DATETIME(6)  NULL,
    title          VARCHAR(255) NOT NULL,
    teacher        VARCHAR(255) NOT NULL,
    credits        int          NOT NULL
);

create table hw_8_web_jdbc.students
(
    id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    created    DATETIME(6)  NULL,
    updated    DATETIME(6)  NULL,
    last_name  VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    age        INT          NOT NULL
);

create table hw_8_web_jdbc.accounting
(
    id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    course_id  BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE
);