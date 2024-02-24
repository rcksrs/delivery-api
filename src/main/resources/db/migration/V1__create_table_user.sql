CREATE TABLE "user" (
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
	email text NULL,
	"name" text NOT NULL,
	"password" text NULL,
	phone text NOT NULL,
	"role" text NOT NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);