INSERT
IGNORE users (id, email, is_verified, password, username, date_register, full_name, profile_picture, verified_token, is_deleted, created_date)
VALUES ( 'f906a295847742309a4de4101dea0c55',
        'solehsolihin2021@gmail.com',
        true,
        '$2a$10$1plWUsDFrI7KQtjN5g9skuN7aFwYiobMvgLN1PNirxQXSHMJ7wkfu',
        'masteradmin',
        current_date,
        'natasha romanov',
        'https://res.cloudinary.com/nielnaga/image/upload/v1615870303/download-removebg-preview_zyrump.png',
        'potatolalalala',
        false,
        current_date );

INSERT
IGNORE role (id, name, is_deleted, input_customer, read_all_customer, input_transaction, read_all_transaction, approve_transaction, read_all_report, read_all_report_by_transaction, master)
VALUES ('c30f9a16859611eb8dcd0242ac130003',
                      'MASTER', false, false, false, false, false, false, false, false, true);

INSERT
ignore users_roles (user_id, role_id)
VALUES ('f906a295847742309a4de4101dea0c55',
        'c30f9a16859611eb8dcd0242ac130003');