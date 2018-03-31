SELECT pc.model, pc.speed, pc.hd
FROM product
INNER JOIN pc ON pc.model = product.model
WHERE product.maker = 'A' and hd >= 10 and hd <=20 order by speed asc;
