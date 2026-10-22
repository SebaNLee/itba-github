-- Create databases for microservices
CREATE DATABASE IF NOT EXISTS saga_sale;
CREATE DATABASE IF NOT EXISTS saga_inventory;
CREATE DATABASE IF NOT EXISTS saga_payment;

-- Grant all privileges to root user on all databases
GRANT ALL PRIVILEGES ON saga_sale.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON saga_inventory.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON saga_payment.* TO 'root'@'%';

FLUSH PRIVILEGES;