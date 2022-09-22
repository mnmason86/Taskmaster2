package com.mnmason86.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.mnmason86.taskmaster.dao.TaskDao;
import com.mnmason86.taskmaster.models.Task;


@TypeConverters({ToDoDatabaseConverters.class})

@Database(entities = {Task.class}, version = 1)
//If version is changed, database is wiped. Create a migration first IRL. In class, ok to change.

public abstract class ToDoDatabase extends RoomDatabase {
    //add daos

    public abstract TaskDao taskDao();
}
