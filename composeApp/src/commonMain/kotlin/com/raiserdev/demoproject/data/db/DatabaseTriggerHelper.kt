package com.raiserdev.demoproject.data.db

import app.cash.sqldelight.db.SqlDriver

fun createTriggers(driver: SqlDriver) {
    driver.execute(null, """
        CREATE TRIGGER IF NOT EXISTS increase_label_count
        AFTER INSERT ON notas
        WHEN NEW.label_id IS NOT NULL
        BEGIN
            UPDATE labels SET count = count + 1 WHERE id = NEW.label_id;
        END;
    """.trimIndent(), 0)

    driver.execute(null, """
        CREATE TRIGGER IF NOT EXISTS decrease_label_count
        AFTER DELETE ON notas
        WHEN OLD.label_id IS NOT NULL
        BEGIN
            UPDATE labels SET count = count - 1 WHERE id = OLD.label_id;
        END;
    """.trimIndent(), 0)

    driver.execute(null, """
        CREATE TRIGGER IF NOT EXISTS update_label_count_on_update
        AFTER UPDATE OF label_id ON notas
        WHEN OLD.label_id IS NOT NEW.label_id
        BEGIN
            UPDATE labels SET count = count - 1 WHERE id = OLD.label_id;
            UPDATE labels SET count = count + 1 WHERE id = NEW.label_id;
        END;
    """.trimIndent(), 0)
}