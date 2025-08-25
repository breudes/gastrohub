CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    preferred_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    user_role VARCHAR(20),
    address TEXT,
    last_update_date TIMESTAMP,
    active BOOLEAN
);

CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    id_user INT REFERENCES users(id),
    active BOOLEAN,
    document VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS owners (
    id SERIAL PRIMARY KEY,
    id_user INT REFERENCES users(id),
    active BOOLEAN,
    document VARCHAR(20)
);
-- Inserting test users
INSERT INTO users (name, preferred_name, email, username, password, user_role, address, last_update_date, active)
VALUES
  ('Admin User', 'Admin', 'admin@example.com', 'admin', '$2a$12$VaaJlrjOtB0m.gNMib6sQe1Eyq.ykgOoFW4SZ0heD2VaYRPd50WIa', 'ADMIN', NULL, CURRENT_TIMESTAMP, TRUE),
  ('Client User', 'Client', 'client@example.com', 'client', '$2a$12$cfLfEGGa77e15LZPK8oJ7OHkcg8HEMix//0xVZI/gg4d7ijawaf96', 'CLIENT', NULL, CURRENT_TIMESTAMP, TRUE),
  ('Owner User', 'Owner', 'owner@example.com', 'owner', '$2a$12$qfPIlFexdjLmnKpPizpEKurlBkSJrMsHzMLqAXRzQ5bwBIPOmqqGO', 'OWNER', NULL, CURRENT_TIMESTAMP, TRUE);

-- Relating entity client to user with username 'client'
INSERT INTO clients (id_user, active, document)
SELECT id, TRUE, '12345678900'
FROM users
WHERE username = 'client';

-- Relating entity owner to user with username 'owner'
INSERT INTO owners (id_user, active, document)
SELECT id, TRUE, '98765432100'
FROM users
WHERE username = 'owner';