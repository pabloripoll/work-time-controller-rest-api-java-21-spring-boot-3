CREATE TABLE IF NOT EXISTS "employment_workdays_logs" (
	"id" BIGSERIAL NOT NULL,
	"workday_id" BIGINT NOT NULL,
	"admin_id" BIGINT NULL DEFAULT NULL,
	"action" VARCHAR(128) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employment_workdays_logs_workday_id" FOREIGN KEY ("workday_id") REFERENCES "employment_workdays" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employment_workdays_logs_admin_id" FOREIGN KEY ("admin_id") REFERENCES "admins" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_employment_workdays_logs_workday_id ON "employment_workdays_logs" ("workday_id");
CREATE INDEX IF NOT EXISTS idx_employment_workdays_logs_admin_id ON "employment_workdays_logs" ("admin_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_employment_workdays_logs_admin_id;
-- rollback DROP INDEX IF EXISTS idx_employment_workdays_logs_workday_id;
-- rollback DROP TABLE IF EXISTS "employment_workdays_logs" CASCADE;