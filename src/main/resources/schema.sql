CREATE TABLE "player_info" (
    "uuid" UUID PRIMARY KEY,
    "nickname" VARCHAR(50) NOT NULL UNIQUE,
    "money" BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE "backpack" (
    "player_uuid" UUID PRIMARY KEY,
    "item_data" LONGTEXT NOT NULL
    --FOREIGN KEY ("player_uuid") REFERENCES "player_info"("uuid") ON DELETE CASCADE
);