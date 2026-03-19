CREATE TABLE IF NOT EXISTS "employment_contracts_logs" (
	"id" BIGSERIAL NOT NULL,
	"contract_id" BIGINT NULL DEFAULT NULL,
	"admin_id" BIGINT NULL DEFAULT NULL,
	"action" VARCHAR(128) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employment_contracts_logs_contract_id" FOREIGN KEY ("contract_id") REFERENCES "employment_contracts" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employment_contracts_logs_admin_id" FOREIGN KEY ("admin_id") REFERENCES "admins" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_employment_contracts_logs_contract_id ON "employment_contracts_logs" ("contract_id");
CREATE INDEX IF NOT EXISTS idx_employment_contracts_logs_admin_id ON "employment_contracts_logs" ("admin_id");