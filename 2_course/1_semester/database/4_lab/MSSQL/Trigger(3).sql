CREATE TRIGGER EmpName ON Student.Employers
AFTER INSERT,UPDATE
AS
IF exists (SELECT * from Employers where name like '������' or name like '����' or name like '������' or name like '����������')
BEGIN
PRINT 'Error'
ROLLBACK TRANSACTION
END
GO