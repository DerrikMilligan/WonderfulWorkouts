package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import wonderful.workouts.database.entities.WorkoutMovementCrossRef;

@Dao
public interface WorkoutMovementCrossRefDao {
    @Insert
    void insert(WorkoutMovementCrossRef workoutMovement);

    @Update
    void update(WorkoutMovementCrossRef workoutMovement);

    @Delete
    void delete(WorkoutMovementCrossRef workoutMovement);
}
