-- Usuário de teste (senha: 123456)
INSERT INTO users (username, email, password) VALUES
('admin', 'admin@logitrack.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2Qfcy');

INSERT INTO vehicles (plate, model, category) VALUES
('ABC-1234', 'Fiat Fiorino', 'LEVE'),
('XYZ-9876', 'Volvo FH', 'PESADO'),
('KJG-1122', 'Mercedes Sprinter', 'LEVE'),
('LMN-4455', 'Scania R500', 'PESADO');

INSERT INTO trips (vehicle_id, departure_at, arrival_at, origin_city, destination_city, mileage) VALUES
(1, DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', -10, DATEADD('HOUR', 9, CURRENT_TIMESTAMP)), 'Sao Paulo', 'Rio de Janeiro', 435.00),
(1, DATEADD('DAY', -7, CURRENT_TIMESTAMP), DATEADD('DAY', -7, DATEADD('HOUR', 3, CURRENT_TIMESTAMP)), 'Rio de Janeiro', 'Niteroi', 20.50),
(2, DATEADD('DAY', -6, CURRENT_TIMESTAMP), DATEADD('DAY', -5, DATEADD('HOUR', 8, CURRENT_TIMESTAMP)), 'Curitiba', 'Belo Horizonte', 1000.00),
(3, DATEADD('DAY', -4, CURRENT_TIMESTAMP), DATEADD('DAY', -4, DATEADD('HOUR', 6, CURRENT_TIMESTAMP)), 'Natal', 'Recife', 292.80),
(4, DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -2, DATEADD('HOUR', 12, CURRENT_TIMESTAMP)), 'Porto Alegre', 'Florianopolis', 470.20);

INSERT INTO maintenance_schedules (vehicle_id, start_date, expected_end_date, service_type, estimated_cost, status) VALUES
(1, DATEADD('DAY', 3, CURRENT_DATE), DATEADD('DAY', 4, CURRENT_DATE), 'OLEO', 350.00, 'PENDENTE'),
(2, DATEADD('DAY', 5, CURRENT_DATE), DATEADD('DAY', 7, CURRENT_DATE), 'FREIOS', 1500.00, 'PENDENTE'),
(3, DATEADD('DAY', 8, CURRENT_DATE), DATEADD('DAY', 8, CURRENT_DATE), 'TROCA_DE_PNEUS', 2200.00, 'EM_REALIZACAO'),
(4, DATEADD('DAY', 12, CURRENT_DATE), DATEADD('DAY', 14, CURRENT_DATE), 'REVISAO_GERAL', 4200.00, 'PENDENTE'),
(2, DATEADD('DAY', 16, CURRENT_DATE), DATEADD('DAY', 18, CURRENT_DATE), 'MOTOR', 9800.00, 'PENDENTE'),
(1, DATEADD('DAY', -15, CURRENT_DATE), DATEADD('DAY', -14, CURRENT_DATE), 'OLEO', 330.00, 'CONCLUIDA');
