package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import wonderful.workouts.database.entities.Measurement;

@Dao
public interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Measurement measurement);

    @Update
    void update(Measurement measurement);

    @Delete
    void delete(Measurement measurement);
}
