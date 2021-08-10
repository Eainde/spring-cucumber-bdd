CREATE TABLE userOrder(
id integer not null
constraint userOrder_i01p
primary key,
desc varchar(30),
country varchar(50),
quantity integer
);

insert into userOrder (id, desc, country, quantity) values (1, 'Akshay', 'UK', 30);
insert into userOrder (id, desc, country, quantity) values (2, 'Ashish', 'UK', 33);
insert into userOrder (id, desc, country, quantity) values (3, 'Praveen', 'UK', 34);
insert into userOrder (id, desc, country, quantity) values (4, 'Parshuram', 'UK', 28);