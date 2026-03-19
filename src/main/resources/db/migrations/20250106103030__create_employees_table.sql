CREATE TABLE IF NOT EXISTS "employees" (
	"id" BIGSERIAL NOT NULL,
	"user_id" BIGINT NOT NULL,
	"uuid" UUID NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT false,
	"is_banned" BOOLEAN NOT NULL DEFAULT false,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("uuid"),
	CONSTRAINT "fk_employees_user_id" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_employees_user_id ON "employees" ("user_id");