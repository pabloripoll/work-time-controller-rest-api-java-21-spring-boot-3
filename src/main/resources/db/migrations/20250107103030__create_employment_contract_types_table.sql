CREATE TABLE IF NOT EXISTS "employment_contract_types" (
	"id" BIGSERIAL NOT NULL,
	"title" VARCHAR(64) NULL DEFAULT NULL::character varying,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	"deleted_at" TIMESTAMP NULL DEFAULT NULL::timestamp without time zone,
	PRIMARY KEY ("id")
);

CREATE INDEX IF NOT EXISTS idx_employment_contract_types_title ON "employment_contract_types" ("title");