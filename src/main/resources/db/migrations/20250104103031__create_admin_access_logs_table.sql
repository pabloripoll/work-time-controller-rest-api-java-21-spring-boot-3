CREATE TABLE IF NOT EXISTS "admin_access_logs" (
	"id" BIGSERIAL NOT NULL,
	"user_id" BIGINT NOT NULL,
	"is_terminated" BOOLEAN NOT NULL DEFAULT false,
	"is_expired" BOOLEAN NOT NULL DEFAULT false,
	"expires_at" TIMESTAMP NOT NULL,
	"refresh_count" INTEGER NOT NULL DEFAULT 0,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	"ip_address" VARCHAR(45) NULL DEFAULT NULL::character varying,
	"user_agent" TEXT NULL DEFAULT NULL,
	"requests_count" INTEGER NOT NULL DEFAULT 0,
	"payload" JSON NULL DEFAULT NULL,
	"token" TEXT NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_admin_access_logs_user_id" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_admin_access_logs_user_id ON "admin_access_logs" ("user_id");
CREATE INDEX IF NOT EXISTS idx_admin_access_logs_expires_at ON "admin_access_logs" ("expires_at");
CREATE INDEX IF NOT EXISTS idx_admin_access_logs_token ON "admin_access_logs" ("token");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_admin_access_log_token;
-- rollback DROP INDEX IF EXISTS idx_admin_access_logs_expires_at;
-- rollback DROP INDEX IF EXISTS idx_admin_access_logs_token;
-- rollback DROP TABLE IF EXISTS "admin_access_logs" CASCADE;