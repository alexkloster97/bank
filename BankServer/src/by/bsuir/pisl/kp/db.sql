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


ALTER TABLE user ADD submitted BOOLEAN  NOT NULL DEFAULT true
