CREATE TABLE IF NOT EXISTS "employee_workplace" (
	"id" BIGSERIAL NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"department_id" BIGINT NULL DEFAULT NULL,
	"job_id" BIGINT NULL DEFAULT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employee_workplace_employee_id" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employee_workplace_department_id" FOREIGN KEY ("department_id") REFERENCES "office_departments" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_workplace_job_id" FOREIGN KEY ("job_id") REFERENCES "office_jobs" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_employee_workplace_employee_id ON "employee_workplace" ("employee_id");
CREATE INDEX IF NOT EXISTS idx_employee_workplace_department_id ON "employee_workplace" ("department_id");
CREATE INDEX IF NOT EXISTS idx_employee_workplace_job_id ON "employee_workplace" ("job_id");