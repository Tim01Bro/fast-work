insert into type_work(id, type_work_date_create, type_work_describe, type_work_name)
values (777, '2023-01-23', 'test', 'test');
insert into users(id, user_date_create, user_email, user_name, user_password, user_role, user_soname, user_status)
values (777, '2023-04-02', 'user@mail.text', 'hirer', 'test', 'WORKER', 'test', 'ACTIVE');
insert into work(id, work_count_person, work_date_create,work_describe, work_name,work_price, work_status, work_hirer_id,work_type_id)
values (777, 2, '2022-02-04', 'test', 'test', 22.3, 'OPEN', 777, 777);
insert into work(id, work_count_person, work_date_create,work_describe, work_name,work_price, work_status, work_hirer_id,work_type_id)
values (778, 2, '2022-02-04', 'test', 'test', 22.3, 'OPEN', 777, 777);
insert into location(id, location_city, location_country, location_date_create, x, y, location_region, location_street,location_work_id)
values (777, 'test', 'test', '2023-02-05', 78.23, 54.23, 'test', 'test', 778);