-- Create User Database
CREATE DATABASE user_db;

-- Create Booking Database
CREATE DATABASE booking_db;

-- Create Payment Database
CREATE DATABASE payment_db;

-- Create Partner Database
CREATE DATABASE partner_db;

-- Create Catalog Database
CREATE DATABASE catalog_db;

GRANT ALL PRIVILEGES ON DATABASE user_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE booking_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE payment_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE partner_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE catalog_db TO postgres;
