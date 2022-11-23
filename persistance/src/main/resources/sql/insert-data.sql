INSERT INTO students (name, surname, age, email, specialisation)
VALUES ('Monika', 'Wojnanowska', 19, 'monika_wojt@gmail.com', 'English literature'),
       ('Mateusz', 'Kozlowsky', 20, 'kozlowsky_mateusz@gmail.com', 'Math analysis'),
       ('Anna', 'Lis', 20, 'anna_lis@gmail.com', 'Chemical technology');

INSERT INTO teachers (name, surname, age, email, subject)
VALUES ('Monika', 'Green', 35, 'monika_gre@gmail.com', 'English'),
       ('Pawel', 'Dudek', 43, 'pawel_dudel@gmail.com', 'Algebra'),
       ('Tomasz', 'Borek', 56, 'borel_tomasz@gmail.com', 'Informatics');

INSERT INTO students_teachers(student_id, teacher_id)
VALUES (1, 1),
       (1, 2),
       (3, 3),
       (3, 2);