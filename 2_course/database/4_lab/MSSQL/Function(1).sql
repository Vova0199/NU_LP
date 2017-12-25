CREATE FUNCTION Student.avgExperience()
  RETURNS int
  BEGIN
    DECLARE @result INT
    SELECT @result = AVG(work_experience) FROM Student.Employers;

    RETURN @result;
  END
GO

/*SELECT * FROM Student.Employers WHERE work_experience > Student.avgExperience();*/