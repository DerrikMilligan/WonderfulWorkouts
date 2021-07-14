package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import wonderful.workouts.database.entities.Movement;

@Dao
public interface MovementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Movement movement);

    @Update
    void update(Movement movement);

    @Delete
    void delete(Movement movement);

    @Query("SELECT * FROM movements WHERE movementId = :movementId")
    Movement lookupMovement(int movementId);

    @Query("SELECT * FROM movements WHERE name = :movementName")
    Movement lookupMovement(String movementName);

    @Query(
        "SELECT m.* " +
            "FROM movements AS m " +
            "LEFT JOIN workout_movements AS wm ON wm.movementId = m.movementId " +
            "LEFT JOIN workouts AS w ON w.workoutId = wm.workoutId " +
            "WHERE w.userId = :userId"
    )
    List<Movement> getUserMovements(int userId);

    @Query(
        "SELECT m.* " +
            "FROM movements AS m " +
            "LEFT JOIN workout_movements AS wm ON wm.movementId = m.movementId " +
            "LEFT JOIN workouts AS w ON w.workoutId = wm.workoutId " +
            "WHERE w.workoutId = :workoutId"
    )
    List<Movement> getWorkoutMovements(int workoutId);

    @Query(
        "SELECT DISTINCT m.category " +
            "FROM movements AS m " +
            "LEFT JOIN workout_movements AS wm ON wm.movementId = m.movementId " +
            "LEFT JOIN workouts AS w ON w.workoutId = wm.workoutId " +
            "WHERE w.userId = :userId " +
            "ORDER BY category ASC"
    )
    List<String> getCategoryList(int userId);

    @Query("SELECT * FROM movements WHERE category = :movementCategory")
    List<Movement> getCategoryMovements(String movementCategory);

    @Query(
        "SELECT DISTINCT m.equipment " +
            "FROM movements AS m " +
            "LEFT JOIN workout_movements AS wm ON wm.movementId = m.movementId " +
            "LEFT JOIN workouts AS w ON w.workoutId = wm.workoutId " +
            "WHERE w.userId = :userId " +
            "ORDER By equipment ASC"
    )
    List<String> getEquipmentList(int userId);

    @Query("SELECT * FROM movements WHERE equipment = :movementEquipment")
    List<Movement> getEquipmentMovements(String movementEquipment);

    @Transaction
    default Movement lookupOrCreateMovement(String movementName, String movementType, String movementCategory, String movementEquipment) {
        Movement lookup = lookupMovement(movementName);
        if (lookup != null) {
            return lookup;
        }

        Movement newMovement = new Movement();
        newMovement.name = movementName;
        newMovement.type = movementType;
        newMovement.category = movementCategory;
        newMovement.equipment = movementEquipment;
        newMovement.movementId = (int) insert(newMovement);

        return newMovement;
    }

}
