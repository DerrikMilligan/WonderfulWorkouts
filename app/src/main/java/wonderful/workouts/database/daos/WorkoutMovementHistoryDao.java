package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

@Dao
public interface WorkoutMovementHistoryDao {
    @Insert
    Completable insert(WorkoutMovementHistory movementHistory);

    @Update
    Completable update(WorkoutMovementHistory movementHistory);

    @Delete
    Completable delete(WorkoutMovementHistory movementHistory);
}
