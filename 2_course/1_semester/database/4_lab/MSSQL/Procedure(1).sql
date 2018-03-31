CREATE PROCEDURE dbo.InsertEmployee
  @first_name NVARCHAR(50),
  @last_name NVARCHAR(50),
  @middle_name NVARCHAR(50),
  @identification_number NVARCHAR(50),
  @birthday DATE,
  @passport_id NCHAR(10),
  @rank_id INT,
  @degree_id INT,
  @possition_id INT
AS
  BEGIN
    INSERT INTO Student.Employers VALUES
    (@rank_id, @first_name, @last_name, @middle_name,
	 @degree_id, @identification_number,  @passport_id,  
	  @possition_id,@birthday)
  END
GO
