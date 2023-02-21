
-- IMPORT USERS
-- Username: ADamas // Password: 1234Asdf
INSERT INTO user_entity (id, account_non_expired, account_non_locked, avatar, birthday, created_at, credentials_non_expired, description, email, enabled, fullname, last_password_change_at, password, username) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', true, true, 'http://placeimg.com/640/480', '1998-01-10', '2023-02-16 18:53:03.527092', true, 'Usuario admin para controlarlo todo y tal', 'damas.viale23@triana.salesianos.edu', true, 'Alejandro Damas', '2023-02-16 18:53:03.499871', '{bcrypt}$2a$10$JgJe3NS9U68X3mZzt73gZeJJncSWw9nd42BXUryyMrvwd9CbBxgS2', 'ADamas');
-- Username: AleUser // Password: 1234Asdf
INSERT INTO user_entity (id, account_non_expired, account_non_locked, avatar, birthday, created_at, credentials_non_expired, description, email, enabled, fullname, last_password_change_at, password, username) VALUES ('ac132001-865b-1a5b-8186-5b5bc7710001', true, true, 'http://placeimg.com/640/480', '1998-01-10', '2023-02-16 18:53:06.929198', true, 'Usuario user para hacer pruebas y tal', 'alejandrodamas3d@gmail.com', true, 'Alejandro Damas', '2023-02-16 18:53:06.929198', '{bcrypt}$2a$10$KwbG6tq/ZzK/8/gzO2cJO.peKDy1ZWnvcdJSAOSfEpH3h5QVXwlfK', 'AleUser');

-- IMPORT ROLES
INSERT INTO user_roles (user_id, roles) VALUES ('ac132001-865b-1a5b-8186-5b5bba320000', 0);
INSERT INTO user_roles (user_id, roles) VALUES ('ac132001-865b-1a5b-8186-5b5bc7710001', 1);

-- IMPORT INGREDIENTS
INSERT INTO ingredient (id, img, name) VALUES ('ac132001-865c-10ce-8186-5c517b990000', 'default_ingredient.jpg', 'Salad');
INSERT INTO ingredient (id, img, name) VALUES ('ac132001-865c-10ce-8186-5c5182e50001', 'default_ingredient.jpg', 'Chicken');
INSERT INTO ingredient (id, img, name) VALUES ('ac132001-865c-10ce-8186-5c519de80004', 'default_ingredient.jpg', 'Bacon');
INSERT INTO ingredient (id, img, name) VALUES ('ac132001-865c-10ce-8186-5c539cd60008', 'default_ingredient.jpg', 'Tuna');
INSERT INTO ingredient (id, img, name) VALUES ('ac132001-865c-10ce-8186-5c53e6710009', 'default_ingredient.jpg', 'Cheese');

-- IMPORT RECIPES
INSERT INTO recipe (id, category, created_at, description, img, name, prep_time, steps, user_id) VALUES ('ac132001-865c-1a80-8186-5c9b088d0001', 'Vegan', '2023-02-17 00:41:49.581014', 'systems Legacy Cambridgeshire platforms ROI', 'default_recipe.jpg', 'Licensed Soft Pizza', 20, 'You can''t index the sensor without copying the neural THX panel!', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO recipe (id, category, created_at, description, img, name, prep_time, steps, user_id) VALUES ('ac132001-865c-1a80-8186-5c9b147c0002', 'Standard', '2023-02-17 00:41:52.635754', 'bypassing AI Customer Tuna', 'default_recipe.jpg', 'Rustic Fresh Chair', 30, 'You can''t reboot the array without synthesizing the solid state SAS transmitter!', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO recipe (id, category, created_at, description, img, name, prep_time, steps, user_id) VALUES ('ac132001-865c-1a80-8186-5c9b1f370003', 'Low-fats', '2023-02-17 00:41:55.383604', 'firewall Borders Outdoors', 'default_recipe.jpg', 'Handcrafted Soft Salad', 40, 'We need to input the primary RAM capacitor!', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO recipe (id, category, created_at, description, img, name, prep_time, steps, user_id) VALUES ('ac132001-865c-1a80-8186-5c9b30f50004', 'Low-carbs', '2023-02-17 00:41:59.925066', 'Tactics Advanced Licensed protocol', 'default_recipe.jpg', 'Sleek Soft Chips', 30, 'Use the cross-platform COM panel, then you can synthesize the cross-platform port!', 'ac132001-865b-1a5b-8186-5b5bba320000');
INSERT INTO recipe (id, category, created_at, description, img, name, prep_time, steps, user_id) VALUES ('ac132001-865c-1a80-8186-5c9afcbe0000', 'Vegetarian', '2023-02-17 00:41:46.549134', 'Tools monetize', 'default_recipe.jpg', 'Intelligent Fresh Mouse', 50, 'Multi-channelled impactful attitude', 'ac132001-865b-1a5b-8186-5b5bba320000');
