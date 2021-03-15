INSERT INTO users (id, email, is_verified, password, username, date_register)
SELECT * FROM (SELECT 'f906a295847742309a4de4101dea0c55',
                      'nieljrnaga@gmail.com',
                      true,
                      '$2a$10$S3cLT6z3D1AN4Xjgp2lc9.gAz4Kmx2cCHglv3p.EZt8Qz/H0.5KdK',
                      'masteradmin',
                      current_date) AS tmp
WHERE NOT EXISTS (
        SELECT users.username FROM users WHERE username = 'masteradmin'
    ) LIMIT 1;

INSERT INTO role (id, name)
SELECT * FROM (SELECT 'c30f9a16859611eb8dcd0242ac130003',
                      'MASTER') AS tmp
WHERE NOT EXISTS (
        SELECT role.name FROM role WHERE role.name = 'MASTER'
    ) LIMIT 1;

INSERT INTO role (id, name)
SELECT * FROM (SELECT '0ca21c1fa3ed4d93b7080792ab26cae4',
                      'STAFF') AS tmp
WHERE NOT EXISTS (
        SELECT role.name FROM role WHERE role.name = 'STAFF'
    ) LIMIT 1;

INSERT INTO role (id, name)
SELECT * FROM (SELECT '0e3c332310c1483797cbe34c419a06e3',
                      'SUPERVISOR') AS tmp
WHERE NOT EXISTS (
        SELECT role.name FROM role WHERE role.name = 'SUPERVISOR'
    ) LIMIT 1;

INSERT ignore users_roles (user_id, role_id)
VALUES ('f906a295847742309a4de4101dea0c55',
        'c30f9a16859611eb8dcd0242ac130003');