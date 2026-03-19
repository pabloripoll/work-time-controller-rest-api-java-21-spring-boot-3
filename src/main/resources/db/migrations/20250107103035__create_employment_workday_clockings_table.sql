CREATE TABLE IF NOT EXISTS "employment_workday_clockings" (
	"id" BIGSERIAL NOT NULL,
	"workday_id" BIGINT NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"clock_in" BOOLEAN NOT NULL DEFAULT false,
	"clock_out" BOOLEAN NOT NULL DEFAULT false,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	"deleted_at" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employment_workday_clockings_workday_id" FOREIGN KEY ("workday_id") REFERENCES "employment_workdays" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employment_workday_clockings_employee_id" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_employment_workday_clockings_workday_id ON "employment_workday_clockings" ("workday_id");
CREATE INDEX IF NOT EXISTS idx_employment_workday_clockings_employee_id ON "employment_workday_clockings" ("employee_id");
