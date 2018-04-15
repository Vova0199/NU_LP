DELIMITER //
CREATE TRIGGER check_employees
BEFORE INSERT ON user
FOR EACH ROW
  BEGIN
    IF LEFT(NEW.login, 2) = '[a-z]' THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'WRONG uuid VALUE';
    END IF;
  END //
DELIMITER ;