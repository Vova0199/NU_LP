use
DELIMITER //
DROP FUNCTION IF EXISTS getBookLink;

CREATE FUNCTION getBookLink(_id INT)
  RETURNS VARCHAR(100)
  BEGIN
    DECLARE _name VARCHAR(100);
    SELECT title INTO _name FROM books WHERE id = _id;

    RETURN _name;
  END;

DELIMITER ;


SELECT adress, books_idbooks from link;