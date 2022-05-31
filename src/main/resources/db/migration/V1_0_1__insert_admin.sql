insert into users(email, password) VALUES
    ('admin@admin.com', '$2a$12$4sCmDCRtONKMG57mfb2HmOcOzKvgZJkMI0NZfX/k26SmJXHRctUKO');

insert into roles(role_type) values
    ('ROLE_ADMIN');

insert into users_to_roles(user_id, role_id) values (1, 1);