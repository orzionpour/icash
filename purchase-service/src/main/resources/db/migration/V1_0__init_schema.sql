CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE supermarket (
    id TEXT PRIMARY KEY
);

CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid()
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    CONSTRAINT unique_product_name UNIQUE (name)
);

CREATE TABLE purchase (
    id SERIAL PRIMARY KEY,
    supermarket_id TEXT REFERENCES supermarket(id) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    user_id UUID REFERENCES "user"(id) NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL
);

CREATE TABLE purchase_products (
    purchase_id INT REFERENCES purchase(id) NOT NULL, 
    product_id INT REFERENCES product(id) NOT NULL,
    PRIMARY KEY (purchase_id, product_id)
);