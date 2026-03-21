CREATE TABLE IF NOT EXISTS "employee_location" (
	"id" BIGSERIAL NOT NULL,
	"employee_id" BIGINT NOT NULL,
	"continent_id" BIGINT NULL DEFAULT NULL,
	"zone_id" BIGINT NULL DEFAULT NULL,
	"country_id" BIGINT NULL DEFAULT NULL,
	"region_id" BIGINT NULL DEFAULT NULL,
	"state_id" BIGINT NULL DEFAULT NULL,
	"district_id" BIGINT NULL DEFAULT NULL,
	"city_id" BIGINT NULL DEFAULT NULL,
	"suburb_id" BIGINT NULL DEFAULT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	"address" VARCHAR(128) NULL DEFAULT NULL::character varying,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk_employee_location_employee_id" FOREIGN KEY ("employee_id") REFERENCES "employees" ("id") ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT "fk_employee_location_continent_id" FOREIGN KEY ("continent_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_region_id" FOREIGN KEY ("region_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_zone_id" FOREIGN KEY ("zone_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_country_id" FOREIGN KEY ("country_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_state_id" FOREIGN KEY ("state_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_district_id" FOREIGN KEY ("district_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_city_id" FOREIGN KEY ("city_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL,
	CONSTRAINT "fk_employee_location_suburb_id" FOREIGN KEY ("suburb_id") REFERENCES "locations" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_employee_location_employee_id ON "employee_location" ("employee_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_continent_id ON "employee_location" ("continent_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_zone_id ON "employee_location" ("zone_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_country_id ON "employee_location" ("country_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_region_id ON "employee_location" ("region_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_state_id ON "employee_location" ("state_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_district_id ON "employee_location" ("district_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_city_id ON "employee_location" ("city_id");
CREATE INDEX IF NOT EXISTS idx_employee_location_suburb_id ON "employee_location" ("suburb_id");

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP INDEX IF EXISTS idx_employee_location_employee_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_continent_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_zone_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_country_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_region_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_state_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_district_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_city_id;
-- rollback DROP INDEX IF EXISTS idx_employee_location_suburb_id;
-- rollback DROP TABLE IF EXISTS "employee_location" CASCADE;