/*
 * ER/Studio Data Architect SQL Code Generation
 * Project :      Elektrik.DM1
 *
 * Date Created : Friday, December 08, 2017 15:12:09
 * Target DBMS : Microsoft SQL Server 2014
 */

/* 
 * TABLE: electrick 
 */

CREATE TABLE electrick(
    id       int         IDENTITY(1,1),
    PIB      char(30)    NULL,
    phone    char(10)    NULL,
    CONSTRAINT PK5 PRIMARY KEY NONCLUSTERED (id)
)
go



IF OBJECT_ID('electrick') IS NOT NULL
    PRINT '<<< CREATED TABLE electrick >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE electrick >>>'
go

/* 
 * TABLE: list_of_payment 
 */

CREATE TABLE list_of_payment(
    PIB            char(10)    NOT NULL,
    id             int         NOT NULL,
    date           date        NULL,
    amount         char(10)    NULL,
    sum_of_pilg    char(10)    NULL,
    total_sum      int         NULL,
    CONSTRAINT PK4 PRIMARY KEY NONCLUSTERED (PIB, id)
)
go



IF OBJECT_ID('list_of_payment') IS NOT NULL
    PRINT '<<< CREATED TABLE list_of_payment >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE list_of_payment >>>'
go

/* 
 * TABLE: Payer 
 */

CREATE TABLE Payer(
    PIB                 char(10)    NOT NULL,
    id                  int         NOT NULL,
    adress              char(10)    NULL,
    list_of_payments    char(10)    NULL,
    account             char(10)    NULL,
    CONSTRAINT PK3 PRIMARY KEY NONCLUSTERED (PIB, id)
)
go



IF OBJECT_ID('Payer') IS NOT NULL
    PRINT '<<< CREATED TABLE Payer >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE Payer >>>'
go

/* 
 * TABLE: list_of_payment 
 */

ALTER TABLE list_of_payment ADD CONSTRAINT RefPayer3 
    FOREIGN KEY (PIB, id)
    REFERENCES Payer(PIB, id)
go


/* 
 * TABLE: Payer 
 */

ALTER TABLE Payer ADD CONSTRAINT Refelectrick1 
    FOREIGN KEY (id)
    REFERENCES electrick(id)
go


