insert into sector (abbr, name) values
('SMA', 'Sciences Mathématiques et applications'),
('SMI', 'Sciences Mathématiques et informatique'),
('SMC', 'Sciences de la matière Chimie'),
('SMP', 'Sciences de la matière Physique'),
('STU', 'Sciences de la terre et de l’univers'),
('SVI', 'Sciences de la vie');

insert into student (first_name, last_name, apogee_code, birth_date, sector_id, semester) values
('salah', 'ghanim', 24324, current_date(), 1, 'S6'),
('moad', 'modani', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('osama', 'jahri', 24324, current_date(), 1, 'S6'),
('ayman', 'wadi', 24324, current_date(), 1, 'S6');


insert into user_app (first_name, last_name, email, password, registered_on, role) values
('salah', 'ghanim', 'example1@example.com', 'password', current_date(), 'ADMIN'),
('marwan', 'aarich', 'example4@example.com', '$2a$10$9YFiP8EmHhwIPhkZIaaMbOfagaYUdyHwddXTQS35yTLEBdCryQk.O', current_date(), 'USER'),
('soufiane', 'modani', 'example2@example.com', 'password',  current_date(), 'ADMIN'),
('adam', 'jawhari', 'example3@example.com', 'password', current_date(), 'ADMIN'),
('yassine', 'modani', 'example5@example.com', '$2a$10$ZdNxI.ihMJ6quM7TdhRqFOb9oEXwk0z2tWz/r9CDAjj4cIT1F3gxK', current_date(), 'ADMIN');

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
(1, 1, 1, 'S1', '08H:30-10H:00', current_date()),
(1, 2, 2, 'S2', '08H:30-10H:00', current_date()),
(2, 2, 2, 'S2', '08H:30-10H:00', current_date()),
(3, 2, 2, 'S2', '08H:30-10H:00', current_date()),
(4, 2, 2, 'S2', '08H:30-10H:00', current_date());

insert into absence values
(2, 1, 'PRESENT'),
(2, 2, 'ABSENT'),
(2, 3, 'PRESENT'),
(2, 4, 'ABSENT'),
(2, 5, 'PRESENT'),
(2, 6, 'ABSENT'),
(2, 7, 'PRESENT'),
(2, 8, 'ABSENT'),
(2, 9, 'PRESENT'),
(2, 10, 'ABSENT'),
(2, 11, 'PRESENT'),
(2, 12, 'ABSENT'),
(2, 13, 'PRESENT'),
(2, 14, 'ABSENT'),
(2, 15, 'ABSENT'),
(2, 16, 'ABSENT'),
(2, 17, 'ABSENT'),
(2, 18, 'ABSENT'),
(2, 19, 'ABSENT'),
(2, 20, 'ABSENT'),
(2, 21, 'ABSENT'),
(2, 22, 'ABSENT'),
(2, 23, 'ABSENT'),
(2, 24, 'ABSENT'),
(2, 25, 'PRESENT');

