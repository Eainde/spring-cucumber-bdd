DROP TABLE IF EXISTS userOrder;

CREATE TABLE userOrder(
id integer not null
constraint userOrder_i01p
primary key,
desc varchar(30),
country varchar(50),
quantity integer
);