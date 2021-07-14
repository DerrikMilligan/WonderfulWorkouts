package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import wonderful.workouts.database.entities.WorkoutHistory;

@Dao
public interface WorkoutHistoryDao {
    @Insert
    long insert(WorkoutHistory history);

    @Update
    void update(WorkoutHistory history);

    @Delete
    void delete(WorkoutHistory history);

    @Query("SELECT * FROM workout_history WHERE workoutHistoryId = :workoutHistoryId")
    WorkoutHistory lookupWorkoutHistory(int workoutHistoryId);
}
