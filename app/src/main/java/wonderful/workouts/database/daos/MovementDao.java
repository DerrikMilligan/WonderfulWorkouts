package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import wonderful.workouts.database.entities.Movement;

@Dao
public interface MovementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(Movement movement);

    @Update
    Completable update(Movement movement);

    @Delete
    Completable delete(Movement movement);
}
