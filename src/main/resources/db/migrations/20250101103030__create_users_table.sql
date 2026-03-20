CREATE TABLE IF NOT EXISTS "users" (
	"id" BIGSERIAL NOT NULL,
	"role" VARCHAR(32) NOT NULL,
	"email" VARCHAR(64) NOT NULL,
	"password" VARCHAR(256) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	"deleted_at" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	PRIMARY KEY ("id"),
	UNIQUE ("email")
);

CREATE INDEX IF NOT EXISTS idx_users_role ON "users" ("role");
CREATE INDEX IF NOT EXISTS idx_users_created_at ON "users" ("created_at");
CREATE INDEX IF NOT EXISTS idx_users_deleted_at ON "users" ("deleted_at");