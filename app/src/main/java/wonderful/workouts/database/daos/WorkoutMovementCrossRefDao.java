package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import wonderful.workouts.database.entities.WorkoutMovementCrossRef;

@Dao
public interface WorkoutMovementCrossRefDao {
    @Insert
    Completable insert(WorkoutMovementCrossRef workoutMovement);

    @Update
    Completable update(WorkoutMovementCrossRef workoutMovement);

    @Delete
    Completable delete(WorkoutMovementCrossRef workoutMovement);
}
