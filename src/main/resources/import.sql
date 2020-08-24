/* SOCIOS */
INSERT INTO `db763781722`.`socios` (`apellidos`, `cuenta_bancaria`, `cuota`, `email`, `estado`, `f_baja`, `f_nacimiento`, `f_alta`, `ts_ult_mod`, `foto`, `nif`, `nombre`, `sexo`, `telefono`, `id_user_mo`, `talla`) VALUES ('Bonilla Ruiz', 'ES63 3068 7664 67 3826264636', 40, 'javbonilla@gmail.com', 1, NULL, '1985-10-31', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '', '28951505W', 'Javier', 1, '+34 626 33 37 01', 1, 4); 
INSERT INTO `db763781722`.`socios` (`apellidos`, `cuenta_bancaria`, `cuota`, `email`, `estado`, `f_baja`, `f_nacimiento`, `f_alta`, `ts_ult_mod`, `foto`, `nif`, `nombre`, `sexo`, `telefono`, `id_user_mo`) VALUES ('Córdoba Barragán', 'ES30 0081 2725 16 7598035526', 40, 'jcordobab@gmail.com', 1, NULL, '1967-01-08', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '', '28556606J', 'Juan José', 1, '+34 615 69 78 68', 1); 

/* USUARIOS */
INSERT INTO `db763781722`.`usuarios` (`id`, `enabled`, `password`, `es_password_temporal`) VALUES (1, 1, '$2a$10$m89UkU4GCUsGqr2/gdNz7uySICdUM9w8Yfd4Qro7g/dQo0NZQGUjy', 0); 
INSERT INTO `db763781722`.`usuarios` (`id`, `enabled`, `password`, `es_password_temporal`) VALUES (2, 1, '$2a$10$m89UkU4GCUsGqr2/gdNz7uySICdUM9w8Yfd4Qro7g/dQo0NZQGUjy', 1); 

/* ROLES */
INSERT INTO `db763781722`.`roles` (`authority`, `usuario_id`) VALUES ('ROLE_ADMIN', 1);
INSERT INTO `db763781722`.`roles` (`authority`, `usuario_id`) VALUES ('ROLE_MANAGER', 1);
INSERT INTO `db763781722`.`roles` (`authority`, `usuario_id`) VALUES ('ROLE_USER', 1);

INSERT INTO `db763781722`.`roles` (`authority`, `usuario_id`) VALUES ('ROLE_USER', 2);

/* PARAMETROS */
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('SEXO', '1', 'Hombre', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('SEXO', '2', 'Mujer', CURRENT_TIMESTAMP());

INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('CUOTA', '40', '40 - Completa', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('CUOTA', '20', '20 - Reducida', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('CUOTA', '99', 'Cuota de Honor', CURRENT_TIMESTAMP());

INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '1', 'XXS', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '2', 'XS', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '3', 'S', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '4', 'M', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '5', 'L', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '6', 'XL', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('TALLA', '7', 'XXL', CURRENT_TIMESTAMP());

INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('ROL', '1', 'ROLE_USER', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('ROL', '3', 'ROLE_ADMIN', CURRENT_TIMESTAMP());
INSERT INTO `db763781722`.`parametros` (`parametro`, `codigo`, `valor`, `ts_ult_mod`) VALUES ('ROL', '2', 'ROLE_MANAGER', CURRENT_TIMESTAMP());