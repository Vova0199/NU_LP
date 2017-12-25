CREATE TRIGGER FKforCityMany on Student.Employers
AFTER INSERT,UPDATE
AS
IF exists (SELECT * FROM Student.Employers WHERE IdPosition NOT IN
		(SELECT IdPosition from dbo.position))
BEGIN
PRINT 'Error : foreign key doesnt exists'
ROLLBACK TRANSACTION
END