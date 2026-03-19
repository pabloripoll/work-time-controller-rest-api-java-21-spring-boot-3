#!/bin/bash

# Define paths
MIGRATIONS_DIR="./src/main/resources/db/migrations"
CHANGELOG_DIR="./src/main/resources/db/changelog"
OUTPUT_FILE="${CHANGELOG_DIR}/db.changelog-master.yaml"

# Ensure the changelog directory exists
mkdir -p "$CHANGELOG_DIR"

# Initialize the YAML file
echo "databaseChangeLog:" > "$OUTPUT_FILE"

# Counter for the ID
COUNTER=1

# Loop through all .sql files in alphabetical/chronological order
for FILEPATH in $(ls -1 "$MIGRATIONS_DIR"/*.sql | sort); do
    # 1. Extract just the filename (e.g., V20250101103030__create_users_table.sql)
    FILENAME=$(basename "$FILEPATH")
    # 2. Strip everything after the '__' (leaves: V20250101103030)
    PREFIX="${FILENAME%%__*}"
    # 3. Strip the leading 'V' (leaves: 20250101103030)
    CHANGESET_ID="${PREFIX#V}"

    # Append the changeSet block to the YAML file
    # Note: The indentation spaces below are carefully aligned for valid YAML
    # Choose between: ${COUNTER} or the recommeded ${CHANGESET_ID}
    cat >> "$OUTPUT_FILE" <<EOF
  - changeSet:
      id: ${CHANGESET_ID}
      author: developer
      changes:
        - sqlFile:
            path: db/migrations/${FILENAME}
            relativeToChangelogFile: false
            splitStatements: true
            endDelimiter: ';'
EOF

    # Increment the counter
    ((COUNTER++))
done

echo "Successfully generated Liquibase changelog at: $OUTPUT_FILE"
echo "Found and mapped $((COUNTER-1)) migration files."