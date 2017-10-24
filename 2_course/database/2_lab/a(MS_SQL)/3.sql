SELECT DISTINCT product.maker, pc.model, pc.price
FROM pc
INNER JOIN product ON pc.model = product.model;
