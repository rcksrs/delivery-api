CREATE TABLE store (
	id bigserial NOT NULL,
	active bool NOT NULL,
	created_at timestamp NOT NULL,
	modified_at timestamp NULL,
	city text NOT NULL,
	country text NOT NULL,
	"number" text NOT NULL,
	state text NOT NULL,
	street text NOT NULL,
	zip_code text NOT NULL,
	description text NULL,
	email text NULL,
	"name" text NOT NULL,
	phone text NOT NULL,
	CONSTRAINT store_pkey PRIMARY KEY (id)
);