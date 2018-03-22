CREATE SCHEMA IF NOT EXISTS `bsb_bank`
  DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `bsb_bank`.`role` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;

INSERT INTO `bsb_bank`.`role` VALUES (1, 'admin');

INSERT INTO `bsb_bank`.`role` VALUES (2, 'user');

CREATE TABLE IF NOT EXISTS `bsb_bank`.`user` (
  `id`       INT(11)     NOT NULL AUTO_INCREMENT,
  `login`    VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name`     VARCHAR(45) NULL     DEFAULT NULL,
  `role_id`  INT(11)     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  INDEX `role_idx` (`role_id` ASC),
  CONSTRAINT `role`
  FOREIGN KEY (`role_id`)
  REFERENCES `bsb_bank`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 33
  DEFAULT CHARACTER SET = utf8;

INSERT INTO user (`login`, `password`, `name`, `role_id`) VALUES ('admin', 'admin', 'admin', 1);

INSERT INTO user (`login`, `password`, `name`, `role_id`) VALUES ('user', 'user', 'user', 2);

INSERT INTO user (name, login, password, role_id) VALUES ('Starla Fantham', 'sfantham0', 'sfantham0', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Sena Ivashinnikov', 'sivashinnikov1', 'sivashinnikov1',2);
INSERT INTO user (name, login, password, role_id) VALUES ('Kelby Ballin', 'kballin2', 'kballin2', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Parker Ygo', 'pygo3', 'pygo3', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Nessi Barbe', 'nbarbe4', 'nbarbe4', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Therese Sorsbie', 'tsorsbie5', 'tsorsbie5', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Waite Burgen', 'wburgen6', 'wburgen6', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Dar McGragh', 'dmcgragh7', 'dmcgragh7', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Tremaine Limming', 'tlimming8', 'tlimming8', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Had Kira', 'hkira9', 'hkira9', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Addy Le Floch', 'alea', 'alea', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Clio Symondson', 'csymondsonb', 'csymondsonb', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Kipp Geeves', 'kgeevesc', 'kgeevesc', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Eliza Ridett', 'eridettd', 'eridettd', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Gregory Boutton', 'gbouttone', 'gbouttone', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Alon Mounce', 'amouncef', 'amouncef', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Brew Symes', 'bsymesg', 'bsymesg', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Zelda Dinsdale', 'zdinsdaleh', 'zdinsdaleh', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Jarrett Smithyman', 'jsmithymani', 'jsmithymani', 2);
INSERT INTO user (name, login, password, role_id) VALUES ('Marnia Stickford', 'mstickfordj', 'mstickfordj', 2);


ALTER TABLE user ADD submitted BOOLEAN  NOT NULL DEFAULT true;



CREATE TABLE IF NOT EXISTS `bsb_bank`.`clients` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL     DEFAULT NULL,
  `phone_number` VARCHAR(20) NULL,
  `pasport_seria` VARCHAR(2) NULL,
  `pasport_nuber` int(7) null,
  `address` VARCHAR(50) null,
  `birth_date` DATE null,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Nickolaus Uebel', '+63 279 268 0447', 'PH', 6646413, '5351 Melvin Alley', '1972-02-17');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Aili Melloi', '+62 245 274 4279', 'ID', 3481847, '32 Golf View Hill', '1980-07-14');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Fowler Franzonetti', '+43 732 748 2646', 'AT', 1452729, '26 Melby Terrace', '1998-10-04');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Yoshi Dobbin', '+86 777 978 4161', 'CN', 6339547, '936 Lakeland Junction', '1969-05-23');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Vania McFadden', '+86 292 851 6152', 'CN', 5613166, '9479 Gina Way', '1979-01-05');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Meriel Braunds', '+20 363 961 7170', 'EG', 5573113, '7237 Old Gate Hill', '1999-05-06');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Ermina Mullany', '+389 734 941 2831', 'MK', 7498009, '17961 Lawn Crossing', '1987-07-02');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Juliann Nyles', '+55 941 286 5058', 'BR', 7209796, '9767 3rd Hill', '1951-02-19');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Wandis Sepey', '+51 518 413 6531', 'PE', 5710921, '8 Dunning Terrace', '1952-12-17');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Uriah Trill', '+385 432 993 2168', 'HR', 8428781, '214 Blaine Drive', '1971-11-20');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Fernandina D''Ambrogi', '+86 233 738 0422', 'CN', 3875179, '03203 Prairieview Center', '1979-12-16');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Lucienne Crudge', '+55 731 545 0372', 'BR', 3192242, '30 Monument Park', '1982-01-04');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Liliane Jaksic', '+62 619 329 0520', 'ID', 3587146, '3 Nevada Crossing', '1952-07-28');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Yolande Orht', '+48 814 985 9023', 'PL', 4946359, '74 Hermina Drive', '1985-12-15');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Terza Tratton', '+66 357 508 5676', 'TH', 1687116, '43 Waubesa Avenue', '1957-11-08');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Gerek Grigoriscu', '+63 474 395 7584', 'PH', 4757850, '43 Lillian Alley', '2001-06-25');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Amelina Rope', '+86 375 715 8532', 'CN', 9587622, '2 East Circle', '1997-08-19');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Nerita Tremethack', '+62 197 934 6196', 'ID', 3530399, '1678 Sundown Center', '1953-10-28');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Moshe Colley', '+234 539 366 3968', 'NG', 2216762, '2773 Fairfield Avenue', '1973-03-08');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Giustina Norcutt', '+86 961 223 7264', 'CN', 7441640, '72947 Sycamore Avenue', '1975-01-24');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Sonnnie Bew', '+62 559 280 9973', 'ID', 7238403, '37 Merrick Lane', '1989-08-30');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Kerby Martensen', '+7 668 121 7485', 'RU', 4048276, '15199 Basil Alley', '1984-05-17');
insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values ('Caterina Sockell', '+55 627 992 4188', 'BR', 8953809, '8073 Transport Point', '1960-07-16');
