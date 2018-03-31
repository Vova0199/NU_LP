SELECT maker,
	CASE WHEN sum(case type when 'Laptop' then 1 else 0 end) = 1
		THEN 'yes'
	ELSE CASE WHEN sum(case type when 'Laptop' then 1 else 0 end) > 1
		THEN CONCAT('yes(',  CAST(sum(case type when 'Laptop' then 1 else 0 end) AS nchar), ')')
			ELSE 'no' END END AS 'laptop'
FROM Product GROUP BY maker;