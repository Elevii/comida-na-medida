package com.elevii.comidanamedida.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE CookedFoodMeasurementEntity ADD COLUMN 'quantityDays' INTEGER NOT NULL DEFAULT 0 ")
        }

    }
}
