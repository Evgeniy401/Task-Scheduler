package com.example.data.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.storage.PriorityDataConverter
import com.example.data.storage.dao.StatisticDao
import com.example.data.storage.dao.TaskDao
import com.example.data.storage.entity.StatisticEntity
import com.example.data.storage.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, StatisticEntity::class],
    version = 5,
    exportSchema = true
)
@TypeConverters(PriorityDataConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun statisticDao(): StatisticDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS statistics (
                        id INTEGER PRIMARY KEY NOT NULL,
                        totalCreated INTEGER NOT NULL DEFAULT 0,
                        totalCompleted INTEGER NOT NULL DEFAULT 0,
                        totalDeleted INTEGER NOT NULL DEFAULT 0
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    "INSERT OR IGNORE INTO statistics (id) VALUES (1)"
                )
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    """
                    CREATE TABLE tasks_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        title TEXT NOT NULL,
                        body TEXT NOT NULL,
                        priority TEXT NOT NULL,
                        isCompleted INTEGER NOT NULL DEFAULT 0,
                        isDeleted INTEGER NOT NULL DEFAULT 0,
                        lastModified INTEGER NOT NULL DEFAULT 0
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    INSERT INTO tasks_new (id, title, body, priority, isCompleted, isDeleted, lastModified)
                    SELECT 
                        id, 
                        title, 
                        body, 
                        priority, 
                        isCompleted, 
                        isDeleted,
                        strftime('%s', 'now') * 1000 AS lastModified
                    FROM tasks
                    """.trimIndent()
                )

                db.execSQL("DROP TABLE tasks")
                db.execSQL("ALTER TABLE tasks_new RENAME TO tasks")
            }
        }

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                                context.applicationContext,
                                TaskDatabase::class.java,
                                "task_database"
                            )
                                .addMigrations(MIGRATION_3_4, MIGRATION_4_5)
                    .fallbackToDestructiveMigrationOnDowngrade(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}