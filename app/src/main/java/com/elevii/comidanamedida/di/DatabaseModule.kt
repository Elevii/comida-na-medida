package com.elevii.comidanamedida.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.elevii.comidanamedida.data.local.AppDatabase
import com.elevii.comidanamedida.data.local.FoodSeeds
import com.elevii.comidanamedida.data.local.dao.CookedFoodMeasurementDao
import com.elevii.comidanamedida.data.local.dao.FoodDao
import com.elevii.comidanamedida.data.local.migrations.Migrations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "comidanamedida.db"
        ).addMigrations(
            Migrations.MIGRATION_1_2,
            Migrations.MIGRATION_2_3
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val sql = """
                INSERT INTO FoodEntity (uuid, name, cookingIndex)
                SELECT ?, ?, ?
                WHERE NOT EXISTS (SELECT 1 FROM FoodEntity WHERE uuid = ?)
            """.trimIndent()
                FoodSeeds.getAll().forEach { (uuid, name, cookingIndex) ->
                    db.execSQL(sql, arrayOf(uuid, name, cookingIndex, uuid))
                }
            }
        }).build()
    }

    @Provides
    fun provideFoodDao(db: AppDatabase): FoodDao = db.foodDao()

    @Provides
    fun provideCookedFoodMeasurementDao(db: AppDatabase): CookedFoodMeasurementDao =
        db.cookedFoodMeasurementDao()
}
