insert into users(email, password) VALUES
    ('admin@admin.com', '12345');

insert into roles(role_type) values
    ('ROLE_ADMIN');

insert into users_to_roles(user_id, role_id) values (1, 1);