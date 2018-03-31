select distinct product.maker 
from product, pc 
where product.type='PC' and exists(select product.maker from pc,product where pc.model=Product.model);