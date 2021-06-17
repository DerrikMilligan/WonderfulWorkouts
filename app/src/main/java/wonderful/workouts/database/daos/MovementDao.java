package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import wonderful.workouts.database.entities.Movement;

@Dao
public interface MovementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movement movement);

    @Update
    void update(Movement movement);

    @Delete
    void delete(Movement movement);
}
