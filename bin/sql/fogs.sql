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



