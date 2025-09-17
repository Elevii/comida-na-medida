package com.elevii.comidanamedida.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.elevii.comidanamedida.data.local.FoodSeeds

object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE CookedFoodMeasurementEntity ADD COLUMN 'quantityDays' INTEGER NOT NULL DEFAULT 0 ")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            val cursor = db.query("SELECT COUNT(*) FROM FoodEntity")
            if (cursor.moveToFirst()) {
                val count = cursor.getInt(0)
                if (count == 0) {
                    val sql = """
                        INSERT INTO FoodEntity (uuid, name, cookingIndex)
                        SELECT ?, ?, ?
                        WHERE NOT EXISTS (SELECT 1 FROM FoodEntity WHERE uuid = ?)
                    """.trimIndent()
                    FoodSeeds.getAll().forEach { (uuid, name, cookingIndex) ->
                        db.execSQL(sql, arrayOf(uuid, name, cookingIndex, uuid))
                    }
                }
            }
            cursor.close()
        }
    }
}
