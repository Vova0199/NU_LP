DELIMITER //
DROP FUNCTION IF EXISTS principal;

CREATE FUNCTION principal()
  RETURNS parinc Varchar(1);
  RETURNS parinc1 Varchar(1);
  RETURNS parinc2 Varchar(1);
  BEGIN
    DECLARE parinc Varchar(3);
    SELECT  LEFT(name,1) into parinc  FROM user;
    SELECT  LEFT(surname,1) into parinc1  FROM user;
    SELECT  LEFT(middle_name, 1) into parinc2  FROM user;

    RETURN parinc, parinc1, parinc2;
  END;

DELIMITER ;

SELECT * from user WHERE name  LIKE "parinc%" && surname  LIKE "parinc1%" && middle_name  LIKE "parinc2%";