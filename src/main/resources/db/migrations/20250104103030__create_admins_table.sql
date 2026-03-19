CREATE TABLE IF NOT EXISTS "admins" (
	"id" BIGSERIAL NOT NULL,
	"user_id" BIGINT NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT false,
	"is_banned" BOOLEAN NOT NULL DEFAULT false,
	"is_superadmin" BOOLEAN NOT NULL DEFAULT false,
	"employee_id" BIGINT,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_admins_user_id" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_admins_user_id ON "admins" ("user_id");