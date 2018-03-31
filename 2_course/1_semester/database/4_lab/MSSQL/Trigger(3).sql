CREATE TRIGGER EmpName ON Student.Employers
AFTER INSERT,UPDATE
AS
IF exists (SELECT * from Employers where name like 'Василь' or name like 'Іван' or name like 'Галина' or name like 'Олександра')
BEGIN
PRINT 'Error'
ROLLBACK TRANSACTION
END
GO