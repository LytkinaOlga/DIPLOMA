insert into objects
select id, first_name || ' ' || last_name, 1 from customers
where customers.id in (select entity_id from {ML_TABLE})
/

insert into parameters
select id, 1, first_name, 1 from customers
where customers.id in (select entity_id from {ML_TABLE})
/

insert into parameters
select id, 2, last_name, 1 from customers
where customers.id in (select entity_id from {ML_TABLE})
/

update {ML_TABLE} set status = 'OK'
/