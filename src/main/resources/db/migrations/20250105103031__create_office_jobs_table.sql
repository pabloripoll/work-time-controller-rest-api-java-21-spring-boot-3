CREATE TABLE IF NOT EXISTS "office_jobs" (
	"id" BIGSERIAL NOT NULL,
	"department_id" BIGINT NOT NULL,
	"title" VARCHAR(64) NOT NULL,
	"description" VARCHAR(256) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("title"),
	CONSTRAINT "fk_office_jobs_department_id" FOREIGN KEY ("department_id") REFERENCES "office_departments" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_office_jobs_department_id ON "office_jobs" ("department_id");