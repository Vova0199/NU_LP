CREATE FUNCTION Student.getPosition(@id INT)
  RETURNS NVARCHAR(50)
  BEGIN
    DECLARE @result NVARCHAR(50)
    SELECT @result = name_of_street FROM Student.street WHERE IdStreet = @id

    RETURN @result
  END
GO

/*SELECT name, web_page, Student.getPosition(IdSteet) AS position FROM dbo.Pharmacy;*/