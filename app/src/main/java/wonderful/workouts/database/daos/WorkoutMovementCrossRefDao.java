package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Transaction;
import androidx.room.Update;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutMovementCrossRef;

@Dao
public interface WorkoutMovementCrossRefDao {
    @Insert
    void insert(WorkoutMovementCrossRef workoutMovement);

    @Update
    void update(WorkoutMovementCrossRef workoutMovement);

    @Delete
    void delete(WorkoutMovementCrossRef workoutMovement);

    public default WorkoutMovementCrossRef addMovementToWorkout(Workout workout, Movement movement) {
        WorkoutMovementCrossRef ref = new WorkoutMovementCrossRef();
        ref.workoutId = workout.workoutId;
        ref.movementId = movement.movementId;

        insert(ref);

        return ref;
    }
}
