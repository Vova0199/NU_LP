CREATE TRIGGER InsteadOfDeleteProduct ON Student.street INSTEAD OF DELETE AS
RAISERROR(N'Удаление записей из таблицы Product запрещено!',16,0);