CREATE TRIGGER InsteadOfDeleteProduct ON Student.street INSTEAD OF DELETE AS
RAISERROR(N'�������� ������� �� ������� Product ���������!',16,0);