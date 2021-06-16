package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import wonderful.workouts.database.entities.Measurement;

@Dao
public interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(Measurement measurement);

    @Update
    Completable update(Measurement measurement);

    @Delete
    Completable delete(Measurement measurement);
}
