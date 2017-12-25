USE gadget;

DELIMITER //
CREATE TRIGGER AfterInsertPersonGadget
AFTER INSERT
ON person_gadget FOR EACH ROW
BEGIN
	DECLARE name_person VARCHAR(50);
    DECLARE name_gadget VARCHAR(90);
    SELECT CONCAT(surname, ' ', name) INTO name_person
    FROM person WHERE person_id=new.person_id;
    SELECT CONCAT(gadget_name, ' / ', Author) INTO name_person
    FROM gadget WHERE gadget_id=new.gadget_id;
	INSERT INTO logger (person, gadget, action,
								time_stamp, user)
	VALUES(name_person,  name_gadget, 'GOT', NOW(), USER() );
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER AfterDeletePersonGadget
AFTER DELETE
ON person_gadget FOR EACH ROW
BEGIN
	DECLARE name_person VARCHAR(50);
    DECLARE name_gadget VARCHAR(90);
    SELECT CONCAT(surname, ' ', name) INTO name_gadget
    FROM person WHERE person_id=old.person_id;
    SELECT CONCAT(gadget_name, ' / ', author) INTO name_gadget
    FROM gadget WHERE gadget_id=old.gadget_id;
	INSERT INTO Logger (person, gadget, action,
                      time_stamp, user)
	VALUES(name_person,  name_gadget, 'GAVEBACK', NOW(), USER() );
END //
DELIMITER ;






