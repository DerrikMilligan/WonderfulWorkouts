package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wonderful.workouts.database.entities.Measurement;

@Dao
public interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Measurement measurement);

    @Update
    void update(Measurement measurement);

    @Delete
    void delete(Measurement measurement);

    @Query("SELECT * FROM user_measurements WHERE userId = :userId AND type = :type")
    Measurement lookupMeasurement(int userId, String type);

    @Query("SELECT * FROM user_measurements WHERE userId = :userId")
    List<Measurement> getUserMeasurements(int userId);
}
