package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.database.joiners.WorkoutWithMovements;

@Dao
public interface WorkoutDao {
    @Insert
    void insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    List<WorkoutWithMovements> getWorkoutMovements(int workoutId);

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    List<WorkoutWithHistory> getWorkoutHistory(int workoutId);
}
