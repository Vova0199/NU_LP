use 4_lab;
DELIMITER //
DROP PROCEDURE IF EXISTS InsertUser_Books//

CREATE PROCEDURE InsertUser_Books(IN _login VARCHAR(150),
  IN _rating INT(10),
  IN _rating1 INT(10),
  IN _surname varchar(45),
  IN _middle_name VARCHAR(45),
  IN _birthday Date,
  IN _place_birthday VARCHAR(150),
  IN _id_books int(10),
  IN _title VARCHAR(150),
  IN _author VARCHAR(150),
  IN _password_idpassword int(10))  
  BEGIN
	insert into user(_login, surname, _middle_name, _birthday, _palce_birthday, _rating, _password_idpassword)
    values(login,surname,middle_name, birthday, place_birthday, rating, password_idpassword);
    INSERT INTO books (_idbooks, _title, _author, _rating1)
    VALUES (idbooks, title, author, rating);
    insert into user_has_books(user_login, books_idbooks)
    values(user_login,books_idbooks);
     SELECT * FROM user_has_books;
  END //
DELIMITER ;







/*INSERT INTO user (login, name,  surname, middle_name, birthday, place_birthday, rating, password_idpassword) VALUES
					(1,'St. mans', 'asfd','Svichado','1999-09-09','asfasd', 5, 1111);


INSERT INTO books (idbooks, title, author, rating) VALUES
(1, 'Bible', 'Sherlok', 4);

insert into user_has_books(user_login, books_idbooks, tree_idtree)
    values(1, 1,1);*/
