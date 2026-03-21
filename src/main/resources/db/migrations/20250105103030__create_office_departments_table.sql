CREATE TABLE IF NOT EXISTS "office_departments" (
	"id" BIGSERIAL NOT NULL,
	"name" VARCHAR(64) NOT NULL,
	"description" VARCHAR(256) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("name")
);

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP TABLE IF EXISTS "office_departments" CASCADE;