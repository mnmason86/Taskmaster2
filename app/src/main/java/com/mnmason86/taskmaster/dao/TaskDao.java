package com.mnmason86.taskmaster.dao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mnmason86.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    public void insertTask(Task task);

    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT * FROM Task WHERE taskState = :taskState")
    public List<Task> findAllByState(Task.TaskStateEnum taskState);

    @Query("SELECT * FROM Task WHERE id = :id")
    Task findByTaskId(Long id);

    //Order by ascending

    @Query("SELECT * FROM Task ORDER BY dateCreated ASC")
    public List<Task> findAllSortedByDate();

}
