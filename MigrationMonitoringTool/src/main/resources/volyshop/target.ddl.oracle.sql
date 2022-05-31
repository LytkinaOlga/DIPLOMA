create table object_types(
  object_type_id number,
  name varchar(200),
  primary key (object_type_id)
);
create table objects(
  object_id number,
  name varchar(200),
  object_type_id number,
  primary key (object_id),
  foreign key (object_type_id) references object_types(object_type_id)
);
create table attributes(
  attr_id number,
  name varchar(200),
  object_type_id number,
  primary key (attr_id),
  foreign key (object_type_id) references object_types(object_type_id)
);
create table parameters(
  object_id number,
  attribute_id number,
  value varchar(200),
  order_number number,
  primary key (object_id, attribute_id),
  foreign key (object_id) references objects(object_id),
  foreign key (attribute_id) references attributes(attr_id)
);