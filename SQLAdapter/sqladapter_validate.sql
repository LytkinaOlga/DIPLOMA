update {ML_TABLE} set status = 'FAILED', error_message = 'Last name too long: can''t exceed 10 symbols'
where entity_id in (
  select id from customers where length(last_name) >= 10
)
/

update {ML_TABLE} set status = 'FAILED', error_message = 'Customer does not have any orders'
where entity_id in (
  select c.id
  from customers c left join orders o on c.id = o.customer_id
  where o.id is null
)
/