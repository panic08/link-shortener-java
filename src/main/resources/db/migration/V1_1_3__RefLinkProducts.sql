CREATE TYPE product_type AS (
    product VARCHAR(255),
    cost DOUBLE PRECISION,
    purchased DOUBLE PRECISION,
    receipt DOUBLE PRECISION
    );

CREATE TABLE RefLink_Product (
                                 reflink_id BIGINT NOT NULL,
                                 product product_type NOT NULL
);

ALTER TABLE RefLink_Product ADD CONSTRAINT pk_RefLink_Product PRIMARY KEY (reflink_id, product);

ALTER TABLE RefLink_Product ADD CONSTRAINT fk_RefLink_Product_RefLink FOREIGN KEY (reflink_id) REFERENCES RefLinks(id);