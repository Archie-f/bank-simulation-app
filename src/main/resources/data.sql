insert into roles( description)
VALUES ( 'Admin'),
       ( 'Cashier');




insert into users(username, password, role_id, is_enabled)
values ( 'Admin','$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',1, true);



insert into users(username, password, role_id, is_enabled)
values ( 'Cashier','$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',2, true);
-- Abc1