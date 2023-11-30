DROP DATABASE IF EXISTS finalproject;

 CREATE SCHEMA `finalproject`;
 CREATE TABLE `finalproject`.`users` (
   `user_id` INT NOT NULL AUTO_INCREMENT,
   `username` VARCHAR(45) NULL,
   `password` VARCHAR(45) NULL,
   `email` VARCHAR(45) NULL,
   PRIMARY KEY (`user_id`));