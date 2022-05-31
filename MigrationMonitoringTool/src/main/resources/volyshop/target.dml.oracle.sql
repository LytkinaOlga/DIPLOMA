insert into object_types(object_type_id, name) values (1, 'customer');
insert into object_types(object_type_id, name) values (2, 'product');
insert into object_types(object_type_id, name) values (3, 'order');

insert into attributes(attr_id, name, object_type_id) values (1, 'First Name', null);
insert into attributes(attr_id, name, object_type_id) values (2, 'Last Name', null);
insert into attributes(attr_id, name, object_type_id) values (3, 'Product', 2);