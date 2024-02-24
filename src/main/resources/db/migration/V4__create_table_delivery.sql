CREATE TABLE delivery (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NOT NULL,
	modified_at timestamp NULL,
	info text NULL,
	city text NOT NULL,
	country text NOT NULL,
	"number" text NOT NULL,
	state text NOT NULL,
	street text NOT NULL,
	zip_code text NOT NULL,
	status text NOT NULL,
	order_id int8 NOT NULL,
	CONSTRAINT delivery_pkey PRIMARY KEY (id),
	CONSTRAINT fk_delivery_order_id FOREIGN KEY (order_id) REFERENCES "order"(id)
);