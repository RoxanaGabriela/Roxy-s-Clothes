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
	type				VARCHAR(50)		NOT NULL DEFAULT 'client',
    notified			INT(4)			NOT NULL DEFAULT '0'
);
ALTER TABLE user ADD CONSTRAINT user_ck_email CHECK (email LIKE '%@%.%');
ALTER TABLE user ADD CONSTRAINT user_ck_type_possible_values CHECK (type in ('administrator', 'client'));

CREATE TABLE IF NOT EXISTS address (
    id                  INT(10)         UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    address             VARCHAR(100)     NOT NULL,
    user_id             INT(10)         UNSIGNED NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product (
    id                  INT(10)         UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                VARCHAR(50)     NOT NULL,
    date                DATE            NOT NULL,
    price               FLOAT(6, 2)     UNSIGNED NOT NULL DEFAULT '0',
    currency            VARCHAR(10)     NOT NULL,
    producer            VARCHAR(50)     NOT NULL,
    category            VARCHAR(50)     NOT NULL,
    color               VARCHAR(50)     NOT NULL,
    description         VARCHAR(500)    NOT NULL,
    picture             VARCHAR(500)    NOT NULL
);
ALTER TABLE product ADD CONSTRAINT product_ck_price_should_be_positive CHECK (price>0);
ALTER TABLE product ADD CONSTRAINT product_ck_category_possible_values CHECK (type in ('pantaloni', 'bluze', 'tricouri', 'fuste', 'rochii', 'jachete'));
ALTER TABLE product ADD CONSTRAINT product_ck_date_should_be_in_past CHECK (date<= CURDATE());

CREATE TABLE IF NOT EXISTS size (
    id                  INT(10)         UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    size                VARCHAR(50)     NOT NULL,
    stockpile           INT(4)          NOT NULL DEFAULT '0',
    product_id          INT(10)         UNSIGNED NOT NULL,
    
    FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE size ADD CONSTRAINT size_ck_stockpile_should_be_positive CHECK (stockpile>0);
ALTER TABLE size ADD CONSTRAINT size_ck_size_possible_values CHECK (type in ('S', 'M', 'L', 'XL', 'XXL'));


CREATE TABLE IF NOT EXISTS fabric (
    id                  INT(10)         UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                VARCHAR(50)     NOT NULL,
    percent             INT(4)          NOT NULL DEFAULT '0',
    product_id          INT(10)         UNSIGNED NOT NULL,
    
    FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE fabric ADD CONSTRAINT fabric_ck_percent_should_be_positive CHECK (percent>0 AND percent<=100);

CREATE TABLE IF NOT EXISTS invoice (
    id                  INT(10)         UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id             INT(10)         UNSIGNED NOT NULL,
    address_id          INT(10)         UNSIGNED NOT NULL,
    total_price         INT(4)          NOT NULL DEFAULT '0',
    date                DATE            NOT NULL,
    issued				BOOLEAN			NOT NULL DEFAULT '0',
    
    FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES address(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE invoice ADD CONSTRAINT invoice_ck_total_price_should_be_positive CHECK (total_price>0);
ALTER TABLE invoice ADD CONSTRAINT invoice_ck_date_should_be_in_past CHECK (date<= CURDATE());

CREATE TABLE IF NOT EXISTS invoice_details (
    id                  INT(10)         UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    invoice_id          INT(10)         UNSIGNED NOT NULL,
    product_id          INT(10)         UNSIGNED NOT NULL,
    quantity            INT(4)          NOT NULL DEFAULT '0',
    size                VARCHAR(50)     NOT NULL,
    
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE invoice_details ADD CONSTRAINT invoice_details_ck_quantity_should_be_positive CHECK (quantity>0);
ALTER TABLE invoice_details ADD CONSTRAINT invoice_details_ck_size_possible_values CHECK (type in ('S', 'M', 'L', 'XL', 'XXL'));

INSERT INTO product (name, date, price, currency, producer, category, color, description, picture) VALUES ('TOP Z DANA PINK STYLE', CURDATE(), '45.00', 'LEI', 'Z', 'tricouri', 'roz', 'Top Z roz, se inchide cu capse la spate.Spalare la masina max. 30°C.', './images/shop/top_z_dana_pink_style.png');
INSERT INTO size (size, stockpile, product_id) VALUES ('S', '20', '1');
INSERT INTO size (size, stockpile, product_id) VALUES ('M', '20', '1');
INSERT INTO size (size, stockpile, product_id) VALUES ('L', '20', '1');
INSERT INTO size (size, stockpile, product_id) VALUES ('XL', '20', '1');
INSERT INTO fabric (name, percent, product_id) VALUES ('Poliester', '51', '1');
INSERT INTO fabric (name, percent, product_id) VALUES ('Nailon', '49', '1');

INSERT INTO product (name, date, price, currency, producer, category, color, description, picture) VALUES ('TOP STR DAMA TABITA TOUCAN BLACK', CURDATE(), '35.00', 'LEI', 'STR', 'tricouri', 'negru', 'Top Str femei, cu inchidere fermoar la spate. Spalare la masina max. 30° Delicate', './images/shop/top_str_dama_black.png');
INSERT INTO size (size, stockpile, product_id) VALUES ('S', '20', '2');
INSERT INTO size (size, stockpile, product_id) VALUES ('M', '20', '2');
INSERT INTO size (size, stockpile, product_id) VALUES ('L', '20', '2');
INSERT INTO size (size, stockpile, product_id) VALUES ('XL', '20', '2');
INSERT INTO fabric (name, percent, product_id) VALUES ('Polioamida', '20', '2');
INSERT INTO fabric (name, percent, product_id) VALUES ('Elastan', '5', '2');
INSERT INTO fabric (name, percent, product_id) VALUES ('Viscosa', '45', '2');
INSERT INTO fabric (name, percent, product_id) VALUES ('Poliester', '30', '2');

INSERT INTO user (first_name, last_name, phone_number, email, username, password, type) VALUES ('Andreea', 'Dragomir', '0724321412', 'andreea.dragomir@gmail.com', 'andreead', '-', 'client');