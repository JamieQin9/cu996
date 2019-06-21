DROP DATABASE IF EXISTS cplanner;
CREATE DATABASE cplanner CHARACTER SET utf8 COLLATE utf8_general_ci;
USE cplanner;


DROP TABLE IF EXISTS `user`;
CREATE TABLE user (
  id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nt_name           VARCHAR(45)  NOT NULL,
  password_md5 VARCHAR(255)     NOT NULL,
  email        VARCHAR(255)     NOT NULL,
  role         VARCHAR(15)     NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `activity`;
CREATE TABLE activity (
  id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  oppturnity_id INT             NOT NULL,
  name       VARCHAR(255)       NOT NULL,
  start      VARCHAR(255)       NOT NULL,
  end        VARCHAR(255)       NOT NULL,
  role       VARCHAR(45)             NOT NULL,
  chargeable VARCHAR(45)             NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `skillset`;
CREATE TABLE skillset (
  id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  activity_id INT             NOT NULL,
  skill_name VARCHAR(255)     NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `catgory`;
CREATE TABLE catgory (
  id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  activity_id INT             NOT NULL,
  catgory_name VARCHAR(255)     NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `capacity`;
CREATE TABLE capacity (
  id      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  action  VARCHAR(255),
  effort_booked INT             NOT NULL,
  user_id INT            NOT NULL,
  activity_id INT            NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO user (nt_name, password_md5, email, role)
VALUES ('admin', '3e6693e83d186225b85b09e71c974d2d', 'admin@admin.com', 'admin');

INSERT INTO user (nt_name, password_md5, email, role)
VALUES ('test', '3e6693e83d186225b85b09e71c974d2d', 'test@test.com', 'test');