create table product
(
id    number not null primary key,
name  varchar2(255),
price float
);

create sequence product_sequence;

CREATE OR REPLACE TRIGGER product_increment_trigger
  BEFORE INSERT ON product
  FOR EACH ROW
BEGIN
  SELECT product_sequence.nextval
  INTO :new.id
  FROM dual;
END;

create table customer
(
    id         number not null primary key,
    firstname  varchar2(255),
    lastname   varchar2(255)
);

create sequence customer_sequence;

CREATE OR REPLACE TRIGGER customer_increment_trigger
  BEFORE INSERT ON customer
  FOR EACH ROW
BEGIN
  SELECT customer_sequence.nextval
  INTO :new.id
  FROM dual;
END;

create table orders
(
    id           number not null primary key,
    order_date         date,
    customer_id  number,
    CONSTRAINT   fk_customer
    FOREIGN KEY  (customer_id)
    REFERENCES   customer(id)
);

create sequence order_sequence;

CREATE OR REPLACE TRIGGER order_increment_trigger
  BEFORE INSERT ON orders
  FOR EACH ROW
BEGIN
  SELECT order_sequence.nextval
  INTO :new.id
  FROM dual;
END;


create table order_product
(
    id         number not null primary key,
    order_id   number,
    product_id number,
    quantity   number,
    
    CONSTRAINT   fk_order
    FOREIGN KEY  (order_id)
    REFERENCES   orders(id),
    
    CONSTRAINT   fk_product
    FOREIGN KEY  (product_id)
    REFERENCES   product(id)
);

create sequence order_product_sequence;

CREATE OR REPLACE TRIGGER order_product_increment_trigger
  BEFORE INSERT ON order_product
  FOR EACH ROW
BEGIN
  SELECT order_product_sequence.nextval
  INTO :new.id
  FROM dual;
END;


