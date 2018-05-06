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


CREATE TABLE IF NOT EXISTS `bsb_bank`.`payments` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;

insert into payments (name) VALUE ('phone');
insert into payments (name) VALUE ('home');
insert into payments (name) VALUE ('internet');
insert into payments (name) VALUE ('tickets');
insert into payments (name) VALUE ('penalty');
insert into payments (name) VALUE ('other');


RENAME TABLE `bsb_bank`.`payments` to `bsb_bank`.`payment_type`;

CREATE TABLE IF NOT EXISTS `bsb_bank`.`payments` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL     DEFAULT NULL,
  `payment_type_id` int(11) null DEFAULT null,
  `number` int(11) null DEFAULT NULL ,
  `summ` DOUBLE null DEFAULT NULL ,
  PRIMARY KEY (`id`),
  INDEX  (payment_type_id),
  FOREIGN KEY (payment_type_id)
  REFERENCES payment_type(id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;

ALTER table bsb_bank.payments add client_id INT(11) NULL DEFAULT null;

ALTER TABLE bsb_bank.payments add CONSTRAINT client_id_fkey
FOREIGN KEY (client_id)
  REFERENCES clients(id)
  ON DELETE CASCADE;


insert into payments (id, description, payment_type_id, number, summ, client_id) values (31, 'Quick and the Dead, The', 4, 9169122, 590.05, 9);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (32, 'One-Armed Swordsman, The (Dubei dao)', 7, 1419058, 1982.7, 24);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (33, 'Man Who Sleeps, The (Un homme qui dort)', 6, 8207674, 1563.5, 24);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (34, 'Death of the Incredible Hulk, The', 7, 5170732, 1496.36, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (35, 'Rubin and Ed', 4, 5557497, 1798.27, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (36, 'Prime Cut', 5, 5450685, 279.55, 5);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (37, 'Great Gatsby, The', 8, 4642219, 865.12, 9);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (38, 'Good Night to Die, A', 6, 9601246, 960.62, 7);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (40, 'Change of Habit', 4, 4090183, 144.64, 19);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (41, 'Flubber', 7, 8328213, 1808.44, 4);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (42, 'Cold Fish (Tsumetai nettaigyo)', 8, 7845599, 348.75, 5);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (43, 'Dr. Goldfoot and the Bikini Machine', 7, 4593091, 1439.73, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (44, 'Topaze', 7, 2126867, 1549.32, 13);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (45, 'Free the Nipple', 5, 9127519, 1409.32, 24);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (46, 'We Are The Night (Wir sind die Nacht)', 5, 1588226, 1298.55, 16);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (47, 'Black Sheep', 8, 1111279, 1392.35, 20);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (48, 'Super Troopers', 4, 9700275, 1934.11, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (49, 'Plastic Age, The', 8, 4127522, 1475.74, 13);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (50, 'Bell Boy, The', 6, 1000495, 775.12, 16);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (51, 'Night Passage', 6, 2937104, 246.08, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (52, 'Steal This Movie!', 7, 7577360, 210.46, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (53, 'Death by China ', 6, 6896826, 1223.04, 18);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (54, 'Dance Party, USA', 5, 4807097, 1548.2, 18);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (55, 'Dawn of the Dead', 5, 7296998, 1771.39, 6);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (56, 'Walk to Remember, A', 5, 5308202, 209.18, 6);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (57, 'Eat a Bowl of Tea', 6, 9786202, 531.37, 17);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (59, 'Break-Up, The', 8, 9191421, 1675.83, 15);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (60, 'Camille Claudel 1915', 8, 6238896, 332.62, 25);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (61, 'Divo, Il', 8, 3663917, 961.31, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (62, 'Point Blank', 6, 5013399, 1728.51, 15);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (63, 'Swastika', 8, 8379975, 404.0, 13);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (64, 'State of Grace', 4, 4315591, 1132.51, 16);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (65, 'Billy Two Hats (Lady and the Outlaw, The)', 8, 7690825, 1412.34, 17);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (66, 'Remonstrance', 7, 9864619, 1338.8, 5);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (67, 'In the House', 4, 4473294, 1089.3, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (68, 'Christmas Carol, A', 7, 4667044, 1089.72, 14);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (69, 'Dark Skies', 6, 2645264, 750.46, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (70, 'The Sword and the Rose', 8, 5905469, 317.94, 20);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (71, 'Spanking the Monkey', 8, 3175248, 1884.4, 11);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (72, 'Heartbeeps', 6, 1000081, 1805.67, 13);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (73, 'Wedding Party, The', 4, 9340124, 295.63, 8);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (74, 'Mark of the Vampire', 8, 6848624, 609.11, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (76, 'Pavilion of Women', 4, 3333366, 1505.95, 11);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (77, 'I Want You', 7, 4047532, 1746.11, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (78, 'Terminal USA', 6, 1224272, 1454.21, 13);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (79, 'Left Behind', 8, 9447424, 1282.45, 8);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (80, 'Touchback', 4, 3164968, 1324.77, 19);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (81, 'Alice Doesn''t Live Here Anymore', 4, 2018143, 554.75, 15);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (82, 'Madhouse', 6, 8840638, 825.3, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (83, 'The Hunchback of Paris', 8, 6079513, 1971.42, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (84, 'Evolution', 6, 3438735, 1868.56, 23);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (85, 'Kika', 4, 2808912, 769.53, 5);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (86, 'Ornamental Hairpin (Kanzashi)', 6, 3618698, 334.78, 22);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (87, 'Pest, The', 5, 6315169, 956.5, 16);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (88, 'Zen Noir', 8, 9870231, 1382.79, 26);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (89, 'Kal Ho Naa Ho', 7, 8053209, 293.65, 10);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (90, 'Boccaccio ''70', 5, 4239331, 1777.24, 17);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (93, 'Locke', 4, 4943212, 109.25, 17);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (94, 'Princess Raccoon (Operetta tanuki goten)', 4, 6927462, 274.66, 4);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (95, 'Caught Up', 6, 2629776, 1136.98, 13);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (96, 'Bang the Drum Slowly', 7, 8053901, 702.92, 19);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (97, 'Border, The', 5, 6987470, 1658.75, 12);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (98, 'Wrecked', 5, 4711490, 159.12, 4);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (99, 'Onmyoji (Onmyoji: The Yin Yang Master)', 5, 4817198, 478.05, 26);
insert into payments (id, description, payment_type_id, number, summ, client_id) values (100, 'Michael Shayne: Private Detective', 4, 3597616, 373.77, 4);


ALTER table bsb_bank.payments add user_id INT(11) NULL DEFAULT null;

ALTER TABLE bsb_bank.payments add CONSTRAINT user_id_fkey
FOREIGN KEY (user_id)
REFERENCES user(id)
  ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS `bsb_bank`.`depositType` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL     DEFAULT NULL,
  `percentage` DOUBLE null DEFAULT null,
  `term` int(11) null DEFAULT NULL ,
  `min_summ` DOUBLE null DEFAULT NULL ,
  `currency` VARCHAR(3) NULL DEFAULT null,
  `capitalization` BOOLEAN null DEFAULT null,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARACTER SET = utf8;


CREATE TABLE currency
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  currency VARCHAR(3) NOT NULL
);
CREATE UNIQUE INDEX currency_currency_uindex ON currency (currency);


ALTER TABLE  bsb_bank.deposit_type CHANGE currency currency_id INT(11) NOT NULL;


ALTER TABLE bsb_bank.deposit_type add CONSTRAINT currency_fkey
FOREIGN KEY (currency_id)
REFERENCES currency(id)
  ON DELETE CASCADE;


INSERT INTO bsb_bank.currency (currency.currency) VALUES ('USD');
INSERT INTO bsb_bank.currency (currency.currency) VALUES ('BYN');
INSERT INTO bsb_bank.currency (currency.currency) VALUES ('EUR');


INSERT INTO bsb_bank.deposit_type (description, percentage, term, min_summ, currency_id, capitalization) VALUES ('Простой', 5, 24, 1000, 4, TRUE );
INSERT INTO bsb_bank.deposit_type (description, percentage, term, min_summ, currency_id, capitalization) VALUES ('Непростой', 10, 48, 6000, 4, FALSE );




CREATE TABLE deposit
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  deposit_id INT NOT NULL,
  summ DOUBLE NOT NULL,
  term INT NOT NULL,
  start_date DATE NOT NULL,
  client_id INT NOT NULL,
  user_id INT NOT NULL,
  CONSTRAINT deposit_id_fk FOREIGN KEY (deposit_id) REFERENCES deposit_type (id),
  CONSTRAINT deposit_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (id),
  CONSTRAINT deposit_user_id_fk FOREIGN KEY (user_id) REFERENCES user (id)
);

ALTER TABLE deposit ADD endDate DATE NOT NULL;
ALTER TABLE deposit
  MODIFY COLUMN client_id INT(11) NOT NULL AFTER endDate,
  MODIFY COLUMN user_id INT(11) NOT NULL AFTER endDate;


ALTER TABLE bsb_bank.deposit CHANGE endDate end_date DATE NOT NULL;