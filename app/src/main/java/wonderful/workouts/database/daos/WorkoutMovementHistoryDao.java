package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import wonderful.workouts.database.entities.WorkoutMovementHistory;

@Dao
public interface WorkoutMovementHistoryDao {
    @Insert
    long insert(WorkoutMovementHistory movementHistory);

    @Update
    void update(WorkoutMovementHistory movementHistory);

    @Delete
    void delete(WorkoutMovementHistory movementHistory);
}
