CREATE DATABASE shopapp;
USE shopapp; 

-- Role of user
CREATE TABLE roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- When user want to buy products => have to regist an account => user table
CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) DEFAULT "",
    phone_number VARCHAR(100) NOT NULL,
    address VARCHAR(200) DEFAULT "",
    password VARCHAR(100) NOT NULL DEFAULT "", -- encrypted password
    created_at TIMESTAMP,
    updated_at TIMESTAMP,  
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

ALTER TABLE users AUTO_INCREMENT = 1;

CREATE TABLE tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Support login with Facebook or Google
CREATE TABLE social_accounts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL COMMENT 'facebook or google',
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'email of user',
    name VARCHAR(100) NOT NULL COMMENT 'full name of user',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Category of products 
CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'name of category'
);

-- Products
CREATE TABLE products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) NOT NULL DEFAULT '' COMMENT 'name of product',
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE product_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    image_url VARCHAR(300) NOT NULL,
    CONSTRAINT fk_product_images_product_id 
        FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
); 

-- Orders
CREATE TABLE orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    full_name VARCHAR(100) NOT NULL DEFAULT '',
    email VARCHAR(100) NOT NULL DEFAULT '',
    phone_number VARCHAR(20) NOT NULL DEFAULT '',
    address VARCHAR(200) NOT NULL DEFAULT '',
    note VARCHAR(200) DEFAULT '',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') COMMENT 'status of order',
    total_price FLOAT CHECK (total_price >= 0),
    shipping_method VARCHAR(50),
    shipping_address VARCHAR(200),
    shipping_date DATE,
    tracking_number VARCHAR(50),
    payment_method VARCHAR(100),
    active TINYINT(1),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order details
CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    price FLOAT CHECK (price >= 0),
    number_of_products INT CHECK (number_of_products > 0),
    total_money FLOAT CHECK (total_money >= 0),
    color VARCHAR(20) DEFAULT '',
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);