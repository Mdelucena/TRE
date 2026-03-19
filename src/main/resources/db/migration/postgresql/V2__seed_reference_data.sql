INSERT INTO vehicles (plate, model, category) VALUES
('ABC-1234', 'Fiat Fiorino', 'LEVE'),
('XYZ-9876', 'Volvo FH', 'PESADO')
ON CONFLICT (plate) DO NOTHING;
