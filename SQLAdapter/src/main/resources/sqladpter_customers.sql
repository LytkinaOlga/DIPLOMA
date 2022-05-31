insert into objects
select id from customers
where customer.id in (select entity_id from {ML_TABLE})
/

insert into parameters
select 1, first_name from customers
where customer.id in (select entity_id from {ML_TABLE})
/

insert into parameters
select 2, last_name from customers
where customer.id in (select entity_id from {ML_TABLE})
/

update {ML_TABLE} set status = 'OK'
/