-- schema.sql
CREATE TABLE IF NOT EXISTS books (
                                     isbn        VARCHAR(20) PRIMARY KEY,
    title       TEXT NOT NULL,
    total_copies INTEGER NOT NULL CHECK (total_copies >= 0)
    );

CREATE TABLE IF NOT EXISTS loans (
                                     id          BIGSERIAL PRIMARY KEY,
                                     isbn        VARCHAR(20) NOT NULL REFERENCES books(isbn),
    user_id     TEXT,
    loan_date   DATE NOT NULL DEFAULT CURRENT_DATE,
    due_date    DATE NOT NULL,
    returned    BOOLEAN NOT NULL DEFAULT FALSE,
    return_date DATE
    );

-- Índices útiles
CREATE INDEX IF NOT EXISTS idx_loans_active ON loans(isbn, returned) WHERE returned = FALSE;

-- Libros (se insertan primero)
INSERT INTO books(isbn, title, total_copies) VALUES
                                                 ('123456', 'Cien años de soledad', 4),
                                                 ('654321', 'Don Quijote de la Mancha', 5),
                                                 ('112233', 'La sombra del viento', 2),
                                                 ('334455', 'Cronica de una muerte anunciada', 3),
                                                 ('556677', 'El amor en los tiempos del cólera', 4),
                                                 ('778899', 'Rayuela', 1),
                                                 ('990011', 'Veinte poemas de amor y una canción desesperada', 5),
                                                 ('223344', 'Como agua para chocolate', 2),
                                                 ('445566', 'La casa de los espíritus', 3),
                                                 ('667788', 'El Aleph', 4),
                                                 ('889900', 'Pedro Páramo', 5),
                                                 ('102030', 'Ficciones', 2),
                                                 ('302010', 'La ciudad y los perros', 3),
                                                 ('405060', 'Boquitas pintadas', 1),
                                                 ('605040', 'La tía Julia y el escribidor', 4),
                                                 ('708090', 'El túnel', 5),
                                                 ('908070', 'Los de abajo', 2),
                                                 ('135790', 'La fiesta del Chivo', 3),
                                                 ('246801', 'Sobre héroes y tumbas', 4),
                                                 ('975310', 'El coronel no tiene quien le escriba', 5)
    ON CONFLICT (isbn) DO NOTHING;

-- Préstamos (user_id es el número inicial de cada línea)
INSERT INTO loans(isbn, user_id, loan_date, due_date, returned, return_date) VALUES
                                                                                 ('123456', '1', '2023-02-10', '2023-02-24', FALSE, NULL),
                                                                                 ('123456', '2', '2023-06-05', '2023-06-19', FALSE, NULL),
                                                                                 ('123456', '3', '2023-11-18', '2023-12-02', FALSE, NULL),
                                                                                 ('123456', '4', '2024-04-01', '2024-04-15', FALSE, NULL),
                                                                                 ('654321', '1', '2023-01-22', '2023-02-05', TRUE, '2023-07-14'),
                                                                                 ('654321', '3', '2023-10-29', '2023-11-12', TRUE, '2024-03-03'),
                                                                                 ('112233', '1', '2023-09-01', '2023-09-15', TRUE, '2024-02-06'),
                                                                                 ('334455', '3', '2023-12-11', '2023-12-25', FALSE, NULL),
                                                                                 ('556677', '1', '2023-05-15', '2023-05-29', TRUE, '2023-08-20'),
                                                                                 ('556677', '3', '2024-01-27', '2024-02-10', TRUE, '2024-04-14'),
                                                                                 ('223344', '1', '2023-08-05', '2023-08-19', TRUE, '2024-01-10'),
                                                                                 ('445566', '2', '2023-10-04', '2023-10-18', TRUE, '2024-03-13'),
                                                                                 ('667788', '2', '2023-07-09', '2023-07-23', TRUE, '2023-12-24'),
                                                                                 ('667788', '4', '2024-05-07', '2024-05-21', FALSE, NULL),
                                                                                 ('889900', '1', '2023-01-01', '2023-01-15', TRUE, '2023-04-06'),
                                                                                 ('889900', '3', '2023-08-11', '2023-08-25', TRUE, '2023-11-19'),
                                                                                 ('102030', '2', '2024-05-02', '2024-05-16', FALSE, NULL),
                                                                                 ('302010', '1', '2023-09-28', '2023-10-12', TRUE, '2024-01-09'),
                                                                                 ('302010', '3', '2024-04-15', '2024-04-29', FALSE, NULL),
                                                                                 ('605040', '2', '2023-06-23', '2023-07-07', TRUE, '2023-10-08'),
                                                                                 ('605040', '4', '2024-03-17', '2024-03-31', FALSE, NULL),
                                                                                 ('708090', '1', '2025-06-04', '2025-06-18', FALSE, NULL),
                                                                                 ('708090', '2', '2022-10-13', '2022-10-27', FALSE, NULL),
                                                                                 ('708090', '3', '2023-02-20', '2023-03-06', FALSE, NULL),
                                                                                 ('708090', '4', '2023-07-28', '2023-08-11', FALSE, NULL),
                                                                                 ('708090', '5', '2024-01-05', '2024-01-19', FALSE, NULL),
                                                                                 ('908070', '1', '2023-11-16', '2023-11-30', FALSE, NULL),
                                                                                 ('908070', '2', '2024-04-23', '2024-05-07', FALSE, NULL),
                                                                                 ('135790', '2', '2023-09-12', '2023-09-26', TRUE, '2023-12-30'),
                                                                                 ('246801', '3', '2023-02-26', '2023-03-12', TRUE, '2023-09-04'),
                                                                                 ('975310', '1', '2023-05-09', '2023-05-23', TRUE, '2023-08-14'),
                                                                                 ('975310', '3', '2023-11-22', '2023-12-06', TRUE, '2024-04-30'),
                                                                                 ('975310', '5', '2024-06-12', '2024-06-26', FALSE, NULL);

