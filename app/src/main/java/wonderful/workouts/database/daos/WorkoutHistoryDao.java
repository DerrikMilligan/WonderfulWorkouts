package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

    @Query("SELECT * FROM workout_history WHERE workoutId = :workoutId")
    List<WorkoutHistory> lookupWorkoutHistories(int workoutId);

    @Query("SELECT wh.* FROM workout_history AS wh LEFT JOIN workouts AS w on wh.workoutId = w.workoutId WHERE w.userId = :userId")
    List<WorkoutHistory> getUserWorkoutHistory(int userId);
}
