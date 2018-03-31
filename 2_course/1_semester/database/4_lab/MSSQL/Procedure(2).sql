CREATE PROCEDURE Student.RandNames AS
  BEGIN
    DECLARE @cnt INT = 0;
    DECLARE @name NVARCHAR(50)

    WHILE @cnt < 10
    BEGIN
      SET @name = 'Noname' + CAST(ROUND(RAND() * 1000 + 1, 0) AS NVARCHAR(50))

      IF NOT EXISTS(SELECT * FROM street WHERE name_of_street = @name)
        BEGIN
          INSERT INTO Student.street VALUES (@cnt, @name)
          SET @cnt = @cnt + 1;
        END
    END;
  END
GO