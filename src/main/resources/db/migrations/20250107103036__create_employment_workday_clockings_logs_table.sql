CREATE TABLE IF NOT EXISTS "employment_workday_clockings_logs" (
	"id" BIGSERIAL NOT NULL,
	"clocking_id" BIGINT NOT NULL,
	"admin_id" BIGINT NULL DEFAULT NULL,
	"action" VARCHAR(128) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employment_workday_clockings_logs_clocking_id" FOREIGN KEY ("clocking_id") REFERENCES "employment_workday_clockings" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employment_workday_clockings_logs_admin_id" FOREIGN KEY ("admin_id") REFERENCES "admins" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_employment_workday_clockings_logs_clocking_id ON "employment_workday_clockings_logs" ("clocking_id");
CREATE INDEX IF NOT EXISTS idx_employment_workday_clockings_logs_admin_id ON "employment_workday_clockings_logs" ("admin_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_employment_workday_clockings_logs_admin_id;
-- rollback DROP INDEX IF EXISTS idx_employment_workday_clockings_logs_clocking_id;
-- rollback DROP TABLE IF EXISTS "employment_workday_clockings_logs" CASCADE;