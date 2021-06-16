package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import wonderful.workouts.database.entities.WorkoutHistory;

@Dao
public interface WorkoutHistoryDao {
    @Insert
    Completable insert(WorkoutHistory history);

    @Update
    Completable update(WorkoutHistory history);

    @Delete
    Completable delete(WorkoutHistory history);
}
