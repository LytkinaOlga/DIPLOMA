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
  customer_id number,
  submit_date timestamp,
  primary key (id),
  foreign key (id) references customers(id)
);
create table orders_products (
  order_id number,
  product_id number,
  quantity number,
  primary key (order_id, product_id),
  foreign key (order_id) references orders(id),
  foreign key (product_id) references products(id)
);
