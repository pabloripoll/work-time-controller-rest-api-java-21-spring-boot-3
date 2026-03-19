CREATE TABLE IF NOT EXISTS "employee_profile" (
	"id" BIGSERIAL NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"name" VARCHAR(64) NOT NULL,
	"surname" VARCHAR(64) NOT NULL,
	"birthdate" DATE NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("name", "surname"),
	CONSTRAINT "fk_employee_profile" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_employee_profile_employee_id ON "employee_profile" ("employee_id");