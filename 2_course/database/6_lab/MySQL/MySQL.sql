--
-- ER/Studio Data Architect SQL Code Generation
-- Project :      Elektrik.DM1
--
-- Date Created : Friday, December 08, 2017 15:13:53
-- Target DBMS : MySQL 5.x
--

-- 
-- TABLE: electrick 
--
CREATE SCHEMA lab_6;
USE lab_6;

CREATE TABLE electrick(
    id       INT         AUTO_INCREMENT,
    PIB      CHAR(30),
    phone    CHAR(10),
    PRIMARY KEY (id)
)ENGINE=MYISAM
;



-- 
-- TABLE: list_of_payment 
--

CREATE TABLE list_of_payment(
    PIB            CHAR(10)    NOT NULL,
    id             INT         NOT NULL,
    date           DATE,
    amount         CHAR(10),
    sum_of_pilg    CHAR(10),
    total_sum      INT,
    PRIMARY KEY (PIB, id)
)ENGINE=MYISAM
;



-- 
-- TABLE: Payer 
--

CREATE TABLE Payer(
    PIB                 CHAR(10)    NOT NULL,
    id                  INT         NOT NULL,
    adress              CHAR(10),
    list_of_payments    CHAR(10),
    account             CHAR(10),
    PRIMARY KEY (PIB, id)
)ENGINE=MYISAM
;



-- 
-- TABLE: list_of_payment 
--

ALTER TABLE list_of_payment ADD CONSTRAINT RefPayer3 
    FOREIGN KEY (PIB, id)
    REFERENCES Payer(PIB, id)
;


-- 
-- TABLE: Payer 
--

ALTER TABLE Payer ADD CONSTRAINT Refelectrick1 
    FOREIGN KEY (id)
    REFERENCES electrick(id)
;


