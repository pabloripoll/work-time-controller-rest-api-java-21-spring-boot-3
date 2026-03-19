CREATE TABLE IF NOT EXISTS "employment_contracts" (
	"id" BIGSERIAL NOT NULL,
	"contract_type_id" BIGINT NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"admin_id" BIGINT NULL DEFAULT NULL,
	"days_per_month" INTEGER NOT NULL DEFAULT 0,
	"days_per_week" INTEGER NOT NULL DEFAULT 0,
	"hours_per_day" INTEGER NOT NULL DEFAULT 0,
	"has_contract_signed" BOOLEAN NOT NULL DEFAULT false,
	"has_grpd_signed" BOOLEAN NOT NULL DEFAULT false,
	"has_lopd_signed" BOOLEAN NOT NULL DEFAULT false,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	"deleted_at" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employment_contracts_contract_type__id" FOREIGN KEY ("contract_type_id") REFERENCES "employment_contract_types" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employment_contracts_employee_id" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employment_contracts_admin_id" FOREIGN KEY ("admin_id") REFERENCES "admins" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_employment_contracts_contract_type_id ON "employment_contracts" ("contract_type_id");
CREATE INDEX IF NOT EXISTS idx_employment_contracts_employee_id ON "employment_contracts" ("employee_id");
CREATE INDEX IF NOT EXISTS idx_employment_contracts_admin_id ON "employment_contracts" ("admin_id");