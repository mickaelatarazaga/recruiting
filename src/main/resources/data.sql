INSERT INTO `technologies` (`id`, `name`,`version`) VALUES
(1, 'java','15'),
(2, 'python',null),
(3, 'maven','3.8'),
(4, 'hibernate',null),
(5, 'spring',null);

INSERT INTO `candidates` (`id`,`deleted`, `first_name`,`last_name`, `type_dni`, `dni_number`,`birthday`) VALUES
(1, false,'Mickaela', 'Tarazaga',0,'35879856', '1996-10-25');

INSERT INTO `experiences` (`id`,`candidate_id`, `technology_id`,`years_experience`) VALUES
(1,1, 1,'1'),
(2,1, 3,'1'),
(3,1, 4,'1'),
(4,1, 5,'1');

