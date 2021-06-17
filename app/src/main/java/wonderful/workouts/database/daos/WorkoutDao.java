package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.database.joiners.WorkoutWithMovements;

@Dao
public interface WorkoutDao {
    @Insert
    Completable insert(Workout workout);

    @Update
    Completable update(Workout workout);

    @Delete
    Completable delete(Workout workout);

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    Maybe<List<WorkoutWithMovements>> getWorkoutMovements(int workoutId);

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    Maybe<List<WorkoutWithHistory>> getWorkoutHistory(int workoutId);
}
