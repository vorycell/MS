CREATE TABLE IF NOT EXISTS cars (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL
);

INSERT INTO cars (brand, model, year) VALUES
('bmw', 'm3', 2024),
('mercedes', 'c63', 2015),
('lada', 'granta', 2024),
('Toyota', 'Camry', 2020),
('Honda', 'Civic', 2018),
('BMW', 'X5', 2022),
('Ford', 'Focus', 2019),
('Nissan', 'Altima', 2021);