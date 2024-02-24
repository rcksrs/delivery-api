CREATE TABLE "order" (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NOT NULL,
	modified_at timestamp NULL,
	description text NOT NULL,
	price float8 NOT NULL,
	quantity int8 NOT NULL,
	status text NOT NULL,
	store_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT order_pkey PRIMARY KEY (id),
	CONSTRAINT fk_order_store_id FOREIGN KEY (store_id) REFERENCES store(id),
	CONSTRAINT fk_order_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);