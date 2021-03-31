CREATE TABLE userOrder (
    id int NOT NULL,
    desc varchar(255) NOT NULL,
    country varchar(255),
    quantity int,
    PRIMARY KEY (id)
);
insert into userOrder (id, desc, country, quantity) values (1, 'Akshay', 'UK', 30);
insert into userOrder (id, desc, country, quantity) values (2, 'Ashish', 'UK', 33);
insert into userOrder (id, desc, country, quantity) values (3, 'Praveen', 'UK', 34);
insert into userOrder (id, desc, country, quantity) values (4, 'Parshuram', 'UK', 28);