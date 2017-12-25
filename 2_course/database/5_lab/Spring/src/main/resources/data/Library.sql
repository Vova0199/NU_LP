
CREATE SCHEMA IF NOT EXISTS gadget DEFAULT CHARACTER SET utf8 ;
USE gadget;

CREATE TABLE IF NOT EXISTS gadget (
  gadget_id BIGINT NOT NULL AUTO_INCREMENT,
  gadget_name VARCHAR(45) NOT NULL,
  author VARCHAR(45) NOT NULL,
  country VARCHAR(50) NULL,
  year_of_creating INT NULL,
  amount INT NOT NULL,
  PRIMARY KEY (gadget_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS city (
  city_id BIGINT NOT NULL AUTO_INCREMENT,
  city VARCHAR(25) NOT NULL,
  PRIMARY KEY (city_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS person (
  person_id BIGINT NOT NULL AUTO_INCREMENT,
  surname VARCHAR(25) NOT NULL,
  name VARCHAR(25) NOT NULL,
  email VARCHAR(45) NULL,
  city_id BIGINT NULL,
  street VARCHAR(30) NULL,
  apartment VARCHAR(10) NULL,
  PRIMARY KEY (person_id),
  CONSTRAINT fk_person_city1
  FOREIGN KEY (city_id)
  REFERENCES gadget.city (city_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS person_gadget (
  person_id BIGINT NOT NULL,
  gadget_id BIGINT NOT NULL,
  PRIMARY KEY (person_id, gadget_id),
  CONSTRAINT persongadget_ibfk_1
  FOREIGN KEY (person_id)
  REFERENCES gadget.person (person_id),
  CONSTRAINT persongadget_ibfk_2
  FOREIGN KEY (gadget_id)
  REFERENCES gadget.gadget (gadget_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS logger (
  logger_id BIGINT NOT NULL AUTO_INCREMENT,
  person VARCHAR(50) NOT NULL,
  gadget VARCHAR(90) NOT NULL,
  action VARCHAR(10) NOT NULL,
  time_stamp DATETIME NOT NULL,
  user VARCHAR(50) NULL,
  PRIMARY KEY (logger_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;









