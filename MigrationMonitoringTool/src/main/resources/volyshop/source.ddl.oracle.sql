create table products(
  id number,
  name varchar(200),
  price decimal,
  total_quantity number,
  primary key (id)
);
create table customers(
  id number,
  first_name varchar(200),
  last_name varchar(200),
  primary key (id)
);
create table orders (
  id number,
  submit_date timestamp,
  customer_id number,
  primary key (id),
  foreign key (id) references customers(id)
);
create table orders_products (
  product_id number,
  order_id number,
  quantity number,
  primary key (order_id, product_id),
  foreign key (product_id) references products(id),
  foreign key (order_id) references orders(id)
);
