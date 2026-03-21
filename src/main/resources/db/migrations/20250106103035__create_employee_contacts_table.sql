CREATE TABLE IF NOT EXISTS "employee_contacts" (
	"id" BIGSERIAL NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"postal" VARCHAR(64) NULL DEFAULT NULL::character varying,
	"email" VARCHAR(64) NULL DEFAULT NULL::character varying,
	"phone" VARCHAR(64) NULL DEFAULT NULL::character varying,
	"mobile" VARCHAR(64) NULL DEFAULT NULL::character varying,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employee_contacts" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_employee_contacts_employee_id ON "employee_contacts" ("employee_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_employee_contacts_employee_id;
-- rollback DROP TABLE IF EXISTS "employee_contacts" CASCADE;