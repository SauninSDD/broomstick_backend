insert into client_addresses values (1, 23, 4, 5, 45, 7, '1');

-- insert into clients values (5f828be3-cd04-481a-82ca-561f46fa3d35, )

select * from client_addresses;

select count(*) from products where id_category = 1;

update products set product_name = 'product2' where id_category = 2;

select * from products where product_name like '%Product 1%';

update products set product_price = 9 where id_category = 1;
update products set product_price = 5 where id_category = 2;