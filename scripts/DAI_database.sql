DROP DATABASE IF EXISTS Roxys_Clothes;
CREATE DATABASE IF NOT EXISTS Roxys_Clothes;

USE Roxys_Clothes;

CREATE TABLE IF NOT EXISTS user (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	first_name			VARCHAR(50)		NOT NULL,
    last_name			VARCHAR(50)		NOT NULL,
    phone_number		INT(10)			UNSIGNED NOT NULL,
    email				VARCHAR(60)		NOT NULL,
    username			VARCHAR(30)		NOT NULL,
    password			VARCHAR(30)		NOT NULL,
	type				VARCHAR(50)		NOT NULL,
    notified			INT(4)			NOT NULL DEFAULT '0'
);
ALTER TABLE user ADD CONSTRAINT user_ck_email CHECK (email LIKE '%@%.%');
ALTER TABLE user ADD CONSTRAINT user_ck_type_possible_values CHECK (type in ('administrator', 'client'));

CREATE TABLE IF NOT EXISTS address (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	judet				VARCHAR(50)		NOT NULL,
    oras				VARCHAR(50)		NOT NULL,
    strada				VARCHAR(50)		NOT NULL,
    cod_postal			INT(10)			UNSIGNED NOT NULL,
    user_id				INT(10)			UNSIGNED NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name				VARCHAR(50)		NOT NULL,
    date				DATE 			NOT NULL,
    price				FLOAT(6, 2)		UNSIGNED NOT NULL DEFAULT '0',
    producer			VARCHAR(50)		NOT NULL,
    category			VARCHAR(50)		NOT NULL,
    color				VARCHAR(50)		NOT NULL
);
ALTER TABLE product ADD CONSTRAINT product_ck_price_should_be_positive CHECK (price>0);
ALTER TABLE product ADD CONSTRAINT product_ck_category_possible_values CHECK (type in ('pantaloni', 'bluze', 'tricouri', 'fuste', 'rochii', 'jachete'));
ALTER TABLE product ADD CONSTRAINT product_ck_date_should_be_in_past CHECK (date<= CURDATE());

CREATE TABLE IF NOT EXISTS size (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    size				VARCHAR(50)		NOT NULL,
    stockpile			INT(4)			NOT NULL DEFAULT '0',
    product_id			INT(10)			UNSIGNED NOT NULL,
    
    FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE size ADD CONSTRAINT size_ck_stockpile_should_be_positive CHECK (stockpile>0);
ALTER TABLE size ADD CONSTRAINT size_ck_size_possible_values CHECK (type in ('S', 'M', 'L', 'XL', 'XXL'));


CREATE TABLE IF NOT EXISTS fabric (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name				VARCHAR(50)		NOT NULL,
    percent				INT(4)			NOT NULL DEFAULT '0',
    product_id			INT(10)			UNSIGNED NOT NULL,
    
    FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE fabric ADD CONSTRAINT fabric_ck_percent_should_be_positive CHECK (percent>0 AND percent<=100);

CREATE TABLE IF NOT EXISTS invoice (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id				INT(10)			UNSIGNED NOT NULL,
    total_price			INT(4)			NOT NULL DEFAULT '0',
    date				DATE 			NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE invoice ADD CONSTRAINT invoice_ck_total_price_should_be_positive CHECK (total_price>0);
ALTER TABLE invoice ADD CONSTRAINT invoice_ck_date_should_be_in_past CHECK (date<= CURDATE());

CREATE TABLE IF NOT EXISTS invoice_details (
	id					INT(10) 		UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    invoice_id			INT(10)			UNSIGNED NOT NULL,
    product_id			INT(10)			UNSIGNED NOT NULL,
    quantity			INT(4)			NOT NULL DEFAULT '0',
    size				VARCHAR(50)		NOT NULL,
    
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE invoice_details ADD CONSTRAINT invoice_details_ck_quantity_should_be_positive CHECK (quantity>0);
ALTER TABLE invoice_details ADD CONSTRAINT invoice_details_ck_size_possible_values CHECK (type in ('S', 'M', 'L', 'XL', 'XXL'));