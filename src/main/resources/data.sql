insert into sector (abbr, name) values
('SMA', 'Sciences Mathématiques et applications'),
('SMI', 'Sciences Mathématiques et informatique'),
('SMC', 'Sciences de la matière Chimie'),
('SMP', 'Sciences de la matière Physique'),
('STU', 'Sciences de la terre et de l’univers'),
('SVI', 'Sciences de la vie');

insert into student (first_name, last_name, apogee_code, birth_date, sector_id, semester) values
('salah', 'ghanim', 24324, current_date, 1, 'S6'),
('moad', 'modani', 24324, current_date, 1, 'S6'),
('osama', 'jahri', 24324, current_date, 1, 'S6'),
('ayman', 'wadi', 24324, current_date, 1, 'S6');


insert into user_app (first_name, last_name, email, password, registered_on, role, is_online) values
('salah', 'ghanim', 'example1@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'SUPER_ADMIN', false),
('marwan', 'aarich', 'example2@example.com', '$2a$10$9YFiP8EmHhwIPhkZIaaMbOfagaYUdyHwddXTQS35yTLEBdCryQk.O', current_date, 'USER', false),
('soufiane', 'modani', 'example3@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK',  current_date, 'USER', false),
('adam', 'jawhari', 'example4@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'ADMIN', false),
('yassine', 'modani', 'example5@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'ADMIN', false);

insert into module (name) values 
('module1'),
('module2'),
('module3'),
('module4');

insert into module_sector (module_id, sector_id) values
(1, 1),
(2, 2),
(3, 3),
(4, 4);

insert into module_semester (module_id, semester_id) values
(1, 'S1'),
(2, 'S2'),
(3, 'S3'),
(4, 'S4');

insert into session (module_id, sector_id, user_id, semester, session_time, created_at) values
(1, 1, 1, 'S1', '08H:30-10H:00', current_date),
(1, 2, 2, 'S2', '08H:30-10H:00', current_date),
(2, 2, 2, 'S2', '08H:30-10H:00', current_date),
(3, 2, 2, 'S2', '08H:30-10H:00', current_date),
(4, 2, 2, 'S2', '08H:30-10H:00', current_date);

insert into absence values
(2, 1, 'PRESENT'),
(2, 2, 'ABSENT'),
(2, 3, 'PRESENT'),
(2, 4, 'ABSENT');




--insert into user_app (first_name, last_name, email, password, registered_on, role, is_online) values
--('salah', 'ghanim', 'example1@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'SUPER_ADMIN', true),
--('ayman', 'elwadi', 'example3@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'USER', true),
----('yassine', 'modani', 'example5@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'ADMIN', true),
--('mustafa', 'bouzghar', 'example7@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date, 'ADMIN', true);