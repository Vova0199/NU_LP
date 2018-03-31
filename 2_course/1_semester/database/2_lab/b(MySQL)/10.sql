SELECT product.type, pc.model, MAX(pc.price)
FROM pc
JOIN product ON pc.model = product.model
WHERE pc.model = product.model


union
SELECT product.type, printer.model, MAX(printer.price)
FROM printer
JOIN product ON printer.model = product.model
WHERE printer.model = product.model 

union
SELECT product.type, laptop.model, MAX(laptop.price)
FROM laptop
JOIN product ON laptop.model = product.model
WHERE laptop.model = product.model;


