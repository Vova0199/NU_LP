use 4_lab;
DELIMITER //
DROP PROCEDURE IF EXISTS InsertUser//

CREATE PROCEDURE InsertUser(
  IN _login VARCHAR(100),  
  IN _name VARCHAR(45),
  IN _surname VARCHAR(45),
  IN _middle_name VARCHAR(45),
  IN _birthday DATE,
  IN _palce_birthday VARCHAR(45),
  IN _rating VARCHAR(150)
)
  BEGIN
    INSERT INTO user (login, surname, name, middle_name, birthday, palce_birthday, rating)
    VALUES (_login, _surname,_name, _middle_name, _birthday, _palce_birthday, _rating);
  END //

DELIMITER ;