DELIMITER //
CREATE TRIGGER save_db_structure
BEFORE INSERT ON link
FOR EACH ROW
  BEGIN
    INSERT INTO link (adress) VALUES ('Db structure is failed');
  END;
DELIMITER ;