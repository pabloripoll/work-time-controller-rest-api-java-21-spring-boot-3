CREATE TABLE IF NOT EXISTS "admin_profile" (
	"id" BIGSERIAL NOT NULL,
	"admin_id" BIGINT NOT NULL,
	"nickname" VARCHAR(64) NOT NULL,
	"avatar" TEXT NULL DEFAULT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_admin_profile_admin_id" FOREIGN KEY ("admin_id") REFERENCES "admins" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_admin_profile_admin_id ON "admin_profile" ("admin_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_admin_profile_admin_id;
-- rollback DROP TABLE IF EXISTS "admin_profile" CASCADE;