-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
-- Create sequence for generating unique identifiers
CREATE SEQUENCE common_sequence
    START WITH 1
    INCREMENT BY 1;

-- Create the 'phones' table
CREATE TABLE phones
(
    id BIGINT NOT NULL PRIMARY KEY,
    number VARCHAR(12),
    client_id BIGINT
);

-- Create the 'addresses' table
CREATE TABLE addresses
(
    id BIGINT NOT NULL PRIMARY KEY,
    street VARCHAR(150)
);

-- Create the 'clients' table
CREATE TABLE clients
(
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    phone_id BIGINT,
    address_id BIGINT
);

-- Add foreign key constraints
ALTER TABLE clients
    ADD CONSTRAINT fk_client_phone FOREIGN KEY (phone_id) REFERENCES phones (id) ON DELETE CASCADE;

ALTER TABLE clients
    ADD CONSTRAINT fk_client_address FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE;

ALTER TABLE phones
    ADD CONSTRAINT fk_phone_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE;