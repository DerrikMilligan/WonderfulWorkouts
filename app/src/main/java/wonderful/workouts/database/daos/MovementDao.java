package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import wonderful.workouts.database.entities.Movement;

@Dao
public interface MovementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Movement movement);

    @Update
    void update(Movement movement);

    @Delete
    void delete(Movement movement);

    @Query("SELECT * FROM movements WHERE name = :movementName")
    Movement lookupMovement(String movementName);

    @Transaction
    public default Movement lookupOrCreateMovement(String movementName, String movementType) {
        Movement lookup = lookupMovement(movementName);
        if (lookup != null) {
            return lookup;
        }

        Movement newMovement = new Movement();
        newMovement.name = movementName;
        newMovement.type = movementType;
        newMovement.movementId = (int) insert(newMovement);

        return newMovement;
    }

}
