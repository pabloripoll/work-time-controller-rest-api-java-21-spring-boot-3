CREATE TABLE IF NOT EXISTS "employment_workdays" (
	"id" BIGSERIAL NOT NULL,
	"contract_id" BIGINT NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"starts_date" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	"ends_date" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	"hours_extra" TIME NULL DEFAULT NULL::time without time zone,
	"hours_made" TIME NULL DEFAULT NULL::time without time zone,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	"deleted_at" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employment_workdays_contract_id" FOREIGN KEY ("contract_id") REFERENCES "employment_contracts" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employment_workdays_employee_id" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_employment_workdays_starts_date ON "employment_workdays" ("starts_date");
CREATE INDEX IF NOT EXISTS idx_employment_workdays_contract_id ON "employment_workdays" ("contract_id");
CREATE INDEX IF NOT EXISTS idx_employment_workdays_employee_id ON "employment_workdays" ("employee_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_employment_workdays_employee_id;
-- rollback DROP INDEX IF EXISTS idx_employment_workdays_contract_id;
-- rollback DROP INDEX IF EXISTS idx_employment_workdays_starts_date;
-- rollback DROP TABLE IF EXISTS "employment_workdays" CASCADE;