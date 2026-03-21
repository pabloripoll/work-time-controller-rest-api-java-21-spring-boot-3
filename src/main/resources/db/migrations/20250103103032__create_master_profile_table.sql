CREATE TABLE IF NOT EXISTS "master_profile" (
	"id" BIGSERIAL NOT NULL,
	"master_id" BIGINT NOT NULL,
	"nickname" VARCHAR(64) NOT NULL,
	"avatar" TEXT NULL DEFAULT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_master_profile_master_id" FOREIGN KEY ("master_id") REFERENCES "masters" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_master_profile_master_id ON "master_profile" ("master_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_master_profile_master_id;
-- rollback DROP TABLE IF EXISTS "master_profile" CASCADE;