
-- IMPORT USERS
-- Username: ADamas // Password: 1234Asdf
INSERT INTO user_entity (id, account_non_expired, account_non_locked, avatar, birthday, created_at, credentials_non_expired, description, email, enabled, fullname, last_password_change_at, password, username) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', true, true, 'peepoSad3.jpg', '1998-01-10', '2023-02-16 18:53:03.527092', true, 'Usuario admin para controlarlo todo y tal', 'damas.viale23@triana.salesianos.edu', true, 'Alejandro Damas', '2023-02-16 18:53:03.499871', '{bcrypt}$2a$10$JgJe3NS9U68X3mZzt73gZeJJncSWw9nd42BXUryyMrvwd9CbBxgS2', 'ADamas');
-- Username: AleUser // Password: 1234Asdf
INSERT INTO user_entity (id, account_non_expired, account_non_locked, avatar, birthday, created_at, credentials_non_expired, description, email, enabled, fullname, last_password_change_at, password, username) VALUES ('ac132001-865b-1a5b-8186-5b5bc7710001', true, true, 'peepoSad3.jpg', '1998-01-10', '2023-02-16 18:53:06.929198', true, 'Usuario user para hacer pruebas y tal', 'alejandrodamas3d@gmail.com', true, 'Alejandro Damas', '2023-02-16 18:53:06.929198', '{bcrypt}$2a$10$KwbG6tq/ZzK/8/gzO2cJO.peKDy1ZWnvcdJSAOSfEpH3h5QVXwlfK', 'AleUser');

-- IMPORT ROLES
INSERT INTO user_roles (user_id, roles) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', 0);
INSERT INTO user_roles (user_id, roles) VALUES ('ac132001-865b-1a5b-8186-5b5bc7710001', 1);
