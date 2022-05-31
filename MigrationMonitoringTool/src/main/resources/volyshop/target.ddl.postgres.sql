create table object_types(
  object_type_id numeric,
  name text,
  primary key (object_type_id)
);
create table objects(
  object_id numeric,
  name text,
  object_type_id numeric,
  primary key (object_id),
  foreign key (object_type_id) references object_types(object_type_id)
);
create table attributes(
  attr_id numeric,
  name text,
  object_type_id numeric,
  primary key (attr_id),
  foreign key (object_type_id) references object_types(object_type_id)
);
create table parameters(
  object_id numeric,
  attribute_id numeric,
  value text,
  order_number numeric,
  primary key (object_id, attribute_id),
  foreign key (object_id) references objects(object_id),
  foreign key (attribute_id) references attributes(attr_id)
);