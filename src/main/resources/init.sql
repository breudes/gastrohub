-- Inserting test users
INSERT INTO users (name, preferred_name, email, username, password, user_role, address, last_update_date, active)
VALUES
  ('Admin User', 'Admin', 'admin@example.com', 'admin', '$2a$10$Xy1hVnLk5OyqTn8mQ0b1Le0Y3Zr/5DzEotdc1z93idBPq8ybj2Ynu', 'ADMIN', NULL, CURRENT_TIMESTAMP, TRUE),
  ('Client User', 'Client', 'client@example.com', 'client', '$2a$10$3q7HgN8Z3mZrj/H7hF4u7uYqj09fNqGi5DLkKT6uQf0aQjF4asJ6O', 'CLIENT', NULL, CURRENT_TIMESTAMP, TRUE),
  ('Owner User', 'Owner', 'owner@example.com', 'owner', '$2a$10$F8tL5EiO4Pd1J1eS3/OcTO3IrzQFqKa95eS0XYxs.4IzoqGwCHpE6', 'OWNER', NULL, CURRENT_TIMESTAMP, TRUE);

-- Relating entity client to user with username 'client'
INSERT INTO client (id_user, active, document)
SELECT id, TRUE, '12345678900'
FROM users
WHERE username = 'client';

-- Relating entity owner to user with username 'owner'
INSERT INTO owner (id_user, active, document)
SELECT id, TRUE, '98765432100'
FROM users
WHERE username = 'owner';