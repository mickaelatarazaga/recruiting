INSERT INTO `technologies` (`id`, `name`,`version`) VALUES
(1, 'Java','15'),
(2, 'Python',null),
(3, 'Maven','3.8'),
(4, 'Hibernate',null),
(5, 'Spring',null);

INSERT INTO `candidates` (`id`,`deleted`, `first_name`,`last_name`, `type_dni`, `dni_number`,`birth_date`) VALUES
(1, false,'Mickaela', 'Tarazaga',0,'35879856', '1996-10-25');

INSERT INTO `experiences` (`id`,`candidate_id`, `technology_id`,`years_experience`) VALUES
(1,1, 1,'1'),
(2,1, 3,'1'),
(3,1, 4,'1'),
(4,1, 5,'1');

