package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutMovementHistoryWithMovement;

@Dao
public interface WorkoutMovementHistoryDao {
    @Insert
    long insert(WorkoutMovementHistory movementHistory);

    @Update
    void update(WorkoutMovementHistory movementHistory);

    @Delete
    void delete(WorkoutMovementHistory movementHistory);

    @Query("SELECT * from workout_movement_history WHERE movementId = :movementId")
    List<WorkoutMovementHistory> lookupMovementHistoriesWithMovementId(int movementId);

    @Query("SELECT * from workout_movement_history WHERE workoutHistoryId = :workoutHistoryId")
    List<WorkoutMovementHistory> lookupMovementHistoriesWithHistoryId(int workoutHistoryId);

    @Query("SELECT * from workout_movement_history WHERE workoutHistoryId = :workoutHistoryId")
    List<WorkoutMovementHistoryWithMovement> lookupMovementHistoriesWithMovements(int workoutHistoryId);
}
