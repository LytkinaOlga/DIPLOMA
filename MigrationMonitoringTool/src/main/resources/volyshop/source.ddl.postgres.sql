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
  customer_id numeric,
  submit_date timestamp,
  primary key (id),
  foreign key (customer_id) references customers(id)
);
create table orders_products (
  order_id numeric,
  product_id numeric,
  quantity numeric,
  primary key (order_id, product_id),
  foreign key (order_id) references orders(id),
  foreign key (product_id) references products(id)
);
