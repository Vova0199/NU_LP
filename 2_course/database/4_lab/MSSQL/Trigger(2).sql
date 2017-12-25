CREATE TRIGGER StudentNumCheck ON Student.Employers
AFTER INSERT,UPDATE
AS
DECLARE @Rate int
IF exists (SELECT * from Employers where number_passport like '[a-Z][a-Z][ ][0-9][0-9][0-9][0-9][0-9][0-9]')
BEGIN
PRINT 'Error'
ROLLBACK TRANSACTION
END
GO