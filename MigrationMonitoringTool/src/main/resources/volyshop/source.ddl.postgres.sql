create table products(
  id numeric,
  name text,
  price numeric,
  total_quantity numeric,
  primary key (id)
);
create table customers(
  id numeric,
  first_name text,
  last_name text,
  primary key (id)
);
create table orders (
  id numeric,
  submit_date timestamp,
  customer_id numeric,
  primary key (id),
  foreign key (customer_id) references customers(id)
);
create table orders_products (
  product_id numeric,
  order_id numeric,
  quantity numeric,
  primary key (order_id, product_id),
  foreign key (product_id) references products(id),
  foreign key (order_id) references orders(id)
);
