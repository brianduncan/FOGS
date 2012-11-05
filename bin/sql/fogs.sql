CREATE TABLE company 
(
facebook_id int NOT NULL, 
name varchar(255) NOT NULL, 
CONSTRAINT pk_company_facebook_id PRIMARY KEY (facebook_id)
);

CREATE TABLE product
(
facebook_id int NOT NULL,
name varchar(255) NOT NULL,
company_id int NOT NULL,
CONSTRAINT pk_product_facebook_id PRIMARY KEY (facebook_id)
);

ALTER TABLE product
ADD CONSTRAINT fk_product_refs_company
FOREIGN KEY (company_id) REFERENCES company(facebook_id) 
ON DELETE CASCADE;

CREATE TABLE users
(
facebook_id int NOT NULL,
login varchar(255) NOT NULL,
CONSTRAINT pk_users_facebook_id PRIMARY KEY (facebook_id)
);

CREATE TABLE views
(
user_id int NOT NULL,
product_id int NOT NULL,
view_count int NOT NULL,
CONSTRAINT pk_userid_productid PRIMARY KEY (user_id, product_id)
);

ALTER TABLE views
ADD CONSTRAINT fk_userid_refs_usersfacebookid
FOREIGN KEY (user_id) REFERENCES users(facebook_id)
ON DELETE CASCADE;

ALTER TABLE views
ADD CONSTRAINT fk_productid_refs_productfacebookid
FOREIGN KEY (product_id) REFERENCES product(facebook_id)
ON DELETE CASCADE;