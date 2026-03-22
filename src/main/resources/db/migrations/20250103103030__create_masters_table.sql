CREATE TABLE IF NOT EXISTS "masters" (
	"id" BIGSERIAL NOT NULL,
	"user_id" BIGINT NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT false,
	"is_banned" BOOLEAN NOT NULL DEFAULT false,
	"is_supermaster" BOOLEAN NOT NULL DEFAULT false,
	"created_by_user_id" BIGINT,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_masters_user_id" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_masters_user_id ON "masters" ("user_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_masters_user_id;
-- rollback DROP TABLE IF EXISTS "masters" CASCADE;