CREATE TYPE client_type AS (
  ip_address VARCHAR(255)
);

CREATE TABLE RefLink_Client (
  reflink_id BIGINT NOT NULL,
  client client_type NOT NULL
);

ALTER TABLE RefLink_Client ADD CONSTRAINT pk_RefLink_Client PRIMARY KEY (reflink_id, client);

ALTER TABLE RefLink_Client ADD CONSTRAINT fk_RefLink_Client_RefLink FOREIGN KEY (reflink_id) REFERENCES reflinks(id);