CREATE TABLE IF NOT EXISTS "locations" (
	"id" BIGSERIAL NOT NULL,
	"is_continent" BOOLEAN NOT NULL DEFAULT false,
	"continent_id" BIGINT NULL DEFAULT NULL,
	"is_zone" BOOLEAN NOT NULL DEFAULT false,
	"zone_id" BIGINT NULL DEFAULT NULL,
	"is_country" BOOLEAN NOT NULL DEFAULT false,
	"country_id" BIGINT NULL DEFAULT NULL,
	"is_region" BOOLEAN NOT NULL DEFAULT false,
	"region_id" BIGINT NULL DEFAULT NULL,
	"is_state" BOOLEAN NOT NULL DEFAULT false,
	"state_id" BIGINT NULL DEFAULT NULL,
	"is_district" BOOLEAN NOT NULL DEFAULT false,
	"district_id" BIGINT NULL DEFAULT NULL,
	"is_city" BOOLEAN NOT NULL DEFAULT false,
	"city_id" BIGINT NULL DEFAULT NULL,
	"is_suburb" BOOLEAN NOT NULL DEFAULT false,
	"suburb_id" BIGINT NULL DEFAULT NULL,
	"slug" VARCHAR(128) NOT NULL,
	"name" VARCHAR(128) NOT NULL,
	"created_at" TIMESTAMP NOT NULL,
	"updated_at" TIMESTAMP NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("slug"),
	UNIQUE ("name")
);

-- -----------------------------------------------------------------------
-- ROLLBACK
-- -----------------------------------------------------------------------
-- rollback DROP TABLE IF EXISTS "locations" CASCADE;