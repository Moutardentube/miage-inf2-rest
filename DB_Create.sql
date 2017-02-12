/*CREATE DATABASE `University` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `University`.`StudentList` (
  `id` int(11) NOT NULL UNIQUE AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `University`.`StudentList`
(`name`)
VALUES
('Les beaux gosses'),
('Les cerveaux'),
('Les khoya');

CREATE TABLE `University`.`Student` (
  `id` int(11) NOT NULL UNIQUE AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `list_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FOREIGN KEY(`list_id`) 
    REFERENCES `University`.`StudentList`(`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `University`.`Student`
(`name`,
`list_id`)
VALUES
('Oscar', 1),
('Ludo', 2),
('Mikhail', 3);
